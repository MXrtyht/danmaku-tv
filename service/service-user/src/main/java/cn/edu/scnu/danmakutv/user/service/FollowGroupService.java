package cn.edu.scnu.danmakutv.user.service;

import cn.edu.scnu.danmakutv.domain.FollowGroup;
import com.baomidou.mybatisplus.extension.service.IService;

public interface FollowGroupService extends IService<FollowGroup> {
    public FollowGroup getById(Long id);
}
