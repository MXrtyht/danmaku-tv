package cn.edu.scnu.danmakutv.service.impl;

import cn.edu.scnu.danmakutv.domain.interaction.Danmaku;
import cn.edu.scnu.danmakutv.mapper.DanmakuMapper;
import cn.edu.scnu.danmakutv.service.DanmakuService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.netty.util.internal.StringUtil;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DanmakuServiceImpl extends ServiceImpl<DanmakuMapper, Danmaku> implements DanmakuService {

    private static final String DANMAKU_KEY = "dm-video-";

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 添加弹幕
     * @param danmaku 弹幕内容
     */
    @Override
    public void addDanmaku (Danmaku danmaku) {
        baseMapper.insert(danmaku);
    }

    /**
     * 异步添加弹幕
     * @param danmaku 弹幕内容
     */
    @Async
    public void asyncAddDanmaku (Danmaku danmaku) {
        baseMapper.insert(danmaku);
    }

    /**
     * 获取视频弹幕
     * @param videoId 视频ID
     * @param startTime 可选的开始时间
     * @param endTime 可选的结束时间
     * @return 弹幕列表
     */
    @Override
    public List<Danmaku> getDanmaku (Long videoId, LocalDateTime startTime, LocalDateTime endTime) {

        // 先查Redis中的弹幕数据
        String redisKey = DANMAKU_KEY + videoId;
        String redisValue = redisTemplate.opsForValue().get(redisKey);
        List<Danmaku> danmakuList;

        if (!StringUtil.isNullOrEmpty(redisValue) && !"[]".equals(redisValue)) {
            danmakuList = JSON.parseArray(redisValue, Danmaku.class);

            // 如果没有时间段, 返回所有结果
            if (startTime == null || endTime == null) {
                return danmakuList;
            }

            List<Danmaku> childList = new ArrayList<>();
            // 取出指定时间段的弹幕
            for (Danmaku danmaku : danmakuList) {
                LocalDateTime createAt = danmaku.getCreateAt();
                if (createAt.isAfter(startTime) && createAt.isBefore(endTime)) {
                    childList.add(danmaku);
                }
            }

            return childList;
        } else {
            // redis没查到, 查数据库
            QueryWrapper<Danmaku> wrapper = new QueryWrapper<>();

            // 设置查询条件
            if (videoId != null) {
                wrapper.eq("video_id", videoId);
            }
            if (startTime != null && endTime != null) {
                wrapper.ge("create_at", startTime).le("create_at", endTime);
            }
            danmakuList = baseMapper.selectList(wrapper);

            // 保存到redis
            redisTemplate.opsForValue().set(redisKey, JSON.toJSONString(danmakuList));

            return danmakuList;
        }
    }

    /**
     * 弹幕添加到Redis
     * @param danmaku 弹幕内容
     */
    public void addDanmakuToRedis (Danmaku danmaku) {
        String key = DANMAKU_KEY + danmaku.getVideoId();
        String value = redisTemplate.opsForValue().get(key);
        List<Danmaku> list = new ArrayList<>();
        if (!StringUtil.isNullOrEmpty(value)) {
            list = JSON.parseArray(value, Danmaku.class);
        }
        list.add(danmaku);
        redisTemplate.opsForValue().set(key, JSONObject.toJSONString(list));
    }
}
