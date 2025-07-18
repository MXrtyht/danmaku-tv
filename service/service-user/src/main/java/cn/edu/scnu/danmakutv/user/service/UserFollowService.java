package cn.edu.scnu.danmakutv.user.service;

import cn.edu.scnu.danmakutv.domain.user.UserFollow;
import cn.edu.scnu.danmakutv.domain.user.UserProfiles;
import cn.edu.scnu.danmakutv.dto.user.UserFollowDTO;
import cn.edu.scnu.danmakutv.vo.user.UserFollowsWithGroupVO;
import cn.edu.scnu.danmakutv.vo.user.UserProfilesVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface UserFollowService extends IService<UserFollow> {
    void addUserFollow (UserFollowDTO userFollowDTO);

    List<UserFollowsWithGroupVO> getUserFollowGroups (Long userId);

    Map<UserProfiles, Boolean> getFans (Long userId);

    void transferGroupToDefault(Long userId, Long oldGroupId);

    void unfollow(Long userId, Long followId);

    Page<UserProfilesVO> getFollowsByGroupId(Long userId, Long groupId, Integer page, Integer size);
}
