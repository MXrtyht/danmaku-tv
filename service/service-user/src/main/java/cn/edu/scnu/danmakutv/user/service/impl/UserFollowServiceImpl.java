package cn.edu.scnu.danmakutv.user.service.impl;

import cn.edu.scnu.danmakutv.common.exception.DanmakuException;
import cn.edu.scnu.danmakutv.domain.FollowGroup;
import cn.edu.scnu.danmakutv.domain.User;
import cn.edu.scnu.danmakutv.domain.UserFollow;
import cn.edu.scnu.danmakutv.dto.UserFollowDTO;
import cn.edu.scnu.danmakutv.user.mapper.UserFollowMapper;
import cn.edu.scnu.danmakutv.user.service.FollowGroupService;
import cn.edu.scnu.danmakutv.user.service.UserFollowService;
import cn.edu.scnu.danmakutv.user.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UserFollowServiceImpl extends ServiceImpl<UserFollowMapper, UserFollow> implements UserFollowService {

    @Resource
    private UserService userService;

    @Resource
    private FollowGroupService followGroupService;

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
        User followUser =  userService.getUserById(followId);
        if( followUser == null) {
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
}
