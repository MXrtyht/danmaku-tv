package cn.edu.scnu.danmakutv.user.service.impl;

import cn.edu.scnu.danmakutv.common.exception.DanmakuException;
import cn.edu.scnu.danmakutv.domain.FollowGroup;
import cn.edu.scnu.danmakutv.domain.User;
import cn.edu.scnu.danmakutv.domain.UserFollow;
import cn.edu.scnu.danmakutv.domain.UserProfiles;
import cn.edu.scnu.danmakutv.dto.UserFollowDTO;
import cn.edu.scnu.danmakutv.user.mapper.UserFollowMapper;
import cn.edu.scnu.danmakutv.user.service.FollowGroupService;
import cn.edu.scnu.danmakutv.user.service.UserFollowService;
import cn.edu.scnu.danmakutv.user.service.UserProfilesService;
import cn.edu.scnu.danmakutv.user.service.UserService;
import cn.edu.scnu.danmakutv.vo.UserFollowsWithGroupVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserFollowServiceImpl extends ServiceImpl<UserFollowMapper, UserFollow> implements UserFollowService {

    @Resource
    private UserService userService;

    @Resource
    private UserProfilesService userProfilesService;

    @Resource
    private FollowGroupService followGroupService;

    /**
     * 添加用户关注
     * @param userFollowDTO 包含用户ID、被关注用户ID和分组ID
     */
    @Override
    @Transactional
    public void addUserFollow (UserFollowDTO userFollowDTO) {
        Long groupId = userFollowDTO.getGroupId();

        // 如果groupId为null或非法，则设置为默认值1
        if (groupId == null || groupId <= 0) {
            userFollowDTO.setGroupId(1L);
        } else {
            // 检查groupId是否存在
            FollowGroup followGroup = followGroupService.getById(groupId);
            if (followGroup == null) {
                throw new DanmakuException("关注分组不存在", 400);
            }
        }

        Long followId = userFollowDTO.getFollowId();
        // 检查被关注用户是否存在
        User followUser = userService.getUserById(followId);
        if (followUser == null) {
            throw new DanmakuException("被关注用户不存在", 400);
        }

        // 先删除再添加
        this.baseMapper.delete(
                new QueryWrapper<>(UserFollow.class)
                        .eq("user_id", userFollowDTO.getUserId())
                        .eq("follow_id", followId)
        );
        userFollowDTO.setCreateAt(LocalDateTime.now());

        UserFollow userFollow = new UserFollow();
        BeanUtils.copyProperties(userFollowDTO, userFollow);

        this.baseMapper.insert(userFollow);
    }

    // 先获取关注的用户列表
    // 查询所有已关注的用户信息
    // 根据关注分组进行分类
    /**
     * 获取用户关注分组和对应的用户列表
     * @param userId 用户ID
     * @return 包含关注分组和相应分组的用户信息的列表
     */
    @Override
    public List<UserFollowsWithGroupVO> getUserFollowGroups (Long userId) {
        List<UserFollowsWithGroupVO> result = new ArrayList<>();

        // 获取所有关注的用户
        List<UserFollow> userFollows = this.baseMapper.selectList(
                new QueryWrapper<>(UserFollow.class)
                        .eq("user_id", userId)
        );

        if (userFollows.isEmpty()) {
            return result;
        }

        // 获取所有关注的用户的id
        Set<Long> followUserIds = userFollows.stream()
                                             .map(UserFollow::getFollowId)
                                             .collect(Collectors.toSet());
        // 查询所有关注的用户信息
        List<UserProfiles> followUserProfiles = userProfilesService.getUserProfilesByUserIds(followUserIds.stream()
                                                                                                          .toList());

        // 获取所有关注分组
        List<FollowGroup> followGroups = followGroupService.getFollowGroupsByUserId(userId);
        followGroups.add(
                // 添加默认分组
                0, followGroupService.getById(1)
        );

        // 对关注分组进行分类
        for (FollowGroup followGroup : followGroups) {
            UserFollowsWithGroupVO userFollowsWithGroupVO = new UserFollowsWithGroupVO();

            BeanUtils.copyProperties(followGroup, userFollowsWithGroupVO);
            userFollowsWithGroupVO.setUserProfilesList(new ArrayList<>());

            // 获取当前分组下 关注的用户
            for (UserFollow userFollow : userFollows) {
                // 如果关注的用户在当前分组下
                if (userFollow.getGroupId().equals(followGroup.getId())) {
                    userFollowsWithGroupVO.getUserProfilesList().add(
                            followUserProfiles.stream()
                                              .filter(profile -> profile.getUserId().equals(userFollow.getFollowId()))
                                              .findFirst()
                                              .orElse(null)
                    );
                }
            }

            // 添加到结果列表
            result.add(userFollowsWithGroupVO);
        }

        return result;
    }

    // 获取粉丝列表

    /**
     * 获取用户的粉丝列表
     * @param userId 用户ID
     * @return 包含粉丝用户信息和是否已关注的Map
     */
    @Override
    public Map<UserProfiles, Boolean> getFans (Long userId) {
        // 查询所有粉丝
        List<UserFollow> fans = this.baseMapper.selectList(
                new QueryWrapper<>(UserFollow.class).eq("follow_id", userId)
        );

        if (fans.isEmpty()) {
            return null;
        }

        // 获取所有粉丝的id
        Set<Long> fanUserIds = fans.stream().map(UserFollow::getUserId)
                                   .collect(Collectors.toSet());

        // 通过粉丝id获取粉丝用户信息
        List<UserProfiles> fanUserProfiles = userProfilesService.getUserProfilesByUserIds(fanUserIds.stream()
                                                                                                    .toList());
        // 查看我是否关注了粉丝
        List<UserFollow> followBackList = this.baseMapper.selectList(
                new QueryWrapper<>(UserFollow.class)
                        .eq("user_id", userId)
                        .in("follow_id", fanUserIds)
        );

        // 提取我回关的粉丝id
        Set<Long> followedBackIds = followBackList.stream()
                                                  .map(UserFollow::getFollowId)
                                                  .collect(Collectors.toSet());
        // 将粉丝信息和是否回关的状态封装到Map中
        Map<UserProfiles, Boolean> result = new HashMap<>();
        for (UserProfiles profile : fanUserProfiles) {
            result.put(profile, followedBackIds.contains(profile.getUserId()));
        }

        return result;
    }
}
