package cn.edu.scnu.danmakutv.video.controller;

import cn.edu.scnu.danmakutv.common.authentication.AuthenticationSupport;
import cn.edu.scnu.danmakutv.common.response.CommonResponse;
import cn.edu.scnu.danmakutv.dto.video.UserUploadVideoDTO;
import cn.edu.scnu.danmakutv.video.service.VideoService;
import cn.edu.scnu.danmakutv.vo.video.VideoVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Tag(name = "视频操作相关接口")
@RestController
@RequestMapping("/video")
public class VideoController {
    @Resource
    private AuthenticationSupport authenticationSupport;

    @Resource
    private VideoService videoService;

    /**
     * 查询所有视频
     * @param page 分页页码
     * @param size 分页大小
     * @return 包含视频列表的分页结果
     */
    @Operation(
            summary = "查询所有视频",
            description = "查询所有视频, 返回分页结果"
    )
    @GetMapping("/all")
    public CommonResponse<IPage<VideoVO>> selectAllVideo (
            @Size(min = 1) @Parameter(description = "分页页码, 从1开始") int page,
            @Size(min = 4) @Parameter(description = "分页大小, 最小每页4个") int size
    ) {
        // QueryWrapper 设置为 null ，查询所有视频
        IPage<VideoVO> result = videoService.selectVideo(page, size, null);
        return CommonResponse.success(result);
    }

    /**
     * 查询当前用户上传的视频
     * @param page 分页页码
     * @param size 分页大小
     * @return 包含当前用户视频列表的分页结果
     */
    @Operation(
            summary = "查询当前用户所有视频",
            description = "查询视频, 返回分页结果"
    )
    @GetMapping("/user")
    public CommonResponse<IPage<VideoVO>> selectUserVideo (
            @Size(min = 1) @Parameter(description = "分页页码, 从1开始") int page,
            @Size(min = 4) @Parameter(description = "分页大小, 最小每页4个") int size
    ) {
        Long userId = authenticationSupport.getCurrentUserId();

        // 构造查询条件, 查询当前用户的视频
        QueryWrapper<VideoVO> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        IPage<VideoVO> result = videoService.selectVideo(page, size, wrapper);

        return CommonResponse.success(result);
    }

    // 上传视频 前端先去MinioService的uploadFile接口上传视频 得到视频的存储路径再调用该接口
    /**
     * 上传视频
     * @param userUploadVideoDTO 请去该类看具体字段
     * @return 响应
     */
    @Operation(
            summary = "上传视频"
    )
    @PostMapping("/user")
    public CommonResponse<String> uploadVideo (
            @Valid @RequestBody @Parameter(description = "用户上传视频DTO, 包含视频标题, 描述, 存储路径等信息")
            UserUploadVideoDTO userUploadVideoDTO
    ) {
        Long userId = authenticationSupport.getCurrentUserId();
        userUploadVideoDTO.setUserId(userId);
        videoService.uploadVideo(userUploadVideoDTO);
        return CommonResponse.success("视频上传成功");
    }

    @Operation(
            summary = "根据视频ID查询视频"
    )
    @GetMapping("/id")
    public CommonResponse<VideoVO> getVideoById (
            @Parameter(description = "视频ID") @RequestParam Long videoId
    ) {
        VideoVO video = videoService.getVideoById(videoId);
        return CommonResponse.success(video);
    }
}
