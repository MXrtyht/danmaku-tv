package cn.edu.scnu.danmakutv.user.service;

import cn.edu.scnu.danmakutv.domain.user.User;
import cn.edu.scnu.danmakutv.dto.user.UserLoginDTO;
import cn.edu.scnu.danmakutv.dto.user.UserRegisterDTO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {
    void registerUser (UserRegisterDTO userRegisterDTO);

    String loginUser (UserLoginDTO userLoginDTO);

    User getUserById (Long Id);
}
