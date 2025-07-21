package cn.edu.scnu.danmakutv.interaction.service;

import cn.edu.scnu.danmakutv.domain.interaction.VideoLike;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

public interface VideoLikeService extends IService<VideoLike> {
    void addVideoLike (Long userId, Long videoId);

    void deleteVideoLike (Long userId, Long videoId);

    Map<String, Object> getVideoLikeCount (Long videoId, Long userId);
}
