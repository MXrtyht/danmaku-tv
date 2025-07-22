package cn.edu.scnu.danmakutv.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "添加硬币传输信息")
@Data
public class UpdateUserCoinDTO {
    @Schema(description = "用户di")
    private Long userId; // 用户ID

    @Schema(description = "要新增/减少的硬币数量")
    private Integer coin;

    @Schema(description = "操作类型，ture增加硬币，false减少硬币")
    private Boolean isAdd;
}
