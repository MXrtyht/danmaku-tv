package cn.edu.scnu.danmakutv.video.service;

import cn.edu.scnu.danmakutv.domain.video.VideoTagRelation;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface VideoTagRelationService extends IService<VideoTagRelation> {
    void addVideoTagRelation (Long videoId, List<Long> tagIds);

    @Transactional(readOnly = true)
    List<Long> getIdsByVideoId (Long videoId);
}
