package cn.edu.scnu.danmakutv.user.controller;

import cn.edu.scnu.common.utils.RSAUtil;
import cn.edu.scnu.danmakutv.common.authentication.AuthenticationSupport;
import cn.edu.scnu.danmakutv.common.response.CommonResponse;
import cn.edu.scnu.danmakutv.dto.UserLoginDTO;
import cn.edu.scnu.danmakutv.dto.UserRegisterDTO;
import cn.edu.scnu.danmakutv.user.service.UserService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @Resource
    private AuthenticationSupport authenticationSupport;

    // 获取RSA公钥
    @GetMapping("/rsa-pks")
    public CommonResponse<String> getRsaPublicKey () {
        String rsaPublicKey = RSAUtil.getPublicKeyStr();
        return CommonResponse.success(rsaPublicKey);
    }

    // 注册
    @PostMapping("/register")
    public CommonResponse<String> registerUser (@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        userService.registerUser(userRegisterDTO);
        return CommonResponse.success("注册成功");
    }

    // 登录
    @PostMapping("/login")
    public CommonResponse<String> loginUser (@Valid @RequestBody UserLoginDTO userLoginDTO) {
        String token = userService.loginUser(userLoginDTO);
        return CommonResponse.success(token);
    }

}
