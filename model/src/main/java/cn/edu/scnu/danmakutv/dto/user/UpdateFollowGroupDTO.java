package cn.edu.scnu.danmakutv.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
@Schema(description = "更新关注分组信息DTO")
public class UpdateFollowGroupDTO {
    @NotNull(message = "分组ID不能为空")
    @Schema(description = "分组ID")
    private Long id;
    
    @Schema(description = "分组名称")
    private String name;
}