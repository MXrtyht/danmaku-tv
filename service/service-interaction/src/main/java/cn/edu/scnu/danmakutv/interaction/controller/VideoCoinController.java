package cn.edu.scnu.danmakutv.interaction.controller;

import cn.edu.scnu.danmakutv.common.authentication.AuthenticationSupport;
import cn.edu.scnu.danmakutv.common.response.CommonResponse;
import cn.edu.scnu.danmakutv.dto.interaction.AddVideoCoinDTO;
import cn.edu.scnu.danmakutv.interaction.service.VideoCoinService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Tag(name = "视频投币相关接口")
@RequestMapping("/video")
@RestController
public class VideoCoinController {
    @Resource
    private AuthenticationSupport authenticationSupport;

    @Resource
    private VideoCoinService videoCoinService;

    /**
     *
     */
    @Operation(summary = "给视频投币")
    @PostMapping("/coin")
    public CommonResponse<Boolean> coinVideoById (
            @Valid @RequestBody AddVideoCoinDTO addVideoCoinDTO
    ) {
        Long userId = authenticationSupport.getCurrentUserId();
        addVideoCoinDTO.setUserId(userId);
        videoCoinService.coinVideoById(addVideoCoinDTO);
        return CommonResponse.success(true);
    }

    @Operation(summary = "获取视频投币数量")
    @GetMapping("/coin-count")
    public CommonResponse<Long> getUserProfilesById (
            @Parameter(description = "视频ID", required = true)
            @RequestParam Long videoId
    ) {
        Long coinCount = videoCoinService.getVideoCoinCount(videoId);
        return CommonResponse.success(coinCount);
    }

    @Operation(summary = "查看当前用户是否已投币")
    @GetMapping("/is-coined")
    public CommonResponse<Boolean> isCoined (
            @Parameter(description = "视频ID", required = true)
            @RequestParam Long videoId
    ) {
        Long userId = authenticationSupport.getCurrentUserId();
        boolean status = videoCoinService.checkVideoCoinRecord(userId, videoId);
        return CommonResponse.success(status);
    }
}
