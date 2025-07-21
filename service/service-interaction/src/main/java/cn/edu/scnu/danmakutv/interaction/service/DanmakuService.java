package cn.edu.scnu.danmakutv.interaction.service;

import cn.edu.scnu.danmakutv.domain.interaction.Danmaku;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDateTime;
import java.util.List;

public interface DanmakuService extends IService<Danmaku> {
    // 添加弹幕
    void addDanmaku (Danmaku danmaku);

    // 异步添加弹幕
    public void asyncAddDanmaku (Danmaku danmaku);

    // 查询弹幕
    List<Danmaku> getDanmaku (Long videoId, LocalDateTime startTime, LocalDateTime endTime);

    // 添加弹幕到redis
    void addDanmakuToRedis (Danmaku danmaku);
}
