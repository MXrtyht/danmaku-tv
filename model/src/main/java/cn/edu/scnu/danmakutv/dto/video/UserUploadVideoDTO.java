package cn.edu.scnu.danmakutv.dto.video;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Schema(name = "用户上传视频 DTO")
@Data
public class UserUploadVideoDTO {

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "视频url")
    private String videoUrl;

    @Schema(description = "封面图片url")
    private String coverUrl;

    @Schema(description = "视频标题")
    private String title;

    @Schema(description = "视频简介")
    private String description;

    @Schema(description = "类型, 0自制, 1转载")
    private boolean type; // true:原创，false:转载

    @Schema(description = "视频时常 单位秒")
    private Integer duration;

    @Schema(description = "视频分区")
    private Integer area;

    @Schema(description = "视频标签id列表")
    @Size(min = 1, max = 10, message = "标签数量必须在1到10之间")
    private List<Long> tags; // 标签id列表
}
