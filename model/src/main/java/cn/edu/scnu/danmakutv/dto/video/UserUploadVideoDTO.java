package cn.edu.scnu.danmakutv.dto.video;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Schema(name = "用户上传视频 DTO")
@Data
public class UserUploadVideoDTO {

    @Schema(description = "用户id")
    Long userId;

    @Schema(description = "视频url")
    String videoUrl;

    @Schema(description = "封面图片url")
    String coverUrl;

    @Schema(description = "视频标题")
    String title;

    @Schema(description = "类型, 0自制, 1转载")
    boolean type; // true:原创，false:转载

    @Schema(description = "视频时常 单位秒")
    Integer duration;

    @Schema(description = "视频分区")
    Integer area;

    @Schema(description = "视频标签id列表")
    @Size(min = 1, max = 10, message = "标签数量必须在1到10之间")
    List<Long> tags; // 标签id列表
}
