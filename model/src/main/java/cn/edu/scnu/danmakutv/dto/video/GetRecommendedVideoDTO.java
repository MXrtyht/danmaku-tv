package cn.edu.scnu.danmakutv.dto.video;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(name = "推荐视频DTO")
@Data
public class GetRecommendedVideoDTO {
    @Schema(description = "视频ID")
    private Long videoId;

    @Schema(description = "数量限制")
    private Long limit;
}