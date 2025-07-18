package cn.edu.scnu.danmakutv.user.service;

import cn.edu.scnu.danmakutv.domain.user.UserFollow;
import cn.edu.scnu.danmakutv.user.mapper.UserFollowMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

// 新建 FollowRelationService.java
@Service
public class FollowRelationService {
    @Resource
    private UserFollowMapper userFollowMapper;
    
    public void transferGroupToDefault(Long userId, Long oldGroupId, Long defaultGroupId) {
        UserFollow updateEntity = new UserFollow();
        updateEntity.setGroupId(defaultGroupId);
        
        userFollowMapper.update(
            updateEntity,
            new QueryWrapper<>(UserFollow.class)
                .eq("user_id", userId)
                .eq("group_id", oldGroupId)
        );
    }
}