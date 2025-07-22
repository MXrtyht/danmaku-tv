package cn.edu.scnu.danmakutv.dto.interaction;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(name = "添加视频评论DTO")
@Data
public class AddVideoCommentDTO {
    @Schema(description = "用户ID")
    private  Long userId;

    @Schema(description = "视频ID")
    private Long videoId;

    @Schema(description = "评论内容")
    private String content;
}
