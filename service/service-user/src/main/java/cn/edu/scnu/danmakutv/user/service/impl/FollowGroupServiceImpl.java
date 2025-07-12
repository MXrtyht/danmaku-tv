package cn.edu.scnu.danmakutv.user.service.impl;

import cn.edu.scnu.danmakutv.common.exception.DanmakuException;
import cn.edu.scnu.danmakutv.domain.FollowGroup;
import cn.edu.scnu.danmakutv.dto.CreateFollowGroupDTO;
import cn.edu.scnu.danmakutv.user.mapper.FollowGroupMapper;
import cn.edu.scnu.danmakutv.user.service.FollowGroupService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowGroupServiceImpl extends ServiceImpl<FollowGroupMapper, FollowGroup> implements FollowGroupService {
    @Override
    public FollowGroup getById (Long id) {
        return this.baseMapper.selectById(id);
    }

    @Override
    public List<FollowGroup> getFollowGroupsByUserId (Long userId) {
        return this.baseMapper.selectList(
                new QueryWrapper<FollowGroup>()
                        .eq("user_id", userId)
        );
    }

    // 创建关注分组
    @Override
    public Long createFollowGroup (CreateFollowGroupDTO createFollowGroupDTO) {
        // 检查分组名称是否已存在
        List<FollowGroup> existingGroups = this.baseMapper.selectList(
                new QueryWrapper<FollowGroup>()
                        .eq("user_id", createFollowGroupDTO.getUserId())
                        .eq("name", createFollowGroupDTO.getName())
        );
        if(!existingGroups.isEmpty()) {
            throw new DanmakuException("分组名称已存在", 400);
        }

        // 创建新的分组
        FollowGroup followGroup = new FollowGroup();
        BeanUtils.copyProperties(createFollowGroupDTO,followGroup);
        this.baseMapper.insert(followGroup);

        return followGroup.getId();
    }
}
