package cn.edu.scnu.danmakutv.dto.user;

import cn.edu.scnu.danmakutv.enums.GenderType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Schema(name = "用户个人资料 DTO")
@Data
public class UserProfilesDTO {

    @Schema(description = "用户昵称")
    private String nickname;

    @Schema(
            description = "用户性别, 0-女, 1-男, 2-未知",
            example = "1"
    )
    private GenderType gender;

    @Schema(
            description = "用户生日",
            example = "2000-01-01"
    )
    private LocalDate birthday;

    @Schema(
            description = "个性签名",
            example = "Hello"
    )
    private String sign;

    @Schema(
            description = "主页公告",
            example = "Welcome"
    )
    private String announcement;

    @Schema(description = "用户头像url")
    private String avatar;

    @Schema(description = "用户硬币数量")
    private Integer coin;
}
