package cn.edu.scnu.danmakutv.user.service;

import cn.edu.scnu.danmakutv.domain.UserProfiles;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserProfilesService extends IService<UserProfiles> {
    void addUserProfiles (Long userId);

}
