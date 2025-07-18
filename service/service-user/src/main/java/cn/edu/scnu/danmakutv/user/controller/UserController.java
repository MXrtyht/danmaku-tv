package cn.edu.scnu.danmakutv.user.controller;

import cn.edu.scnu.common.utils.RSAUtil;
import cn.edu.scnu.danmakutv.common.response.CommonResponse;
import cn.edu.scnu.danmakutv.dto.user.UserLoginDTO;
import cn.edu.scnu.danmakutv.dto.user.UserRegisterDTO;
import cn.edu.scnu.danmakutv.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Tag(name = "用户操作相关接口")
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    // 获取RSA公钥
    @Operation(
            summary = "获取RSA公钥",
            description = "用于前端加密用户密码"
    )
    @GetMapping("/rsa-pks")
    public CommonResponse<String> getRsaPublicKey () {
        String rsaPublicKey = RSAUtil.getPublicKeyStr();
        return CommonResponse.success(rsaPublicKey);
    }

    // 注册
    @Operation(
            summary = "用户注册",
            description = "前端使用RSA加密用户密码后提交"
    )
    @PostMapping("/register")
    public CommonResponse<String> registerUser (
            @Valid @RequestBody @Parameter(description = "用户注册信息，包括手机号, 邮箱, 密码等")
            UserRegisterDTO userRegisterDTO
    ) {
        userService.registerUser(userRegisterDTO);
        return CommonResponse.success("注册成功");
    }

    // 登录
    @Operation(
            summary = "用户登录",
            description = "登录成功后返回JWT Token"
    )
    @PostMapping("/login")
    public CommonResponse<String> loginUser (
            @Valid @RequestBody @Parameter(description = "用户登录信息，包括手机号, 密码")
            UserLoginDTO userLoginDTO
    ) {
        String token = userService.loginUser(userLoginDTO);
        return CommonResponse.success(token);
    }

}
