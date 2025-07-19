package cn.edu.scnu.danmakutv.service;

import cn.edu.scnu.danmakutv.domain.interaction.VideoCollection;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

public interface VideoCollectService extends IService<VideoCollection> {
    void addVideoCollection(VideoCollection videoCollection, Long userId);
    void deleteVideoCollection(Long videoId, Long userId);

    Map<String, Object> getVideoCollections(Long videoId, Long userId);
}
