package cn.edu.scnu.danmakutv.user.controller;

import cn.edu.scnu.danmakutv.common.authentication.AuthenticationSupport;
import cn.edu.scnu.danmakutv.common.response.CommonResponse;
import cn.edu.scnu.danmakutv.domain.user.FollowGroup;
import cn.edu.scnu.danmakutv.domain.user.UserProfiles;
import cn.edu.scnu.danmakutv.dto.user.CreateFollowGroupDTO;
import cn.edu.scnu.danmakutv.dto.user.UserFollowDTO;
import cn.edu.scnu.danmakutv.user.service.FollowGroupService;
import cn.edu.scnu.danmakutv.user.service.UserFollowService;
import cn.edu.scnu.danmakutv.vo.user.UserFollowsWithGroupVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "用户关注 相关接口")
@RestController
@RequestMapping("/user")
public class UserFollowController {
    @Resource
    private UserFollowService userFollowService;

    @Resource
    private FollowGroupService followGroupService;

    @Resource
    private AuthenticationSupport authenticationSupport;

    /**
     * 关注用户
     * @param userFollowDTO 包含被关注用户ID和分组ID
     * @return 响应
     */
    @Operation(
            summary = "关注用户",
            description = "关注指定用户并添加到分组, groupId = 1 为默认分组"
    )
    @PostMapping("/follow")
    public CommonResponse<String> followUser (
            @Valid @RequestBody @Parameter(description = "用户关注DTO，包括被关注用户ID和分组ID")
            UserFollowDTO userFollowDTO
    ) {
        Long userId = authenticationSupport.getCurrentUserId();
        userFollowDTO.setUserId(userId);

        userFollowService.addUserFollow(userFollowDTO);
        return CommonResponse.success("关注成功");
    }

    /**
     * 获取用户关注列表
     * @return 包含用户关注分组和用户列表
     */
    @Operation(
            summary = "获取用户关注列表",
            description = "获取当前用户的所有关注分组和对应的用户列表"
    )
    @GetMapping("/follows")
    public CommonResponse<List<UserFollowsWithGroupVO>> getUserFollows () {
        Long userId = authenticationSupport.getCurrentUserId();

        List<UserFollowsWithGroupVO> followGroups = userFollowService.getUserFollowGroups(userId);
        return CommonResponse.success(followGroups);
    }

    /**
     * 获取用户粉丝列表
     * @return 包含粉丝用户信息和是否已关注的Map
     */
    @Operation(
            summary = "获取用户粉丝列表",
            description = "获取当前用户的所有粉丝及是否已关注"
    )
    @GetMapping("/fans")
    public CommonResponse<Map<UserProfiles, Boolean>> getFans () {
        Long userId = authenticationSupport.getCurrentUserId();

        Map<UserProfiles, Boolean> fans = userFollowService.getFans(userId);
        return CommonResponse.success(fans);
    }

    /**
     * 创建关注分组
     * @param createFollowGroupDTO 包含用户id和分组名称
     * @return 新创建的分组ID
     */
    @Operation(summary = "创建关注分组")
    @PostMapping("/follow-group")
    public CommonResponse<Long> createFollowGroup (
            @Valid @RequestBody @Parameter(description = "创建关注分组DTO，包括用户ID和分组名称")
            CreateFollowGroupDTO createFollowGroupDTO
    ) {
        Long userId = authenticationSupport.getCurrentUserId();
        createFollowGroupDTO.setUserId(userId);
        Long groupId = followGroupService.createFollowGroup(createFollowGroupDTO);
        return CommonResponse.success(groupId);
    }

    /**
     * 获取用户的关注分组列表
     * @return 关注分组列表
     */
    @Operation(summary = "获取当前用户关注分组列表")
    @GetMapping("/follow-groups")
    public CommonResponse<List<FollowGroup>> getFollowGroups () {
        Long userId = authenticationSupport.getCurrentUserId();
        List<FollowGroup> followGroups = followGroupService.getFollowGroupsByUserId(userId);
        return CommonResponse.success(followGroups);
    }
}
