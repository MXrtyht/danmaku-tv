package cn.edu.scnu.danmakutv.user.service.impl;

import cn.edu.scnu.danmakutv.constant.UserProfilesDefaultConstant;
import cn.edu.scnu.danmakutv.domain.UserProfiles;
import cn.edu.scnu.danmakutv.user.mapper.UserProfilesMapper;
import cn.edu.scnu.danmakutv.user.service.UserProfilesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserProfilesServiceImpl extends ServiceImpl<UserProfilesMapper, UserProfiles> implements UserProfilesService {

    @Override
    public void addUserProfiles (Long userId) {
        UserProfiles userProfiles = new UserProfiles();

        // 设置默认值
        userProfiles.setUid(userId);
        userProfiles.setNickname(UserProfilesDefaultConstant.DEFAULT_NICKNAME_PREFIX + userId);
        userProfiles.setAvatar(UserProfilesDefaultConstant.DEFAULT_AVATAR);
        userProfiles.setGender(UserProfilesDefaultConstant.DEFAULT_GENDER);
        userProfiles.setBirthday(UserProfilesDefaultConstant.DEFAULT_BIRTHDAY);
        userProfiles.setCoin(UserProfilesDefaultConstant.DEFAULT_COIN);
        userProfiles.setSign(UserProfilesDefaultConstant.DEFAULT_SIGN);
        userProfiles.setCreatedAt(LocalDateTime.now());

        baseMapper.insert(userProfiles);
    }
}
