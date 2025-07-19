package cn.edu.scnu.danmakutv.dto.interaction;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(name = "添加视频收藏 数据传输对象")
@Data
public class AddVideoCollectionDTO {
    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "关注的视频的id")
    @NotBlank
    private Long videoId;

    @Schema(description = "收藏夹的分组id")
    // 该字段为1时 表示默认分组
    private Long groupId;

    @Schema(description = "关注时间")
    private LocalDateTime createAt; // 收藏时间
}
