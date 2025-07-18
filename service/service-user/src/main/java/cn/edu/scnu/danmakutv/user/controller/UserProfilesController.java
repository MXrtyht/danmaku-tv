package cn.edu.scnu.danmakutv.user.controller;

import cn.edu.scnu.danmakutv.common.authentication.AuthenticationSupport;
import cn.edu.scnu.danmakutv.common.response.CommonResponse;
import cn.edu.scnu.danmakutv.dto.user.CoinUpdateDTO;
import cn.edu.scnu.danmakutv.dto.user.UserProfilesDTO;
import cn.edu.scnu.danmakutv.user.service.UserProfilesService;
import cn.edu.scnu.danmakutv.vo.user.UserProfilesVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 根据用户id获取用户信息
     * @param userId
     * @return
     */
    @Operation(summary = "根据用户ID获取用户信息", description = "根据用户ID获取个人信息")
    @GetMapping("/info/{userId}")
    public CommonResponse<UserProfilesVO> getUserProfilesById(
            @PathVariable @Parameter(description = "用户ID") Long userId
    ) {
        UserProfilesVO userProfilesVO = userProfilesService.getUserProfilesByUserId(userId);
        return CommonResponse.success(userProfilesVO);
    }

    /**
     * 分页查询用户列表
     * @param nickname
     * @param page
     * @param size
     * @return
     */
    @Operation(summary = "根据昵称分页查询用户",
            description = "根据昵称模糊查询用户列表")
    @GetMapping("/list")
    public CommonResponse<Page<UserProfilesVO>> searchUsersByNickname(
            @RequestParam @Parameter(description = "昵称关键字") String nickname,
            @RequestParam(defaultValue = "1") @Parameter(description = "页码") Integer page,
            @RequestParam(defaultValue = "10") @Parameter(description = "每页数量") Integer size
    ) {
        Page<UserProfilesVO> result = userProfilesService.searchUsersByNickname(nickname, page, size);
        return CommonResponse.success(result);
    }

    @Operation(summary = "修改用户硬币数量",
            description = "内部接口，用于增加或减少用户硬币数量")
    @PostMapping("/internal/coin")
    public CommonResponse<String> updateUserCoin(
            @Valid @RequestBody @Parameter(description = "硬币修改DTO")
            CoinUpdateDTO coinUpdateDTO
    ) {
        userProfilesService.updateUserCoin(coinUpdateDTO);
        return CommonResponse.success("硬币数量更新成功");
    }
}
