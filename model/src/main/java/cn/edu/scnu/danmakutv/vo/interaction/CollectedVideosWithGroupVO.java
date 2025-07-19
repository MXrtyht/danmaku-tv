package cn.edu.scnu.danmakutv.vo.interaction;

import cn.edu.scnu.danmakutv.domain.user.UserProfiles;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(name = "用户收藏分组视图对象")
@Data
public class CollectedVideosWithGroupVO {
    @Schema(description = "分组id")
    private Long id;

    @Schema(description = "用户id")
    private Long userId; // 用户ID

    @Schema(description = "分组名称")
    private String name; // 分组名称

    @Schema(description = "分组创建时间")
    private String createdAt; // 分组创建时间

    @Schema(description = "分组更新时间")
    private String updatedAt; // 分组更新时间

    @Schema(description = "分组视频id列表")
    private List<Long> videoIdList; // 分组内视频id列表
}
