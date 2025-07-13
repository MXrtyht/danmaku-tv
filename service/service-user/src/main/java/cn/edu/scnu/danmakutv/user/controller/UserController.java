package cn.edu.scnu.danmakutv.user.controller;

import cn.edu.scnu.common.utils.RSAUtil;
import cn.edu.scnu.danmakutv.common.authentication.AuthenticationSupport;
import cn.edu.scnu.danmakutv.common.response.CommonResponse;
import cn.edu.scnu.danmakutv.dto.UserLoginDTO;
import cn.edu.scnu.danmakutv.dto.UserRegisterDTO;
import cn.edu.scnu.danmakutv.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@Api(tags = "{用户相关接口}")
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @Resource
    private AuthenticationSupport authenticationSupport;

    // 获取RSA公钥
    @ApiOperation(
            value = "获取RSA公钥",
            notes = "用于前端加密用户密码"
    )
    @GetMapping("/rsa-pks")
    public CommonResponse<String> getRsaPublicKey () {
        String rsaPublicKey = RSAUtil.getPublicKeyStr();
        return CommonResponse.success(rsaPublicKey);
    }

    // 注册
    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public CommonResponse<String> registerUser (@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        userService.registerUser(userRegisterDTO);
        return CommonResponse.success("注册成功");
    }

    // 登录
    @ApiOperation(
            value = "用户登录",
            notes = "登录成功后返回JWT Token"
    )
    @PostMapping("/login")
    public CommonResponse<String> loginUser (@Valid @RequestBody UserLoginDTO userLoginDTO) {
        String token = userService.loginUser(userLoginDTO);
        return CommonResponse.success(token);
    }

}
