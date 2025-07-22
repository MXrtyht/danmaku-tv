package cn.edu.scnu.danmakutv.dto.interaction;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(name = "视频投币 数据传输对象")
@Data
public class AddVideoCoinDTO {
    @Schema(description = "用户id")
    @TableField("user_id")
    private Long userId;

    @Schema(description = "视频id")
    @TableField("video_id")
    private Long videoId;

    @Schema(description = "投币数量")
    @TableField("coin")
    @Min(value = 1, message = "投币数量不能小于1")
    @Max(value = 2, message = "投币数量不能大于2")
    private Integer coin;

    @Schema(description = "投币时间")
    @TableField("create_at")
    private LocalDateTime createAt;
}
