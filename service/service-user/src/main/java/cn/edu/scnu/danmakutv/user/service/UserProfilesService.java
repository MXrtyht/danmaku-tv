package cn.edu.scnu.danmakutv.user.service;

import cn.edu.scnu.danmakutv.domain.user.UserProfiles;
import cn.edu.scnu.danmakutv.dto.user.CoinUpdateDTO;
import cn.edu.scnu.danmakutv.dto.user.UserProfilesDTO;
import cn.edu.scnu.danmakutv.vo.user.UserProfilesVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface UserProfilesService extends IService<UserProfiles> {
    void addUserProfiles (Long userId);

    UserProfilesVO getUserProfilesByUserId (Long userId);

    void updateUserProfiles (Long userId, UserProfilesDTO userProfilesDTO);

    List<UserProfiles> getUserProfilesByUserIds (List<Long> userIds);

    Page<UserProfilesVO> searchUsersByNickname(String nickname, Integer page, Integer size);

    void updateUserCoin(CoinUpdateDTO coinUpdateDTO);
}
