package cn.edu.scnu.danmakutv.controller;

import cn.edu.scnu.danmakutv.common.authentication.AuthenticationSupport;
import cn.edu.scnu.danmakutv.common.response.CommonResponse;
import cn.edu.scnu.danmakutv.service.VideoLikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@Tag(name = "互动相关接口")
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
    @Operation(summary = "视频点赞")
    @PostMapping("/like")
    public CommonResponse<String> addVideoLike (@Parameter(description = "视频id") Long videoId) {
        Long userId = authenticationSupport.getCurrentUserId();
        videoLikeService.addVideoLike(userId, videoId);
        return CommonResponse.success("点赞成功");
    }

    /**
     * 取消视频点赞
     *
     * @param videoId 视频ID
     * @return 响应
     */
    @Operation(summary = "取消视频点赞")
    @DeleteMapping("/like")
    public CommonResponse<String> deleteVideoLike (@Parameter(description = "视频id") Long videoId) {
        Long userId = authenticationSupport.getCurrentUserId();
        videoLikeService.deleteVideoLike(userId, videoId);
        return CommonResponse.success("");
    }

    /**
     * 获取视频点赞数量
     *
     * @param videoId 视频ID
     * @return CommonResponse<Map < Long, Object>> 其中: "isLiked", isLiked 表示是否已点赞
     */
    @Operation(
            summary = "获取视频点赞数量",
            description = "返回视频的点赞数量和当前用户是否已点赞"
    )
    @GetMapping("/like")
    public CommonResponse<Map<String, Object>> getVideoLikeCount (@Parameter(description = "视频id") Long videoId) {
        Long userId = null;
        try {
            userId = authenticationSupport.getCurrentUserId();
        } catch (Exception ignore) {
        }
        Map<String, Object> result = videoLikeService.getVideoLikeCount(videoId, userId);
        return CommonResponse.success(result);
    }
}
