package cn.edu.scnu.danmakutv.user.controller;

import cn.edu.scnu.danmakutv.common.authentication.AuthenticationSupport;
import cn.edu.scnu.danmakutv.common.response.CommonResponse;
import cn.edu.scnu.danmakutv.domain.user.FollowGroup;
import cn.edu.scnu.danmakutv.domain.user.UserProfiles;
import cn.edu.scnu.danmakutv.dto.user.CreateFollowGroupDTO;
import cn.edu.scnu.danmakutv.dto.user.UnfollowDTO;
import cn.edu.scnu.danmakutv.dto.user.UpdateFollowGroupDTO;
import cn.edu.scnu.danmakutv.dto.user.UserFollowDTO;
import cn.edu.scnu.danmakutv.user.service.FollowGroupService;
import cn.edu.scnu.danmakutv.user.service.UserFollowService;
import cn.edu.scnu.danmakutv.vo.user.UserFollowsWithGroupVO;
import cn.edu.scnu.danmakutv.vo.user.UserProfilesVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

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

    @Operation(summary = "修改关注分组信息")
    @PutMapping("/follow-group")
    public CommonResponse<String> updateFollowGroup(
            @Valid @RequestBody @Parameter(description = "更新关注分组DTO")
            UpdateFollowGroupDTO updateFollowGroupDTO
    ) {
        Long userId = authenticationSupport.getCurrentUserId();
        followGroupService.updateFollowGroup(userId, updateFollowGroupDTO);
        return CommonResponse.success("分组信息更新成功");
    }

    @Operation(summary = "删除关注分组")
    @DeleteMapping("/follow-group/{id}")
    public CommonResponse<String> deleteFollowGroup(
            @PathVariable @Parameter(description = "分组ID") Long id
    ) {
        Long userId = authenticationSupport.getCurrentUserId();
        followGroupService.deleteFollowGroup(userId, id);
        return CommonResponse.success("分组删除成功");
    }

    @Operation(summary = "取消关注用户")
    @PostMapping("/unfollow")
    public CommonResponse<String> unfollowUser(
            @Valid @RequestBody @Parameter(description = "取消关注DTO")
            UnfollowDTO unfollowDTO
    ) {
        Long userId = authenticationSupport.getCurrentUserId();
        userFollowService.unfollow(userId, unfollowDTO.getFollowId());
        return CommonResponse.success("取消关注成功");
    }

    @Operation(summary = "根据分组ID获取关注用户列表",
            description = "分页获取指定分组中的关注用户列表")
    @GetMapping("/follows/{groupId}")
    public CommonResponse<Page<UserProfilesVO>> getFollowsByGroupId(
            @PathVariable @Parameter(description = "分组ID") Long groupId,
            @RequestParam(defaultValue = "1") @Parameter(description = "页码") Integer page,
            @RequestParam(defaultValue = "10") @Parameter(description = "每页数量") Integer size
    ) {
        Long userId = authenticationSupport.getCurrentUserId();
        Page<UserProfilesVO> result = userFollowService.getFollowsByGroupId(userId, groupId, page, size);
        return CommonResponse.success(result);
    }
}
