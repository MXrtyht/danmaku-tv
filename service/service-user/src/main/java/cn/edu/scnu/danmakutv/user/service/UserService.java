package cn.edu.scnu.danmakutv.user.service;

import cn.edu.scnu.danmakutv.domain.User;
import cn.edu.scnu.danmakutv.dto.UserLoginVO;
import cn.edu.scnu.danmakutv.dto.UserRegisterVO;
import cn.edu.scnu.danmakutv.vo.UserProfilesVO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {
    void registerUser (UserRegisterVO userRegisterVO);

    String loginUser (UserLoginVO userLoginVO);
}
