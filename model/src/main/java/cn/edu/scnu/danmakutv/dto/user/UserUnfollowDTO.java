package cn.edu.scnu.danmakutv.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "用户关注信息")
@Data
public class UserUnfollowDTO {

    @Schema(description = "用户di")
    private Long userId; // 用户ID

    @Schema(description = "被关注用户id")
    private Long followId; // 被关注用户ID

    @Schema(description = "关注分组id")
    // 该字段为1时 表示默认分组
    private Long groupId; // 关注分组ID
}