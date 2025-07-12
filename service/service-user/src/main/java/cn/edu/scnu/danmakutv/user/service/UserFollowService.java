package cn.edu.scnu.danmakutv.user.service;

import cn.edu.scnu.danmakutv.domain.FollowGroup;
import cn.edu.scnu.danmakutv.domain.UserFollow;
import cn.edu.scnu.danmakutv.domain.UserProfiles;
import cn.edu.scnu.danmakutv.dto.UserFollowDTO;
import cn.edu.scnu.danmakutv.vo.UserFollowGroupVO;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

public interface UserFollowService extends IService<UserFollow> {
    void addUserFollow (UserFollowDTO userFollowDTO);

    List<UserFollowGroupVO> getUserFollowGroups (Long userId);

    Map<UserProfiles, Boolean> getFans (Long userId);
}
