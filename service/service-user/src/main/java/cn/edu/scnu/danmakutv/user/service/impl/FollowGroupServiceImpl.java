package cn.edu.scnu.danmakutv.user.service.impl;

import cn.edu.scnu.danmakutv.common.exception.DanmakuException;
import cn.edu.scnu.danmakutv.domain.user.FollowGroup;
import cn.edu.scnu.danmakutv.dto.user.CreateFollowGroupDTO;
import cn.edu.scnu.danmakutv.dto.user.UpdateFollowGroupDTO;
import cn.edu.scnu.danmakutv.user.mapper.FollowGroupMapper;
import cn.edu.scnu.danmakutv.user.service.FollowGroupService;
import cn.edu.scnu.danmakutv.user.service.FollowRelationService;
import cn.edu.scnu.danmakutv.user.service.UserFollowService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class FollowGroupServiceImpl extends ServiceImpl<FollowGroupMapper, FollowGroup> implements FollowGroupService {

    @Resource
    private FollowRelationService followRelationService;
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Override
    public FollowGroup getById (Long id) {
        return this.baseMapper.selectById(id);
    }

    // 根据用户ID获取关注分组列表
    /**
     * 获取指定用户的关注分组列表
     * @param userId 用户ID
     * @return 返回用户的关注分组列表，包含默认分组
     */
    @Override
    public List<FollowGroup> getFollowGroupsByUserId (Long userId) {
        List<FollowGroup> followGroups = this.baseMapper.selectList(
                new QueryWrapper<FollowGroup>()
                        .eq("user_id", userId)
        );
        // 添加默认分组
        followGroups.add(0, this.getById(1));

        return followGroups;
    }

    // 创建关注分组
    /**
     * 创建一个新的关注分组
     * @param createFollowGroupDTO 包含分组信息的DTO
     * @return 返回新创建的分组ID
     */
    @Override
    public Long createFollowGroup (CreateFollowGroupDTO createFollowGroupDTO) {
        // 检查分组名称是否已存在
        List<FollowGroup> existingGroups = this.baseMapper.selectList(
                new QueryWrapper<FollowGroup>()
                        .eq("user_id", createFollowGroupDTO.getUserId())
                        .eq("name", createFollowGroupDTO.getName())
        );
        if (!existingGroups.isEmpty()) {
            throw new DanmakuException("分组名称已存在", 400);
        }

        // 创建新的分组
        FollowGroup followGroup = new FollowGroup();
        BeanUtils.copyProperties(createFollowGroupDTO, followGroup);
        this.baseMapper.insert(followGroup);

        return followGroup.getId();
    }

    @Override
    public void updateFollowGroup(Long userId, UpdateFollowGroupDTO updateDTO) {
        FollowGroup followGroup = this.getById(updateDTO.getId());
        if (followGroup == null) {
            throw new DanmakuException("分组不存在", 400);
        }
        if (!followGroup.getUserId().equals(userId)) {
            throw new DanmakuException("无权修改该分组", 403);
        }

        // 更新分组信息
        if (updateDTO.getName() != null && !updateDTO.getName().isEmpty()) {
            followGroup.setName(updateDTO.getName());
        }

        // 设置更新时间
        followGroup.setUpdatedAt(LocalDateTime.now().format(FORMATTER));
        this.updateById(followGroup);
    }

    @Override
    @Transactional
    public void deleteFollowGroup(Long userId, Long groupId) {
        // 检查分组是否存在且属于当前用户
        FollowGroup group = this.getById(groupId);
        if (group == null) {
            throw new DanmakuException("分组不存在", 400);
        }
        if (!group.getUserId().equals(userId)) {
            throw new DanmakuException("无权删除该分组", 403);
        }

        // 检查是否为默认分组（名称判断）
        if ("默认分组".equals(group.getName())) {
            throw new DanmakuException("不能删除默认分组", 400);
        }

        // 修改为使用新的服务
        FollowGroup defaultGroup = this.getDefaultGroup(userId);
        followRelationService.transferGroupToDefault(userId, groupId, defaultGroup.getId());

        // 删除分组
        this.removeById(groupId);
    }

    private FollowGroup getDefaultGroup(Long userId) {
        return this.getOne(
                new QueryWrapper<>(FollowGroup.class)
                        .eq("user_id", userId)
                        .eq("name", "默认分组")
        );
    }
}
