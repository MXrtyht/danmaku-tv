package cn.edu.scnu.danmakutv.controller;

import cn.edu.scnu.danmakutv.common.authentication.AuthenticationSupport;
import cn.edu.scnu.danmakutv.common.response.CommonResponse;
import cn.edu.scnu.danmakutv.domain.interaction.VideoCollection;
import cn.edu.scnu.danmakutv.service.VideoCollectService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.checkerframework.checker.units.qual.C;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@Tag(name="视频收藏相关接口")
@RestController
@RequestMapping("/video")
public class VideoCollectController {
    @Resource
    private AuthenticationSupport authenticationSupport;
    @Resource
    private VideoCollectService videoCollectService;
    /**
     * 收藏视频
     */
    @PostMapping("/collect")
    public CommonResponse<String> addVideoCollection(@RequestBody VideoCollection videoCollection){
        Long userId = authenticationSupport.getCurrentUserId();
        videoCollectService.addVideoCollection(videoCollection, userId);
        return CommonResponse.success("");
    }
    /**
     * 取消收藏视频
     */
    @DeleteMapping("/collect")
    public CommonResponse<String> deleteVideoCollection(@RequestParam Long videoId){
        Long userId = authenticationSupport.getCurrentUserId();
        videoCollectService.deleteVideoCollection(videoId, userId);
        return CommonResponse.success("");
    }
    /**
     * 查询视频收藏数量
     */
    @GetMapping("/collect")
    public CommonResponse<Map<String, Object>> getVideoCollections(@RequestParam Long videoId) {
        Long userId = null;
        try {
            userId = authenticationSupport.getCurrentUserId();
        } catch (Exception ignored){}
        Map<String, Object> result = videoCollectService.getVideoCollections(videoId, userId);
        return CommonResponse.success(result);
    }
}
