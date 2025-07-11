package cn.edu.scnu.danmakutv.controller;

import cn.edu.scnu.danmakutv.common.authentication.AuthenticationSupport;
import cn.edu.scnu.danmakutv.common.response.CommonResponse;
import cn.edu.scnu.danmakutv.domain.Danmaku;
import cn.edu.scnu.danmakutv.service.DanmakuService;
import jakarta.annotation.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class DanmakuController {
    @Resource
    private DanmakuService danmakuService;

    @Resource
    private AuthenticationSupport authenticationSupport;

    @PostMapping("/danmaku")
    public CommonResponse<String> addDanmaku (@RequestBody Danmaku danmaku) {
        danmakuService.addDanmaku(danmaku);
        return CommonResponse.success("");
    }

    @GetMapping("/danmaku")
    public CommonResponse<List<Danmaku>> getDanmaku (@RequestParam Long videoId,
                                                     @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
                                                     @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        List<Danmaku> result ;
        try{
            authenticationSupport.getCurrentUserId();
            // 已登录 允许时间段筛选
            result = danmakuService.getDanmaku(videoId, startTime, endTime);
        }catch (Exception ignored){
            // 为登录 则不允许
            result = danmakuService.getDanmaku(videoId, null, null);
        }
        return CommonResponse.success(result);
    }
}
