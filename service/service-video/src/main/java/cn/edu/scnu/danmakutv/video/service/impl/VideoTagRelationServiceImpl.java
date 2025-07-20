package cn.edu.scnu.danmakutv.video.service.impl;

import cn.edu.scnu.danmakutv.domain.video.VideoTagRelation;
import cn.edu.scnu.danmakutv.video.mapper.VideoTagRelationMapper;
import cn.edu.scnu.danmakutv.video.service.VideoTagRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VideoTagRelationServiceImpl extends ServiceImpl<VideoTagRelationMapper, VideoTagRelation> implements VideoTagRelationService {

    /**
     * 批量添加视频与标签的关联关系
     *
     * @param videoId 视频id
     * @param tagIds  标签id列表
     */
    @Override
    public void addVideoTagRelation (Long videoId, List<Long> tagIds) {
        if (tagIds == null || tagIds.isEmpty()) {
            return;
        }

        List<VideoTagRelation> relations = tagIds.stream()
                                                 .map(tagId -> {
                                                     VideoTagRelation relation = new VideoTagRelation();
                                                     relation.setVideoId(videoId);
                                                     relation.setTagId(tagId);
                                                     return relation;
                                                 })
                                                 .collect(Collectors.toList());

        // 批量保存
        this.baseMapper.insert(relations);
    }

    /**
     * 根据视频ID查找其标签
     *
     * @param videoId 视频id
     */
    @Transactional(readOnly = true)
    @Override
    public List<Long> getIdsByVideoId (Long videoId) {
        if (videoId == null) {
            return null;
        }

        // 获取视频与标签的关联关系
        List<VideoTagRelation> relations = this.lambdaQuery()
                                               .eq(VideoTagRelation::getVideoId, videoId)
                                               .list();

        // 返回标签ID列表
        return relations.stream()
                        .map(VideoTagRelation::getTagId)
                        .collect(Collectors.toList());
    }
}
