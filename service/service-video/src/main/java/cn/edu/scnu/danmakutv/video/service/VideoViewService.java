package cn.edu.scnu.danmakutv.video.service;

import cn.edu.scnu.danmakutv.domain.video.VideoView;
import cn.edu.scnu.danmakutv.vo.video.GetVideoPlayCountVO;

import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface VideoViewService extends IService<VideoView> {
    /**
     * 添加视频观看记录
     *
     * @param videoView 视频观看记录
     * @param request
     */
    void addVideoView (VideoView videoView, HttpServletRequest request);

    /**
     * 获取视频播放量
     *
     * @param videoId
     * @return
     */
    Long getVideoViewCounts (Long videoId);
    
    /**
     * 批量获取视频播放量
     *
     * @param videoId列表
     * @return 
     */
    List<GetVideoPlayCountVO> getVideoViewCountsBatch(List<Long> videoIdList);

    /**
     * 获取用户观看历史
     *
     * @param userId 用户ID
     * @return 视频ID列表
     */
    List<Long> getVideoViewsHistory (Long userId);
}
