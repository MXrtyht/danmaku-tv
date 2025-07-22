package cn.edu.scnu.danmakutv.interaction.controller;


import cn.edu.scnu.danmakutv.common.authentication.AuthenticationSupport;
import cn.edu.scnu.danmakutv.common.response.CommonResponse;
import cn.edu.scnu.danmakutv.domain.interaction.VideoComment;
import cn.edu.scnu.danmakutv.dto.interaction.AddVideoCommentDTO;
import cn.edu.scnu.danmakutv.interaction.service.VideoCommentService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.Size;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Tag(name = "视频评论相关接口")
@RequestMapping("/video")
@RestController
public class VideoCommentController {
    @Resource
    private AuthenticationSupport authenticationSupport;

    @Resource
    private VideoCommentService videoCommentService;

    @Operation(summary = "添加视频评论")
    @PostMapping("/add-comment")
    public CommonResponse<Long> addComment (
            @RequestBody AddVideoCommentDTO addVideoCommentDTO
    ) {
        Long userId = authenticationSupport.getCurrentUserId();
        addVideoCommentDTO.setUserId(userId);
        Long commentId = videoCommentService.addComment(addVideoCommentDTO);
        return CommonResponse.success(commentId);
    }

    @Operation(summary = "删除视频评论")
    @PostMapping("/delete-comment")
    public CommonResponse<String> deleteComment (
            @RequestBody Long commentId
    ) {
        Long userId = authenticationSupport.getCurrentUserId();
        videoCommentService.deleteComment(userId, commentId);
        return CommonResponse.success("删除成功");
    }

    @Operation(summary = "分页获取视频评论")
    @GetMapping("/video-comments")
    public CommonResponse<IPage<VideoComment>> getVideoComments(
            @Size(min = 1) @Parameter(description = "视频id", required = true) Long videoId,
            @Size(min = 1) @Parameter(description = "分页页码, 从1开始") int page,
            @Size(min = 4) @Parameter(description = "分页大小, 最小每页5个") int size
    ){
        IPage<VideoComment> videoComments = videoCommentService.getVideoComments(videoId, page, size);
        return CommonResponse.success(videoComments);
    }
}
