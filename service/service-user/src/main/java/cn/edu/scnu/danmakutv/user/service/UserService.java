package cn.edu.scnu.danmakutv.user.service;

import cn.edu.scnu.danmakutv.domain.User;
import cn.edu.scnu.danmakutv.dto.UserLoginDTO;
import cn.edu.scnu.danmakutv.dto.UserRegisterDTO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {
    void registerUser (UserRegisterDTO userRegisterDTO);

    String loginUser (UserLoginDTO userLoginDTO);
}
