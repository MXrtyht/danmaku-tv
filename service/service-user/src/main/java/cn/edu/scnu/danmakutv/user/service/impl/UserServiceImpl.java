package cn.edu.scnu.danmakutv.user.service.impl;

import cn.edu.scnu.danmakutv.domain.User;
import cn.edu.scnu.danmakutv.user.mapper.UserMapper;
import cn.edu.scnu.danmakutv.user.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
