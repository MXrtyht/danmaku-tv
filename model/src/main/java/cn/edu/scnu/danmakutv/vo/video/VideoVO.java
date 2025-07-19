package cn.edu.scnu.danmakutv.vo.video;

import cn.edu.scnu.danmakutv.domain.video.VideoTag;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Schema(name = "视频视图")
@Data
public class VideoVO {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "用户id")
    private String userId;

    @Schema(description = "视频url")
    private String videoUrl;

    @Schema(description = "封面url")
    private String coverUrl;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "简介")
    private  String description;

    @Schema(description = "类型, 0自制, 1转载")
    private boolean type;

    @Schema(description = "视频时常 单位秒")
    private int duration;

    @Schema(description = "视频分区")
    private Integer area;

    @Schema(description = "视频标签列表")
    private List<VideoTag> tags;

    @Schema(description = "创建时间")
    private LocalDateTime createAt;

    @Schema(description = "更新时间")
    private LocalDateTime updateAt;
}
