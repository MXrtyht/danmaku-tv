package cn.edu.scnu.danmakutv.user.service.impl;

import cn.edu.scnu.danmakutv.constant.UserProfilesDefaultConstant;
import cn.edu.scnu.danmakutv.domain.user.UserProfiles;
import cn.edu.scnu.danmakutv.dto.user.UserProfilesDTO;
import cn.edu.scnu.danmakutv.user.mapper.UserProfilesMapper;
import cn.edu.scnu.danmakutv.user.service.UserProfilesService;
import cn.edu.scnu.danmakutv.vo.user.UserProfilesVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserProfilesServiceImpl extends ServiceImpl<UserProfilesMapper, UserProfiles> implements UserProfilesService {

    /**
     * 添加用户个人资料
     * @param userId 用户ID
     */
    @Override
    public void addUserProfiles (Long userId) {
        UserProfiles userProfiles = new UserProfiles();

        // 设置默认值
        userProfiles.setUserId(userId);
        userProfiles.setNickname(UserProfilesDefaultConstant.DEFAULT_NICKNAME_PREFIX + userId);
        userProfiles.setAvatar(UserProfilesDefaultConstant.DEFAULT_AVATAR);
        userProfiles.setGender(UserProfilesDefaultConstant.DEFAULT_GENDER);
        userProfiles.setBirthday(UserProfilesDefaultConstant.DEFAULT_BIRTHDAY);
        userProfiles.setCoin(UserProfilesDefaultConstant.DEFAULT_COIN);
        userProfiles.setSign(UserProfilesDefaultConstant.DEFAULT_SIGN);
        userProfiles.setCreateAt(LocalDateTime.now());

        baseMapper.insert(userProfiles);
    }

    /**
     * 根据用户ID获取用户个人资料
     * @param userId 用户ID
     * @return 返回用户个人资料
     */
    @Override
    public UserProfilesVO getUserProfilesByUserId (Long userId) {
        UserProfiles userProfiles = baseMapper.selectOne(
                new QueryWrapper<>(UserProfiles.class)
                        .eq("user_id", userId)
        );

        UserProfilesVO userProfilesVO = new UserProfilesVO();
        BeanUtils.copyProperties(userProfiles, userProfilesVO);

        return userProfilesVO;
    }

    /**
     * 更新用户个人资料
     * @param userId 用户ID
     * @param userProfilesDTO 包含更新信息的DTO
     */
    @Override
    public void updateUserProfiles (Long userId, UserProfilesDTO userProfilesDTO) {
        UserProfiles userProfiles = baseMapper.selectOne(
                new QueryWrapper<>(UserProfiles.class)
                        .eq("user_id", userId)
        );
        BeanUtils.copyProperties(userProfilesDTO, userProfiles, "userId", "createAt");
        userProfiles.setUpdateAt(LocalDateTime.now());
        baseMapper.updateById(userProfiles);
    }

    /**
     * 根据用户ID列表获取用户个人资料
     * @param userIds 用户ID列表
     * @return 返回用户个人资料列表
     */
    @Override
    public List<UserProfiles> getUserProfilesByUserIds (List<Long> userIds) {
        return this.baseMapper.selectList(
                new QueryWrapper<>(UserProfiles.class)
                        .in("user_id", userIds)
        );
    }
}
