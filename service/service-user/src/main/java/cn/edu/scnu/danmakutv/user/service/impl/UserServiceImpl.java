package cn.edu.scnu.danmakutv.user.service.impl;

import cn.edu.scnu.danmakutv.common.authentication.JwtHelper;
import cn.edu.scnu.common.utils.MD5Util;
import cn.edu.scnu.common.utils.RSAUtil;
import cn.edu.scnu.common.utils.SaltUtil;
import cn.edu.scnu.danmakutv.common.exception.DanmakuException;
import cn.edu.scnu.danmakutv.common.response.StatusCodeEnum;
import cn.edu.scnu.danmakutv.domain.User;
import cn.edu.scnu.danmakutv.dto.UserLoginDTO;
import cn.edu.scnu.danmakutv.dto.UserRegisterDTO;
import cn.edu.scnu.danmakutv.user.mapper.UserMapper;
import cn.edu.scnu.danmakutv.user.service.UserProfilesService;
import cn.edu.scnu.danmakutv.user.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserProfilesService userProfilesService;

    /**
     * 用户注册
     *
     * @param userRegisterDTO
     */
    @Override
    public void registerUser (UserRegisterDTO userRegisterDTO) {
        User existingUser = baseMapper.selectOne(
                new QueryWrapper<>(User.class).eq("phone", userRegisterDTO.getPhone())
                                              .or()
                                              .eq("email", userRegisterDTO.getEmail())
        );

        if (existingUser != null) {
            String errorMessage;
            if (userRegisterDTO.getPhone().equals(existingUser.getPhone())) {
                errorMessage = "手机号已被注册";
            } else {
                errorMessage = "邮箱已被注册";
            }
            throw new DanmakuException(errorMessage, 400);
        }

        // 对密码RSA解密, MD5加密
        String password = userRegisterDTO.getPassword();
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
        user.setPhone(userRegisterDTO.getPhone());
        user.setEmail(userRegisterDTO.getEmail());
        user.setPassword(md5Password);
        user.setSalt(salt);
        user.setIsBanned(false);
        user.setCreateAt(LocalDateTime.now());
        // 添加用户
        baseMapper.insert(user);

        // 设置相应用户信息
        userProfilesService.addUserProfiles(user.getId());
    }

    @Override
    public String loginUser (UserLoginDTO userLoginDTO) {
        User user = baseMapper.selectOne(
                new QueryWrapper<>(User.class).eq("phone", userLoginDTO.getPhone())
        );
        if (user == null) {
            throw new DanmakuException("用户不存在", 400);
        }
        if (user.getIsBanned()) {
            throw new DanmakuException("用户已被封禁", 400);
        }

        String password = userLoginDTO.getPassword();
        String rawPassword;
        try {
            rawPassword = RSAUtil.decrypt(password);
        } catch (Exception e) {
            log.error("密码解密失败", e);
            throw new DanmakuException("密码无效或格式错误", 400);
        }
        String md5Password = MD5Util.sign(rawPassword, user.getSalt(), "UTF-8");

        // 验证密码
        if (!md5Password.equals(user.getPassword())) {
            throw new DanmakuException("密码错误", 400);
        }

        return JwtHelper.createToken(user.getId());
    }

    @Override
    public User getUserById (Long Id) {
        return this.baseMapper.selectById(Id);
    }
}
