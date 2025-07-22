package cn.edu.scnu.danmakutv.interaction.controller.client;

import cn.edu.scnu.danmakutv.common.response.CommonResponse;
import cn.edu.scnu.danmakutv.vo.user.UserProfilesVO;
import cn.edu.scnu.danmakutv.vo.video.VideoVO;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("service-video")
public interface VideoServiceClient {
    @GetMapping("/video/id")
    CommonResponse<VideoVO> getVideoById (
            @Parameter(description = "视频ID") @RequestParam Long videoId
    );
}
