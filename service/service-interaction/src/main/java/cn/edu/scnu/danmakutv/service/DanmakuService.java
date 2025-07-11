package cn.edu.scnu.danmakutv.service;

import cn.edu.scnu.danmakutv.domain.Danmaku;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDateTime;
import java.util.List;

public interface DanmakuService extends IService<Danmaku> {
    // 添加弹幕
    void addDanmaku (Danmaku danmaku);

    // 查询弹幕
    List<Danmaku> getDanmaku (Long videoId, LocalDateTime startTime, LocalDateTime endTime);

    // 添加弹幕到redis
    void addDanmakuToRedis (Danmaku danmaku);
}
