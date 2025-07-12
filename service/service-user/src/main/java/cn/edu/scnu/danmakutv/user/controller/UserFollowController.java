package cn.edu.scnu.danmakutv.user.controller;

import cn.edu.scnu.danmakutv.common.authentication.AuthenticationSupport;
import cn.edu.scnu.danmakutv.common.response.CommonResponse;
import cn.edu.scnu.danmakutv.domain.FollowGroup;
import cn.edu.scnu.danmakutv.domain.UserProfiles;
import cn.edu.scnu.danmakutv.dto.CreateFollowGroupDTO;
import cn.edu.scnu.danmakutv.dto.UserFollowDTO;
import cn.edu.scnu.danmakutv.user.service.FollowGroupService;
import cn.edu.scnu.danmakutv.user.service.UserFollowService;
import cn.edu.scnu.danmakutv.vo.UserFollowsWithGroupVO;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserFollowController {
    @Resource
    private UserFollowService userFollowService;

    @Resource
    private FollowGroupService followGroupService;

    @Resource
    private AuthenticationSupport authenticationSupport;

    @PostMapping("/follows")
    public CommonResponse<String> followUser (@Valid @RequestBody UserFollowDTO userFollowDTO) {
        Long userId = authenticationSupport.getCurrentUserId();
        userFollowDTO.setUserId(userId);

        userFollowService.addUserFollow(userFollowDTO);
        return CommonResponse.success("关注成功");
    }

    @GetMapping("/follows")
    public CommonResponse<List<UserFollowsWithGroupVO>> getUserFollows () {
        Long userId = authenticationSupport.getCurrentUserId();

        List<UserFollowsWithGroupVO> followGroups = userFollowService.getUserFollowGroups(userId);
        return CommonResponse.success(followGroups);
    }

    // 获取粉丝列表
    @GetMapping("/fans")
    public CommonResponse<Map<UserProfiles, Boolean>> getFans(){
        Long userId = authenticationSupport.getCurrentUserId();

        Map<UserProfiles, Boolean> fans = userFollowService.getFans(userId);
        return CommonResponse.success(fans);
    }

    // 新建用户关注分组
    @PostMapping("/follow-group")
    public CommonResponse<Long> createFollowGroup(@Valid @RequestBody CreateFollowGroupDTO createFollowGroupDTO) {
        Long userId = authenticationSupport.getCurrentUserId();
        createFollowGroupDTO.setUserId(userId);
        Long groupId = followGroupService.createFollowGroup(createFollowGroupDTO);
        return CommonResponse.success(groupId);
    }
}
