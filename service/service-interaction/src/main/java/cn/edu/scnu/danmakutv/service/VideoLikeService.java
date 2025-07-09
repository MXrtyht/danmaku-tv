package cn.edu.scnu.danmakutv.service;

import cn.edu.scnu.danmakutv.domain.VideoLike;
import com.baomidou.mybatisplus.extension.service.IService;

public interface VideoLikeService extends IService<VideoLike> {
    void addVideoLike (Long userId, Long videoId);
}
