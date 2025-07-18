package cn.edu.scnu.danmakutv.video.mapper;

import cn.edu.scnu.danmakutv.domain.video.VideoTagRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface VideoTagRelationMapper extends BaseMapper<VideoTagRelation> {
    /**
     * 根据视频ID删除标签关联关系
     * @param videoId 视频ID
     * @return 影响的行数
     */
    void deleteByVideoId(Long videoId);
}
