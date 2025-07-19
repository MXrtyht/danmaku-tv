package cn.edu.scnu.danmakutv.user.controller;

import cn.edu.scnu.danmakutv.common.authentication.AuthenticationSupport;
import cn.edu.scnu.danmakutv.common.response.CommonResponse;
import cn.edu.scnu.danmakutv.domain.user.UserProfiles;
import cn.edu.scnu.danmakutv.dto.user.UserProfilesDTO;
import cn.edu.scnu.danmakutv.user.service.UserProfilesService;
import cn.edu.scnu.danmakutv.vo.user.UserProfilesVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Tag(name = "用户信息操作相关接口")
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
    @Operation(summary = "获取用户信息", description = "根据当前登录用户的ID获取个人信息")
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
    @Operation(summary = "更新用户信息")
    @PostMapping("/info")
    public CommonResponse<String> updateUserProfiles (
            @Valid @RequestBody @Parameter(description = "用户信息DTO")
            UserProfilesDTO userProfilesDTO
    ) {
        Long userId = authenticationSupport.getCurrentUserId();
        userProfilesService.updateUserProfiles(userId, userProfilesDTO);
        return CommonResponse.success("用户信息更新成功");
    }

    @Operation(summary = "批量获取用户信息")
    @PostMapping("/batch")
    public CommonResponse<List<UserProfiles>> getUserProfilesByUserIds(
            @RequestBody @Parameter(description = "用户ID列表") List<Long> userIds
    ) {
        List<UserProfiles> userProfiles = userProfilesService.getUserProfilesByUserIds(userIds);
        return CommonResponse.success(userProfiles);
    }
}
