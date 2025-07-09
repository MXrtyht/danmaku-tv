package cn.edu.scnu.danmakutv.controller;

import cn.edu.scnu.danmakutv.common.authentication.AuthenticationSupport;
import cn.edu.scnu.danmakutv.common.response.CommonResponse;
import cn.edu.scnu.danmakutv.service.VideoLikeService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/video")
public class VideoLikeController {
    @Resource
    private AuthenticationSupport authenticationSupport;

    @Resource
    private VideoLikeService videoLikeService;

    /**
     * 视频点赞
     *
     * @param videoId 视频ID
     * @return 响应
     */
    @PostMapping("/like")
    public CommonResponse<String> addVideoLike (Long videoId) {
        Long userId = authenticationSupport.getCurrentUserId();
        videoLikeService.addVideoLike(userId, videoId);
        return CommonResponse.success("");
    }

    /**
     * 取消视频点赞
     *
     * @param videoId 视频ID
     * @return 响应
     */
    @DeleteMapping("/like")
    public CommonResponse<String> deleteVideoLike (Long videoId) {
        Long userId = authenticationSupport.getCurrentUserId();
        videoLikeService.deleteVideoLike(userId, videoId);
        return CommonResponse.success("");
    }

    /**
     * 获取视频点赞数量
     *
     * @param videoId 视频ID
     * @return CommonResponse<Map < Long, Boolean>> Long:视频点赞数 Boolean:是否已点赞
     */
    @GetMapping("/like")
    public CommonResponse<Map<String, Object>> getVideoLikeCount (Long videoId) {
        Long userId = null;
        try {
            userId = authenticationSupport.getCurrentUserId();
        } catch (Exception ignore) {
        }
        Map<String, Object> result = videoLikeService.getVideoLikeCount(videoId, userId);
        return CommonResponse.success(result);
    }
}
