package cn.edu.scnu.danmakutv.user.controller;

import cn.edu.scnu.danmakutv.common.response.CommonResponse;
import cn.edu.scnu.danmakutv.dto.UserProfilesDTO;
import cn.edu.scnu.danmakutv.user.service.UserProfilesService;
import cn.edu.scnu.danmakutv.common.authentication.AuthenticationSupport;
import cn.edu.scnu.danmakutv.vo.UserProfilesVO;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/")
public class UserProfilesController {
    @Resource
    private UserProfilesService userProfilesService;

    @Resource
    private AuthenticationSupport authenticationSupport;

    @GetMapping("/info")
    public CommonResponse<UserProfilesVO> getUserProfiles () {
        Long userId = authenticationSupport.getCurrentUserId();
        UserProfilesVO userProfilesVO = userProfilesService.getUserProfilesByUserId(userId);
        System.out.println("-------------------controller:" + userProfilesVO.getAnnouncement());
        return CommonResponse.success(userProfilesVO);
    }

    @PostMapping("/info")
    public CommonResponse<String> updateUserProfiles (@Valid @RequestBody UserProfilesDTO userProfilesDTO) {
        Long userId = authenticationSupport.getCurrentUserId();
        userProfilesService.updateUserProfiles(userId, userProfilesDTO);
        return CommonResponse.success("用户信息更新成功");
    }
}
