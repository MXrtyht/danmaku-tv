package cn.edu.scnu.danmakutv.video.service;

import cn.edu.scnu.danmakutv.domain.video.VideoTagRelation;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface VideoTagRelationService extends IService<VideoTagRelation> {
    void addVideoTagRelation (Long videoId, List<Long> tagIds);

    /**
     * 根据视频ID删除标签关联关系
     *
     * @param videoId 视频ID
     * @return 影响的行数
     */
    void deleteByVideoId (Long videoId);

    @Transactional(readOnly = true)
    List<Long> getIdsByVideoId (Long videoId);

    /**
     * 根据视频标签列表查找被推荐视频ID
     *
     * @param tagIds
     * @param limit
     * @return 被推荐视频id列表
     */
    List<Long> findVideoIdsByTagIds (List<Long> tagIds, Long limit);

    void insert (VideoTagRelation relation);
}
