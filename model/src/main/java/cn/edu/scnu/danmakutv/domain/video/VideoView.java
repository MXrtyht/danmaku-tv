package cn.edu.scnu.danmakutv.domain.video;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(name = "视频观看实体类")
@Data
@TableName("t_video_view")
public class VideoView {
    @Schema(description = "id")
    @TableId(type = IdType.AUTO)
    private Long id;

    @Schema(description = "用户id")
    @TableField("user_id")
    private Long userId;

    @Schema(description = "视频id")
    @TableField("video_id")
    private Long videoId;

    @Schema(description = "客户端id")
    @TableField("client_id")
    private String clientId;

    @Schema(description = "ip")
    @TableField("ip")
    private String ip;

    @Schema(description = "创建时间")
    @TableField("create_at")
    private LocalDateTime createAt;
}
