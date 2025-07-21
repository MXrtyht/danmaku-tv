package cn.edu.scnu.danmakutv.video.controller;

import cn.edu.scnu.danmakutv.common.authentication.AuthenticationSupport;
import cn.edu.scnu.danmakutv.common.response.CommonResponse;
import cn.edu.scnu.danmakutv.domain.video.VideoView;
import cn.edu.scnu.danmakutv.video.service.VideoViewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Tag(name = "视频观看记录相关接口")
@RestController
@RequestMapping("/video")
public class VideoViewController {
    @Resource
    private AuthenticationSupport authenticationSupport;

    @Resource
    private VideoViewService videoViewService;

    /**
     * 添加视频观看记录
     *
     * @param videoView 视频观看记录
     * @param request   HttpServletRequest
     * @return 提示
     */
    @Operation(summary = "添加视频观看记录")
    @PostMapping("/video-views")
    public CommonResponse<String> addVideoView (
            @RequestBody Long videoId,
            HttpServletRequest request
    ) {
        Long userId;

        VideoView videoView = new VideoView();
        videoView.setVideoId(videoId);

        try {
            userId = authenticationSupport.getCurrentUserId();
            videoView.setUserId(userId);
            videoViewService.addVideoView(videoView, request);
        } catch (Exception e) {
            videoViewService.addVideoView(videoView, request);
        }
        return CommonResponse.success("成功添加视频观看记录");
    }

    /**
     * 查询视频播放量
     *
     * @param videoId 视频id
     * @return 视频播放量
     */
    @Operation(summary = "查询视频播放量")
    @GetMapping("/video-view-counts")
    public CommonResponse<Long> getVideoViewCounts (@RequestParam Long videoId) {
        Long count = videoViewService.getVideoViewCounts(videoId);
        return CommonResponse.success(count);
    }

    @Operation(summary = "获取当前用户的观看记录")
    @GetMapping("/video-views-history")
    public CommonResponse<List<Long>> getVideoViewsHistory () {
        Long userId = authenticationSupport.getCurrentUserId();
        List<Long> videoIds = videoViewService.getVideoViewsHistory(userId);
        return CommonResponse.success(videoIds);
    }
}
