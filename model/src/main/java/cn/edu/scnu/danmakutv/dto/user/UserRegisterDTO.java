package cn.edu.scnu.danmakutv.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(name = "用户注册数据传输对象")
@Data
public class UserRegisterDTO {

    @Schema(
            description = "电话号码",
            example = "13812345678"
    )
    @NotBlank(message = "电话不能为空")
    @Size(max = 11, message = "号码无效")
    private String phone;

    @Schema(
            description = "邮箱, 格式要符合邮箱规范",
            example = "4739285@gmail.com"
    )
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "请输入正确邮箱地址")
    private String email;

    @Schema(
            description = "密码, 前端传加密后的",
            example = "G6X+JjGPhhp/wkPMWvxrIaasTLa6IiRpmfmdfnxepDfwc7l61wvjfzvIk9me/4q7k+oAOt9PzibwzkV4QNghtXfcHhQwLTJ4jHIEEQZfsDd3zgojGHf8oOmxz5031WK2aMR6elly7+mr1jPDy7ObEAvNpiVkQLchQ7xupfA+Krk="
    )
    @NotBlank(message = "密码不能为空")
    private String password;
}
