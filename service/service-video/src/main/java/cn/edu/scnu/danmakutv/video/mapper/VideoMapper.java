package cn.edu.scnu.danmakutv.video.mapper;

import cn.edu.scnu.danmakutv.domain.video.Video;
import cn.edu.scnu.danmakutv.domain.video.VideoView;
import cn.edu.scnu.danmakutv.vo.video.VideoVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface VideoMapper extends BaseMapper<Video> {
    IPage<VideoVO> selectVideo (IPage<?> page, @Param("ew") QueryWrapper<?> wrapper);

    /**
     * 获取视频观看记录
     * @param params
     * @return
     */
    VideoView getVideoView(Map<String, Object> params);

    /**
     * 获取视频播放量
     * @param videoId
     * @return
     */
    Integer getVideoViewCounts(Long videoId);

    /**
     * 添加视频观看记录
     * @param videoView
     */
    void addVideoView(VideoView videoView);
}
