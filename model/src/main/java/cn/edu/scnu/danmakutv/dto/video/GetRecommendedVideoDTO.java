package cn.edu.scnu.danmakutv.dto.video;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Schema(name = "推荐视频DTO")
@Data
public class GetRecommendedVideoDTO {
    @Schema(description = "视频ID")
    private Long videoId;

    @Schema(description = "数量限制")
    private Long limit;
}