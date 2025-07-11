package cn.edu.scnu.danmakutv.user.service;

import cn.edu.scnu.danmakutv.domain.UserFollow;
import cn.edu.scnu.danmakutv.dto.UserFollowDTO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserFollowService extends IService<UserFollow> {
    void addUserFollow(UserFollowDTO userFollowDTO);
}
