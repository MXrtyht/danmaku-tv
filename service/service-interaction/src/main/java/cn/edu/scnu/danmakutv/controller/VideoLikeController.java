package cn.edu.scnu.danmakutv.controller;

import cn.edu.scnu.danmakutv.common.authentication.AuthenticationSupport;
import cn.edu.scnu.danmakutv.common.response.CommonResponse;
import cn.edu.scnu.danmakutv.service.VideoLikeService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/video")
public class VideoLikeController {
    @Resource
    private AuthenticationSupport authenticationSupport;

    @Resource
    private VideoLikeService videoLikeService;

    /**
     * 视频点赞
     * @param videoId 视频ID
     * @return 响应
     */
    @PostMapping("/like")
    public CommonResponse<String> addVideoLike (Long videoId) {
        Long userId = authenticationSupport.getCurrentUserId();
        videoLikeService.addVideoLike(userId, videoId);
        return CommonResponse.success("");
    }
}
