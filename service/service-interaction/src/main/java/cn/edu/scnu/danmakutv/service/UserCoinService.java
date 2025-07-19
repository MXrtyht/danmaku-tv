package cn.edu.scnu.danmakutv.service;

import cn.edu.scnu.danmakutv.domain.interaction.UserCoin;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserCoinService extends IService<UserCoin> {
    Integer getUserCoinsAmount(Long userId);

    boolean updateUserCoinsAmount(Long userId, int amount);
}
