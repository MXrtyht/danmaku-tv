package cn.edu.scnu.danmakutv.controller;

import cn.edu.scnu.danmakutv.common.authentication.AuthenticationSupport;
import cn.edu.scnu.danmakutv.common.response.CommonResponse;
import cn.edu.scnu.danmakutv.domain.interaction.VideoComment;
import cn.edu.scnu.danmakutv.service.VideoCommentService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Tag(name = "视频评论相关接口")
@RestController
@RequestMapping("/video")
public class VideoCommentController {
    @Resource
    private AuthenticationSupport userSupport;
    @Resource
    private VideoCommentService videoCommentService;
    /**
     * 添加视频评论
     */
    @PostMapping("/comment")
    public CommonResponse<String> addVideoComment(@RequestBody VideoComment videoComment){
        Long userId = userSupport.getCurrentUserId();
        videoCommentService.addVideoComment(videoComment, userId);
        return CommonResponse.success("");
    }
    /**
     * 分页查询视频评论
     */
    @GetMapping("/comment/{videoId}")
    public CommonResponse<Page<VideoComment>> pageListVideoComments(
            @PathVariable Long videoId,
            @RequestParam Integer page,
            @RequestParam Integer size
            ) {
        return CommonResponse.success(videoCommentService.pageListVideoComments(size, page, videoId));
    }
}
