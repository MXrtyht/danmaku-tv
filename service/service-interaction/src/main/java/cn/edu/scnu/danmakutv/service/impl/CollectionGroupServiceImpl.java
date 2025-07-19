package cn.edu.scnu.danmakutv.service.impl;

import cn.edu.scnu.danmakutv.domain.interaction.CollectionGroup;
import cn.edu.scnu.danmakutv.mapper.CollectionGroupMapper;
import cn.edu.scnu.danmakutv.service.CollectionGroupService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectionGroupServiceImpl extends ServiceImpl<CollectionGroupMapper, CollectionGroup> implements CollectionGroupService {
    /**
     * 根据用户ID获取所有的收藏分组列表
     * @param userId 用户ID
     * @return 收藏分组列表
     */
    @Override
    public List<CollectionGroup> getCollectionGroupsByUserId (Long userId) {

         List<CollectionGroup> collectionGroups = this.baseMapper.selectList(
                new QueryWrapper<CollectionGroup>()
                        .eq("user_id", userId)
        );
        // 添加默认分组
        collectionGroups.add(0, this.getById(1));
        return collectionGroups;
    }
}
