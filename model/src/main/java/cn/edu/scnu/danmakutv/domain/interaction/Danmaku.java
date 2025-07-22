package cn.edu.scnu.danmakutv.domain.interaction;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("t_danmaku")
public class Danmaku {
    @Schema(description = "id")
    @TableId(type = IdType.AUTO)
    private Long id;

    @Schema(description = "用户id")
    @TableField("user_id")
    private Long userId;

    @Schema(description = "视频id")
    @TableField("video_id")
    private Long videoId;

    @Schema(description = "弹幕内容")
    @TableField("content")
    private String content;

    @Schema(description = "视频时间点")
    @TableField("video_time")
    private Long videoTime; // 视频时间点，单位为秒

    @Schema(description = "发送弹幕时间")
    @TableField("create_at")
    private LocalDateTime createAt;

}
