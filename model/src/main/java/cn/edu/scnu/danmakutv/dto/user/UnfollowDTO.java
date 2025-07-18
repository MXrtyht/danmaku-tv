package cn.edu.scnu.danmakutv.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
@Schema(description = "取消关注DTO")
public class UnfollowDTO {
    @NotNull(message = "被关注用户ID不能为空")
    @Schema(description = "被关注用户ID")
    private Long followId;
}