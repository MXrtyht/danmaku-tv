package cn.edu.scnu.danmakutv.video.controller;

import cn.edu.scnu.danmakutv.common.authentication.AuthenticationSupport;
import cn.edu.scnu.danmakutv.common.response.CommonResponse;
import cn.edu.scnu.danmakutv.dto.UserUploadVideoDTO;
import cn.edu.scnu.danmakutv.video.service.VideoService;
import cn.edu.scnu.danmakutv.vo.VideoVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/video")
public class VideoController {
    @Resource
    private AuthenticationSupport authenticationSupport;

    @Resource
    private VideoService videoService;

    @GetMapping("/all")
    public CommonResponse<IPage<VideoVO>> selectAllVideo (@Size(min = 1) int page, @Size(min = 4) int size) {
        // QueryWrapper 设置为 null ，查询所有视频
        IPage<VideoVO> result = videoService.selectVideo(page, size, null);
        return CommonResponse.success(result);
    }

    @GetMapping("/user")
    public CommonResponse<IPage<VideoVO>> selectUserVideo (@Size(min = 1) int page, @Size(min = 4) int size) {
        Long userId = authenticationSupport.getCurrentUserId();

        // 构造查询条件, 查询当前用户的视频
        QueryWrapper<VideoVO> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        IPage<VideoVO> result = videoService.selectVideo(page, size, wrapper);

        return CommonResponse.success(result);
    }

    // 上传视频 前端先去MinioService的uploadFile接口上传视频 得到视频的存储路径再调用该接口
    @PostMapping("/user")
    public CommonResponse<String> uploadVideo (@Valid @RequestBody UserUploadVideoDTO userUploadVideoDTO) {
        Long userId = authenticationSupport.getCurrentUserId();
        userUploadVideoDTO.setUserId(userId);
        videoService.uploadVideo(userUploadVideoDTO);
        return CommonResponse.success("视频上传成功");
    }
}
