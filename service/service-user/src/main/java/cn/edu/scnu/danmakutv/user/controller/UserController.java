package cn.edu.scnu.danmakutv.user.controller;

import cn.edu.scnu.common.utils.RSAUtil;
import cn.edu.scnu.danmaku.common.response.CommonResponse;
import cn.edu.scnu.danmakutv.user.service.UserService;
import cn.edu.scnu.danmakutv.vo.authentication.UserRegisterVO;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/")
public class UserController {
    @Resource
    private UserService userService;

    // 获取RSA公钥
    @GetMapping("/rsa-pks")
    public CommonResponse<String> getRsaPublicKey () {
        String rsaPublicKey = RSAUtil.getPublicKeyStr();
        return CommonResponse.success(rsaPublicKey);
    }

    // 注册
    @PostMapping("/register")
    public CommonResponse<String> registerUser (@Valid @RequestBody UserRegisterVO userRegisterVO) {
        userService.registerUser(userRegisterVO);
        return CommonResponse.success("注册成功");
    }
}
