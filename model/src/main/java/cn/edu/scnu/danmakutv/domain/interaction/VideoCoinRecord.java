package cn.edu.scnu.danmakutv.domain.interaction;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("t_video_coin")
public class VideoCoinRecord {
    @Schema(description = "id")
    @TableId(type = IdType.AUTO)
    private Long id;

    @Schema(description = "用户id")
    @TableField("user_id")
    private Long userId;

    @Schema(description = "视频id")
    @TableField("video_id")
    private Long videoId;

    @Schema(description = "投币数量")
    @TableField("coin")
    private Integer coin;

    @Schema(description = "投币时间")
    @TableField("create_at")
    private LocalDateTime createAt;
}
