package cn.edu.scnu.danmakutv.user.service;

import cn.edu.scnu.danmakutv.domain.user.UserMoments;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface UserMomentsService extends IService<UserMoments> {
    void addUserMoments(UserMoments userMoments) throws Exception;
    List<UserMoments> getUserSubscribedMoments(Long userId) throws Exception;
}
