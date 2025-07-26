package cn.edu.scnu.danmakutv.user.service.impl;

import cn.edu.scnu.danmakutv.common.exception.DanmakuException;
import cn.edu.scnu.danmakutv.domain.user.FollowGroup;
import cn.edu.scnu.danmakutv.domain.user.User;
import cn.edu.scnu.danmakutv.domain.user.UserFollow;
import cn.edu.scnu.danmakutv.domain.user.UserProfiles;
import cn.edu.scnu.danmakutv.dto.user.UserFollowDTO;
import cn.edu.scnu.danmakutv.dto.user.UserUnfollowDTO;
import cn.edu.scnu.danmakutv.user.mapper.UserFollowMapper;
import cn.edu.scnu.danmakutv.user.service.FollowGroupService;
import cn.edu.scnu.danmakutv.user.service.UserFollowService;
import cn.edu.scnu.danmakutv.user.service.UserProfilesService;
import cn.edu.scnu.danmakutv.user.service.UserService;
import cn.edu.scnu.danmakutv.vo.user.UserFanDTO;
import cn.edu.scnu.danmakutv.vo.user.UserFollowsWithGroupVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
     *
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
     *
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
        // followGroups.add(
        //         // 添加默认分组
        //         0, followGroupService.getById(1)
        // );

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
     *
     * @param userId 用户ID
     * @return 包含粉丝用户信息和是否已关注的Map
     */
    @Override
    public List<UserFanDTO> getFans (Long userId) {
        // 查询所有粉丝
        List<UserFollow> fans = this.baseMapper.selectList(
                new QueryWrapper<>(UserFollow.class).eq("follow_id", userId)
        );

        if (fans.isEmpty()) {
            return new ArrayList<>(); // 返回空列表而不是null
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

        // 将粉丝信息和是否回关的状态封装到UserFanDTO中
        List<UserFanDTO> result = new ArrayList<>();
        for (UserProfiles profile : fanUserProfiles) {
            UserFanDTO fanDTO = new UserFanDTO();
            fanDTO.setUserProfiles(profile);
            fanDTO.setIsFollowBack(followedBackIds.contains(profile.getUserId()));
            result.add(fanDTO);
        }

        return result;
    }

    /**
     * 获取用户的总关注数量
     *
     * @param userId 用户ID
     * @return 总关注数量
     */
    @Override
    public Long getTotalFollowCount (Long userId) {
        // 查询用户的关注数量
        return this.baseMapper.selectCount(
                new QueryWrapper<>(UserFollow.class).eq("user_id", userId)
        );
    }

    /**
     * 查询用户粉丝数量
     *
     * @param userId 用户id
     * @return 粉丝数量
     */
    @Override
    public Long getTotalFansCount (Long userId) {
        // 查询用户的粉丝数量
        return this.baseMapper.selectCount(
                new QueryWrapper<>(UserFollow.class).eq("follow_id", userId)
        );
    }

    /**
     * 移除已关注的用户
     *
     * @param unfollowDTO 包含用户ID和被关注用户ID
     */
    @Override
    public void removeUserFollow (@Valid UserUnfollowDTO unfollowDTO) {
        Long userId = unfollowDTO.getUserId();
        Long followId = unfollowDTO.getFollowId();

        UserFollow userFollow = this.baseMapper.selectOne(
                new QueryWrapper<>(UserFollow.class)
                        .eq("user_id", userId)
                        .eq("follow_id", followId)
        );
        if (userFollow == null) {
            throw new DanmakuException("关注关系不存在", 400);
        }
        // 删除关注关系
        this.baseMapper.delete(
                new QueryWrapper<>(UserFollow.class)
                        .eq("user_id", userId)
                        .eq("follow_id", followId)
        );
    }

    @Override
    public void deleteFollowGroup (Long userId, Long groupId) {
        // 先取关分组下的所有用户
        this.baseMapper.delete(
                new QueryWrapper<>(UserFollow.class)
                        .eq("user_id", userId)
                        .eq("group_id", groupId)
        );

        // 删除分组
        boolean isDeleted = followGroupService.removeById(groupId);
        if (!isDeleted) {
            throw new DanmakuException("删除关注分组失败，可能分组不存在", 400);
        }
    }

    @Override
    public Long getFansCountById (Long userId) {
        // 查询用户的粉丝数量
        return this.baseMapper.selectCount(
                new QueryWrapper<>(UserFollow.class).eq("follow_id", userId)
        );
    }

    @Override
    public boolean isFollowed (Long userId, Long followId) {
        // 查询用户是否关注了指定用户
        UserFollow userFollow = this.baseMapper.selectOne(
                new QueryWrapper<>(UserFollow.class)
                        .eq("user_id", userId)
                        .eq("follow_id", followId)
        );
        return userFollow != null;
    }

    @Override
    public List<UserProfiles> getFollowGroupUsers (Long userId, Long groupId) {
        // 查询指定分组下的关注用户
        List<UserFollow> userFollows = this.baseMapper.selectList(
                new QueryWrapper<>(UserFollow.class)
                        .eq("user_id", userId)
                        .eq("group_id", groupId)
        );

        if (userFollows.isEmpty()) {
            return new ArrayList<>(); // 返回空列表而不是null
        }

        // 获取所有关注用户的id
        Set<Long> followUserIds = userFollows.stream()
                                             .map(UserFollow::getFollowId)
                                             .collect(Collectors.toSet());

        // 通过关注用户id获取用户信息
        return userProfilesService.getUserProfilesByUserIds(followUserIds.stream()
                                                                          .toList());
    }
}
