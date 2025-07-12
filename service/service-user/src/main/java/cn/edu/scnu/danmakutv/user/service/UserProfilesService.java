package cn.edu.scnu.danmakutv.user.service;

import cn.edu.scnu.danmakutv.domain.UserProfiles;
import cn.edu.scnu.danmakutv.dto.UserProfilesDTO;
import cn.edu.scnu.danmakutv.vo.UserProfilesVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface UserProfilesService extends IService<UserProfiles> {
    void addUserProfiles (Long userId);

    UserProfilesVO getUserProfilesByUserId (Long userId);

    void updateUserProfiles (Long userId, UserProfilesDTO userProfilesDTO);

    List<UserProfiles> getUserProfilesByUserIds (List<Long> userIds);
}
