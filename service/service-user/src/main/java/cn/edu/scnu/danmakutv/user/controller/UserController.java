package cn.edu.scnu.danmakutv.user.controller;

import cn.edu.scnu.common.utils.RSAUtil;
import cn.edu.scnu.danmaku.common.CommonResponse;
import cn.edu.scnu.danmakutv.user.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/")
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("/rsa-pks")
    public CommonResponse<String> getRsaPublicKey(){
        String rsaPublicKey = RSAUtil.getPublicKeyStr();
        return CommonResponse.success(rsaPublicKey);
    }
}
