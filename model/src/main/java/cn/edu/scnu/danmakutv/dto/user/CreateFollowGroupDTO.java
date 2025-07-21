package cn.edu.scnu.danmakutv.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(name = "创建关注分组DTO")
@Data
public class CreateFollowGroupDTO {
    @Schema(description = "用户ID")
    Long userId;
    @Schema(description = "分组名称")
    String name; // 分组名称
}
