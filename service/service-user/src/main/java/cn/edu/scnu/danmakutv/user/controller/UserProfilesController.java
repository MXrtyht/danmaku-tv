package cn.edu.scnu.danmakutv.user.controller;

import cn.edu.scnu.danmakutv.common.authentication.AuthenticationSupport;
import cn.edu.scnu.danmakutv.common.response.CommonResponse;
import cn.edu.scnu.danmakutv.dto.UserProfilesDTO;
import cn.edu.scnu.danmakutv.user.service.UserProfilesService;
import cn.edu.scnu.danmakutv.vo.UserProfilesVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@Api(tags = "用户信息操作相关接口")
@RestController
@RequestMapping("/user")
public class UserProfilesController {
    @Resource
    private UserProfilesService userProfilesService;

    @Resource
    private AuthenticationSupport authenticationSupport;

    /**
     * 获取用户信息
     * @return 用户信息
     */
    @ApiOperation(value = "获取用户信息", notes = "根据当前登录用户的ID获取个人信息")
    @GetMapping("/info")
    public CommonResponse<UserProfilesVO> getUserProfiles () {
        Long userId = authenticationSupport.getCurrentUserId();
        UserProfilesVO userProfilesVO = userProfilesService.getUserProfilesByUserId(userId);
        return CommonResponse.success(userProfilesVO);
    }

    /**
     * 更新用户信息
     * @param userProfilesDTO 包含用户信息的DTO
     * @return 响应消息
     */
    @ApiOperation(value = "更新用户信息")
    @PostMapping("/info")
    public CommonResponse<String> updateUserProfiles (@Valid @RequestBody UserProfilesDTO userProfilesDTO) {
        Long userId = authenticationSupport.getCurrentUserId();
        userProfilesService.updateUserProfiles(userId, userProfilesDTO);
        return CommonResponse.success("用户信息更新成功");
    }
}
