package cn.edu.scnu.danmakutv.video.mapper;

import cn.edu.scnu.danmakutv.domain.video.VideoTag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoTagMapper extends BaseMapper<VideoTag> {
    List<VideoTag> selectTagsByVideoId (@Param("videoId") Long videoId);
}
