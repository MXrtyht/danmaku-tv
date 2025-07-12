package cn.edu.scnu.danmakutv.user.service.impl;

import cn.edu.scnu.danmakutv.common.exception.DanmakuException;
import cn.edu.scnu.danmakutv.domain.FollowGroup;
import cn.edu.scnu.danmakutv.dto.CreateFollowGroupDTO;
import cn.edu.scnu.danmakutv.user.mapper.FollowGroupMapper;
import cn.edu.scnu.danmakutv.user.service.FollowGroupService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowGroupServiceImpl extends ServiceImpl<FollowGroupMapper, FollowGroup> implements FollowGroupService {
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
}
