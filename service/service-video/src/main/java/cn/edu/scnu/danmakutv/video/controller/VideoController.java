package cn.edu.scnu.danmakutv.video.controller;

import cn.edu.scnu.danmakutv.common.authentication.AuthenticationSupport;
import cn.edu.scnu.danmakutv.common.response.CommonResponse;
import cn.edu.scnu.danmakutv.video.service.VideoService;
import cn.edu.scnu.danmakutv.vo.VideoVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.Size;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/video")
public class VideoController {
    @Resource
    private AuthenticationSupport authenticationSupport;

    @Resource
    private VideoService videoService;

    @GetMapping("/all")
    public CommonResponse<IPage<VideoVO>> selectAllVideo (@Size(min = 1) int page, @Size(min = 4) int size) {
        IPage<VideoVO> result = videoService.selectVideo(page, size, null);
        return CommonResponse.success(result);
    }
}
