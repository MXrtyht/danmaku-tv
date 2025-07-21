package cn.edu.scnu.danmakutv.video.service.impl;

import cn.edu.scnu.danmakutv.domain.video.VideoTagRelation;
import cn.edu.scnu.danmakutv.video.mapper.VideoTagRelationMapper;
import cn.edu.scnu.danmakutv.video.service.VideoTagRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
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

    /**
     * 根据视频ID删除标签关联关系
     * @param videoId 视频ID
     * @return 影响的行数
     */
    @Override
    public void deleteByVideoId(Long videoId){
        baseMapper.deleteByVideoId(videoId);
    }

    /**
     * 根据视频标签列表查找被推荐视频ID
     * @param tagIds
     * @param limit
     * @return 被推荐视频id列表
     */
    public List<Long> findVideoIdsByTagIds(@Param("tagIds") List<Long> tagIds, @Param("limit") int limit){
        return baseMapper.findVideoIdsByTagIds(tagIds,limit);
    }

    @Override
    public void insert(VideoTagRelation relation) {
        baseMapper.insert(relation);
    }
}
