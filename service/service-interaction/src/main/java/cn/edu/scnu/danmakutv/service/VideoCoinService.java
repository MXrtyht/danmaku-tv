package cn.edu.scnu.danmakutv.service;

import cn.edu.scnu.danmakutv.domain.interaction.VideoCoin;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

public interface VideoCoinService extends IService<VideoCoin> {

    void addVideoCoins(VideoCoin videoCoin, Long userId);

    Map<String, Object> getVideoCoins(Long videoId, Long userId);
}
