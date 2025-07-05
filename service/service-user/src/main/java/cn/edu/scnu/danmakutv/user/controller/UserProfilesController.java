package cn.edu.scnu.danmakutv.user.controller;

import cn.edu.scnu.danmaku.common.response.CommonResponse;
import cn.edu.scnu.danmakutv.user.service.UserProfilesService;
import cn.edu.scnu.danmakutv.user.support.AuthenticationSupport;
import cn.edu.scnu.danmakutv.vo.UserProfilesVO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/")
public class UserProfilesController {
    @Resource
    private UserProfilesService userProfilesService;

    @Resource
    private AuthenticationSupport authenticationSupport;

    @GetMapping("/info")
    public CommonResponse<UserProfilesVO> getUserProfiles() {
        Long userId = authenticationSupport.getCurrentUserId();
        UserProfilesVO userProfilesVO = userProfilesService.getUserProfilesByUserId(userId);
        return CommonResponse.success(userProfilesVO);
    }
}
