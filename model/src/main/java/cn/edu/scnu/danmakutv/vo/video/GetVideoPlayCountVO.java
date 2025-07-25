package cn.edu.scnu.danmakutv.vo.video;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;


@Schema(name="视频播放VO")
@Data
@AllArgsConstructor
public class GetVideoPlayCountVO {
    @Schema(description = "视频ID")
    private Long videoId;

    @Schema(description = "播放次数")
    private Long count;
}
