package cn.edu.scnu.danmakutv.user.service;

import cn.edu.scnu.danmakutv.domain.FollowGroup;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface FollowGroupService extends IService<FollowGroup> {
    FollowGroup getById (Long id);

    List<FollowGroup> getFollowGroupsByUserId (Long userId);
}
