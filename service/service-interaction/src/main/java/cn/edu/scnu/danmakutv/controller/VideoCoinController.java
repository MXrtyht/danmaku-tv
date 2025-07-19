package cn.edu.scnu.danmakutv.controller;

import cn.edu.scnu.danmakutv.common.authentication.AuthenticationSupport;
import cn.edu.scnu.danmakutv.common.response.CommonResponse;
import cn.edu.scnu.danmakutv.domain.interaction.VideoCoin;
import cn.edu.scnu.danmakutv.service.VideoCoinService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@Tag(name = "视频投币相关接口")
@RestController
@RequestMapping("/video")
public class VideoCoinController {
    @Resource
    private AuthenticationSupport authenticationSupport;
    @Resource
    private VideoCoinService videoCoinService;
    /**
     * 视频投币
     */
    @PostMapping("/coin")
    public CommonResponse<String> addVideoCoins(@RequestBody VideoCoin videoCoin){
        Long userId = authenticationSupport.getCurrentUserId();
        videoCoinService.addVideoCoins(videoCoin, userId);
        return CommonResponse.success("");
    }
    /**
     * 查询视频投币数量
     */
    @GetMapping("/coin")
    public CommonResponse<Map<String, Object>> getVideoCoins(@RequestParam Long videoId){
        Long userId = null;
        try{
            userId = authenticationSupport.getCurrentUserId();
        }catch (Exception ignored){}
        Map<String, Object> result = videoCoinService.getVideoCoins(videoId, userId);
        return CommonResponse.success(result);
    }
}
