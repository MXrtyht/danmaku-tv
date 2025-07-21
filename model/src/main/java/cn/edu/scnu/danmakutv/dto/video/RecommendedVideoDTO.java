package cn.edu.scnu.danmakutv.dto.video;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Schema(name = "推荐视频响应DTO")
@Data
public class RecommendedVideoDTO {
    @Schema(description = "视频ID")
    private Long id;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "视频URL")
    private String videoUrl;

    @Schema(description = "封面URL")
    private String coverUrl;

    @Schema(description = "视频类型, 0自制, 1转载")
    private boolean type;

    @Schema(description = "时长（秒）")
    private Integer duration;

    @Schema(description = "分区ID")
    private Integer area;

    @Schema(description = "标签列表")
    private List<String> tags;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;
}