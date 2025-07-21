package cn.edu.scnu.danmakutv.interaction.service;

import cn.edu.scnu.danmakutv.domain.interaction.CollectionGroup;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface CollectionGroupService extends IService<CollectionGroup> {
    List<CollectionGroup> getCollectionGroupsByUserId (Long userId);
}
