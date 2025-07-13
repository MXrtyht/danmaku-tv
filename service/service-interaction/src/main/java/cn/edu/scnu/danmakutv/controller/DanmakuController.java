package cn.edu.scnu.danmakutv.controller;

import cn.edu.scnu.danmakutv.common.authentication.AuthenticationSupport;
import cn.edu.scnu.danmakutv.common.response.CommonResponse;
import cn.edu.scnu.danmakutv.domain.Danmaku;
import cn.edu.scnu.danmakutv.service.DanmakuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Api(tags = "弹幕操作相关接口")
@RestController
public class DanmakuController {
    @Resource
    private DanmakuService danmakuService;

    @Resource
    private AuthenticationSupport authenticationSupport;

    /**
     * 添加弹幕
     *
     * @param danmaku 弹幕内容
     * @return 成功响应
     */
    @ApiOperation(
            value = "添加弹幕")
    @PostMapping("/danmaku")
    public CommonResponse<String> addDanmaku (@RequestBody Danmaku danmaku) {
        danmakuService.addDanmaku(danmaku);
        return CommonResponse.success("");
    }

    /**
     * 获取视频弹幕
     *
     * @param videoId 视频ID
     * @param startTime 可选的开始时间
     * @param endTime 可选的结束时间
     * @return 弹幕列表
     */
    @ApiOperation(
            value = "获取视频弹幕",
            notes = "获取指定视频的弹幕列表, 可选时间段筛选"
    )
    @GetMapping("/danmaku")
    public CommonResponse<List<Danmaku>> getDanmaku (@RequestParam Long videoId,
                                                     @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
                                                     @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        List<Danmaku> result;
        try {
            authenticationSupport.getCurrentUserId();
            // 已登录 允许时间段筛选
            result = danmakuService.getDanmaku(videoId, startTime, endTime);
        } catch (Exception ignored) {
            // 为登录 则不允许
            result = danmakuService.getDanmaku(videoId, null, null);
        }
        return CommonResponse.success(result);
    }
}
