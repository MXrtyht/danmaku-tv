package cn.edu.scnu.danmakutv.user.controller;

import cn.edu.scnu.danmakutv.common.authentication.AuthenticationSupport;
import cn.edu.scnu.danmakutv.common.response.CommonResponse;
import cn.edu.scnu.danmakutv.common.response.StatusCodeEnum;
import cn.edu.scnu.danmakutv.domain.user.UserMoments;
import cn.edu.scnu.danmakutv.user.service.UserMomentsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;

import java.util.List;

@CrossOrigin
@Tag(name = "用户动态相关接口")
@RestController
@RequestMapping("/user")
public class UserMomentsController {
    @Resource
    private UserMomentsService userMomentsService;

    @Autowired
    private AuthenticationSupport authenticationSupport;

    @PostMapping("/user-moments")
    public CommonResponse<String> addUserMoments(@RequestBody UserMoments userMoments) throws Exception {
        Long userId = authenticationSupport.getCurrentUserId();
        userMoments.setUserId(userId);
        userMomentsService.addUserMoments(userMoments);
        return CommonResponse.success("添加用户动态成功");
    }

    @GetMapping("/user-subscribed-moments")
    public CommonResponse<List<UserMoments>> getUserMomentsSubscribedMoments() throws Exception {
        Long userId = authenticationSupport.getCurrentUserId();
        List<UserMoments> list = userMomentsService.getUserSubscribedMoments(userId);
        return CommonResponse.build(list, StatusCodeEnum.SUCCESS);
    }
}
