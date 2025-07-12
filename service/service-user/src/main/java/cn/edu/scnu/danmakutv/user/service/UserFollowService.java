package cn.edu.scnu.danmakutv.user.service;

import cn.edu.scnu.danmakutv.domain.UserFollow;
import cn.edu.scnu.danmakutv.dto.UserFollowDTO;
import cn.edu.scnu.danmakutv.vo.UserFollowGroupVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface UserFollowService extends IService<UserFollow> {
    void addUserFollow(UserFollowDTO userFollowDTO);
    List<UserFollowGroupVO> getUserFollowGroups(Long userId);
}
