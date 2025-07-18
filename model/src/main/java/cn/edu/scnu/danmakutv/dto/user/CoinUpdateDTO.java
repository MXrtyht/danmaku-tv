package cn.edu.scnu.danmakutv.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
@Schema(description = "硬币修改DTO")
public class CoinUpdateDTO {
    @NotNull(message = "用户ID不能为空")
    @Schema(description = "用户ID")
    private Long userId;
    
    @NotNull(message = "硬币变化量不能为空")
    @Schema(description = "硬币变化量（正数增加，负数减少）")
    private Integer changeAmount;
    
    @Schema(description = "操作原因")
    private String reason;
}