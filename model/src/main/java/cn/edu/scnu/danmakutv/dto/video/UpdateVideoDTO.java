package cn.edu.scnu.danmakutv.dto.video;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Schema(name = "修改视频信息DTO")
@Data
public class UpdateVideoDTO {
    @Schema(description = "视频ID")
    private Long videoId; // 视频ID

    @Schema(description = "视频标题")
    private String title;

    @Schema(description = "封面URL")
    private String coverUrl;

    @Schema(description = "类型 (true:自制, false:转载)")
    private Boolean type;

    @Schema(description = "视频分区ID")
    private Integer area;

    @Schema(description = "视频标签ID列表")
    @Size(max = 10, message = "标签数量不能超过10个")
    private List<String> tags; // 标签ID列表
}