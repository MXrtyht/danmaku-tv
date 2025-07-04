package cn.edu.scnu.danmakutv.user.service;

import cn.edu.scnu.danmakutv.domain.User;
import cn.edu.scnu.danmakutv.vo.authentication.UserRegisterVO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {
    void registerUser (UserRegisterVO userRegisterVO);
}
