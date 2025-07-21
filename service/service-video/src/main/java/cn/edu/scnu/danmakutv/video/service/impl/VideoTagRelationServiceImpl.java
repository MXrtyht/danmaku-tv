package cn.edu.scnu.danmakutv.video.service.impl;

import cn.edu.scnu.danmakutv.domain.video.VideoTagRelation;
import cn.edu.scnu.danmakutv.video.mapper.VideoTagRelationMapper;
import cn.edu.scnu.danmakutv.video.service.VideoTagRelationService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
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
     *
     * @param videoId 视频ID
     * @return 影响的行数
     */
    @Override
    public void deleteByVideoId (Long videoId) {
        baseMapper.delete(
                new QueryWrapper<VideoTagRelation>()
                        .eq("video_id", videoId)
        );
    }

    /**
     * 根据视频标签列表查找被推荐视频ID （按匹配标签数排序）
     *
     * @param tagIds 视频标签ID列表
     * @param limit  返回的视频ID数量限制
     * @return 被推荐视频id列表，按标签命中数降序排列
     */
    public List<Long> findVideoIdsByTagIds(List<Long> tagIds, Long limit) {
        if (tagIds == null || tagIds.isEmpty()) {
            return Collections.emptyList();
        }

        // 查询所有 tag_id 命中的 video_tag_relation
        List<VideoTagRelation> relations = baseMapper.selectList(
                new QueryWrapper<VideoTagRelation>().in("tag_id", tagIds)
        );

        // 统计每个视频命中标签的次数
        Map<Long, Long> videoIdCountMap = relations.stream()
                                                   .collect(Collectors.groupingBy(
                                                           VideoTagRelation::getVideoId,
                                                           Collectors.counting()
                                                   ));

        // 按标签命中数降序排序，并限制数量
        return videoIdCountMap.entrySet()
                              .stream()
                              .sorted(Map.Entry.<Long, Long>comparingByValue().reversed()) // 命中数降序
                              .limit(limit)
                              .map(Map.Entry::getKey)
                              .collect(Collectors.toList());
    }
}
