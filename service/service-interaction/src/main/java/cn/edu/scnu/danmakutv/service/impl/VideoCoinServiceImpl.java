package cn.edu.scnu.danmakutv.service.impl;

import cn.edu.scnu.danmakutv.common.exception.DanmakuException;
import cn.edu.scnu.danmakutv.domain.interaction.UserCoin;
import cn.edu.scnu.danmakutv.domain.interaction.VideoCoin;
import cn.edu.scnu.danmakutv.domain.video.Video;
import cn.edu.scnu.danmakutv.mapper.VideoCoinMapper;
import cn.edu.scnu.danmakutv.service.UserCoinService;
import cn.edu.scnu.danmakutv.service.VideoCoinService;
import cn.edu.scnu.danmakutv.video.service.VideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class VideoCoinServiceImpl extends ServiceImpl<VideoCoinMapper, VideoCoin> implements VideoCoinService {
    private VideoService videoService;
    private VideoCoinMapper videoCoinMapper;
    private VideoCoinService videoCoinService;
    private UserCoinService userCoinService;

    /**
     * 视频投币数量增加
     * @param videoCoin
     * @param userId 用户ID
     */
    @Override
    public void addVideoCoins(VideoCoin videoCoin, Long userId) {
        Long videoId = videoCoin.getVideoId();
        Integer amount = videoCoin.getAmount();
        if(videoId == null){
            throw new DanmakuException("参数异常", 3003);
        }
        Video video = videoService.getById(videoId);
        if (video==null){
            throw new DanmakuException("非法视频！", 3004);
        }
        //查询当前登录用户是否拥有足够的硬币
        Integer userCoinsAmount = userCoinService.getUserCoinsAmount(userId);
        userCoinsAmount = userCoinsAmount == null ? 0 : userCoinsAmount;
        if(amount > userCoinsAmount){
            throw new DanmakuException("硬币数量不足！", 3305);
        }
        //查询当前登录用户对该视频已经投了多少硬币
        VideoCoin dbVideoCoin = videoCoinMapper.selectOne(
                new QueryWrapper<VideoCoin>()
                        .eq("videoId", videoId)
                        .eq("userId", userId)
        );
        //新增视频投币
        if(dbVideoCoin == null){
            videoCoin.setUserId(userId);
            videoCoin.setCreateTime(new Date());
            videoCoinMapper.insert(videoCoin);
        }else {
            Integer dbAmount = dbVideoCoin.getAmount();
            dbAmount += amount;
            //更新视频投币
            videoCoin.setUserId(userId);
            videoCoin.setAmount(dbAmount);
            videoCoin.setUpdateTime(new Date());
            videoCoinMapper.update(
                    new UpdateWrapper<VideoCoin>()
                    .set("amount", amount)
                    .set("updatetime", new Date())
                    .eq("userId", userId)
                    .eq("videoId", videoId)
    );
        }
        //更新用户当前硬币总数
        userCoinService.updateUserCoinsAmount(userId, (userCoinsAmount-amount));
    }

    /**
     * 获取该视频被投币的数量、用户是否对该视频投过硬币
     * @param videoId 该视频ID
     * @param userId 用户ID
     * @return 包含当前视频硬币的总数，以及当前用户是否投过币的Map
     */
    @Override
    public Map<String, Object> getVideoCoins(Long videoId, Long userId) {
        Long count = videoCoinService.getObj(
                new QueryWrapper<VideoCoin>()
                        .select("SUM(amount)")
                        .eq("userId", userId),
                obj -> obj != null ? Long.valueOf(obj.toString()) : 0
        );
        VideoCoin videoCoin = videoCoinMapper.selectOne(
                new QueryWrapper<VideoCoin>()
                        .eq("videoId", videoId)
                        .eq("userId", userId)
        );;
        boolean like = videoCoin != null;
        Map<String, Object> result = new HashMap<>();
        result.put("count", count);
        result.put("like", like);
        return result;
    }
}
