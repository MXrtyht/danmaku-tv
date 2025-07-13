package cn.edu.scnu.danmakutv.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Schema(name = "用户登录数据传输对象")
@Getter
public class UserLoginDTO {

    @Schema(
            description = "手机号",
            example = "1832040343"
    )
    @NotBlank(message = "手机号不能为空")
    private String phone;

    @Schema(
            description = "密码,前端传入RSA加密后的密文",
            example = "G6X+JjGPhhp/wkPMWvxrIaasTLa6IiRpmfmdfnxepDfwc7l61wvjfzvIk9me/4q7k+oAOt9PzibwzkV4QNghtXfcHhQwLTJ4jHIEEQZfsDd3zgojGHf8oOmxz5031WK2aMR6elly7+mr1jPDy7ObEAvNpiVkQLchQ7xupfA+Krk="
    )
    @NotBlank(message = "密码不能为空")
    private String password;
}
