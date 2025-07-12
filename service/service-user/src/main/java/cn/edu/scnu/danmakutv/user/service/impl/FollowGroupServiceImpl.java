package cn.edu.scnu.danmakutv.user.service.impl;

import cn.edu.scnu.danmakutv.domain.FollowGroup;
import cn.edu.scnu.danmakutv.user.mapper.FollowGroupMapper;
import cn.edu.scnu.danmakutv.user.service.FollowGroupService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
}
