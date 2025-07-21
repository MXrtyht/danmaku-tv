package cn.edu.scnu.danmakutv.interaction.controller;


import cn.edu.scnu.danmakutv.common.authentication.AuthenticationSupport;
import cn.edu.scnu.danmakutv.common.response.CommonResponse;
import cn.edu.scnu.danmakutv.dto.video.AddVideoCommentDTO;
import cn.edu.scnu.danmakutv.interaction.service.VideoCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

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
}
