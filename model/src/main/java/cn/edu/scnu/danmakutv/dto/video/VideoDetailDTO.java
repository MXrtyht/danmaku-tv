package cn.edu.scnu.danmakutv.dto.video;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Schema(name = "视频详情响应DTO")
@Data
public class VideoDetailDTO {
    @Schema(description = "视频ID")
    private Long id;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "视频URL")
    private String videoUrl;

    @Schema(description = "封面URL")
    private String coverUrl;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "类型 (true:自制, false:转载)")
    private Boolean type;

    @Schema(description = "时长（秒）")
    private Integer duration;

    @Schema(description = "分区ID")
    private Integer area;

    @Schema(description = "标签列表")
    private List<String> tags; // 标签名称列表

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;
}