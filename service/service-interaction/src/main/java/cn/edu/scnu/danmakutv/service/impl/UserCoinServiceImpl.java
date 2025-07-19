package cn.edu.scnu.danmakutv.service.impl;

import cn.edu.scnu.danmakutv.domain.interaction.UserCoin;
import cn.edu.scnu.danmakutv.mapper.UserCoinMapper;
import cn.edu.scnu.danmakutv.service.UserCoinService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserCoinServiceImpl extends ServiceImpl<UserCoinMapper, UserCoin> implements UserCoinService {
    /**
     * 获取用户硬币总数
     * @param userId 用户ID
     * @return 用户硬币数量
     */
    @Override
    public Integer getUserCoinsAmount(Long userId) {
        UserCoin userCoin = this.getOne(
                new QueryWrapper<UserCoin>()
                        .select("SUM(amount)")
                        .eq("userId", userId)
                        .groupBy("userId")
        );
        return userCoin.getAmount();
    }

    /**
     * 更新用户当前硬币数量与得到对应更新时间
     * @param userId
     * @param amount
     * @return 更新过的UserCoin实体变量
     */
    @Override
    public boolean updateUserCoinsAmount(Long userId, int amount) {
        return this.update(
                new UpdateWrapper<UserCoin>()
                        .set("amount", amount)
                        .set("updatetime", new Date())
                        .eq("userId", userId)
        );
    }
}
