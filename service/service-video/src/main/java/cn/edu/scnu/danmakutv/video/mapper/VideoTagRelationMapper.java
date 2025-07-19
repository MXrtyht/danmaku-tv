package cn.edu.scnu.danmakutv.video.mapper;

import cn.edu.scnu.danmakutv.domain.video.VideoTagRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface VideoTagRelationMapper extends BaseMapper<VideoTagRelation> {
    /**
     * 根据视频ID删除标签关联关系
     * @param videoId 视频ID
     * @return 影响的行数
     */
    void deleteByVideoId(Long videoId);

    /**
     * 根据视频标签列表查找被推荐视频ID
     * @param tagIds
     * @param limit
     * @return 被推荐视频id列表
     */
    List<Long> findVideoIdsByTagIds(@Param("tagIds") List<Long> tagIds, @Param("limit") int limit);
}
