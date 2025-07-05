package cn.edu.scnu.danmakutv.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserLoginDTO {

    @NotBlank(message = "手机号不能为空")
    private String phone;

    private String password;
}
