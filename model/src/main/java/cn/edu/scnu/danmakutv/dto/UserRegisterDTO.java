package cn.edu.scnu.danmakutv.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegisterDTO {
    @NotBlank(message = "电话不能为空")
    @Size(max = 11, message = "号码无效")
    private String phone;

    @Email(message = "请输入正确邮箱地址")
    private String email;

    @NotBlank(message = "密码不能为空")
    private String password;
}
