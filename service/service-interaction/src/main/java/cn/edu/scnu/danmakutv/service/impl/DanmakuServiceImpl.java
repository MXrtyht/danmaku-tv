package cn.edu.scnu.danmakutv.service.impl;

import cn.edu.scnu.danmakutv.domain.Danmaku;
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

    @Override
    public void addDanmaku (Danmaku danmaku) {
        baseMapper.insert(danmaku);
    }

    @Async
    public void asyncAddDanmaku (Danmaku danmaku) {
        baseMapper.insert(danmaku);
    }

    @Override
    public List<Danmaku> getDanmaku (Long videoId, LocalDateTime startTime, LocalDateTime endTime) {

        System.out.println("-------------" + startTime + "  " + endTime);

        // 先查Redis中的弹幕数据
        String redisKey = DANMAKU_KEY + videoId;
        String redisValue = redisTemplate.opsForValue().get(redisKey);
        List<Danmaku> danmakuList;

        System.out.println("------------------------------Redis Value: " + redisValue);
        System.out.println("------------------------------Redis Value: " + StringUtil.isNullOrEmpty(redisValue));
        if (!StringUtil.isNullOrEmpty(redisValue) && !"[]".equals(redisValue)) {
            danmakuList = JSON.parseArray(redisValue, Danmaku.class);

            List<Danmaku> childList = new ArrayList<>();
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
            if (startTime != null) {
                wrapper.ge("create_at", startTime);
            }
            if (endTime != null) {
                wrapper.le("create_at", endTime);
            }

            danmakuList = baseMapper.selectList(wrapper);

            // 保存到redis
            redisTemplate.opsForValue().set(redisKey, JSON.toJSONString(danmakuList));

            return danmakuList;
        }
    }

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
