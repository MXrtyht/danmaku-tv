package cn.edu.scnu.danmakutv.video.service.impl;

import cn.edu.scnu.danmakutv.domain.video.VideoTagRelation;
import cn.edu.scnu.danmakutv.video.mapper.VideoTagRelationMapper;
import cn.edu.scnu.danmakutv.video.service.VideoTagRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoTagRelationServiceImpl extends ServiceImpl<VideoTagRelationMapper, VideoTagRelation> implements VideoTagRelationService {
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
