package cn.edu.scnu.danmakutv.user.service.impl;

import cn.edu.scnu.common.utils.MD5Util;
import cn.edu.scnu.common.utils.RSAUtil;
import cn.edu.scnu.common.utils.SaltUtil;
import cn.edu.scnu.danmaku.common.exception.DanmakuException;
import cn.edu.scnu.danmaku.common.response.StatusCodeEnum;
import cn.edu.scnu.danmakutv.domain.User;
import cn.edu.scnu.danmakutv.user.mapper.UserMapper;
import cn.edu.scnu.danmakutv.user.service.UserService;
import cn.edu.scnu.danmakutv.vo.authentication.UserRegisterVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 用户注册
     *
     * @param userRegisterVO
     */
    @Override
    public void registerUser (UserRegisterVO userRegisterVO) {
        User existingUser = baseMapper.selectOne(
                new QueryWrapper<>(User.class).eq("phone", userRegisterVO.getPhone())
                                              .or()
                                              .eq("email", userRegisterVO.getEmail())
        );

        if (existingUser != null) {
            String errorMessage;
            if (userRegisterVO.getPhone().equals(existingUser.getPhone())) {
                errorMessage = "手机号已被注册";
            } else {
                errorMessage = "邮箱已被注册";
            }
            throw new DanmakuException(errorMessage,400);
        }

        // 对密码RSA解密, MD5加密
        String password = userRegisterVO.getPassword();
        String rawPassword;
        try {
            rawPassword = RSAUtil.decrypt(password);
        } catch (Exception e) {
            log.error("密码解密失败", e);
            throw new DanmakuException(StatusCodeEnum.SERVICE_ERROR);
        }
        String salt = SaltUtil.generateSalt();
        String md5Password = MD5Util.sign(rawPassword, salt, "UTF-8");

        // 设置相应字段
        User user = new User();
        user.setPhone(userRegisterVO.getPhone());
        user.setEmail(userRegisterVO.getEmail());
        user.setPassword(md5Password);
        user.setSalt(salt);
        user.setIsBanned(false);
        user.setCreateAt(LocalDateTime.now());
        baseMapper.insert(user);

    }
}
