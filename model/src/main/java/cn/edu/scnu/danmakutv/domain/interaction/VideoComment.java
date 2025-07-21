package cn.edu.scnu.danmakutv.domain.interaction;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(name = "视频评论")
@Data
@TableName("t_video_comment")
public class VideoComment {

    @Schema(description = "id")
    @TableId(type = IdType.AUTO)
    private Long id; // 评论ID

    @Schema(description = "用户id")
    @TableField("user_id")
    private Long userId; // 用户ID

    @Schema(description = "评论的视频id")
    @TableField("video_id")
    private Long videoId; // 视频ID

    @Schema(description = "评论内容")
    @TableField("content")
    private String content; // 评论内容

    @Schema(description = "评论时间")
    @TableField("create_at")
    private LocalDateTime createTime; // 评论时间
}
