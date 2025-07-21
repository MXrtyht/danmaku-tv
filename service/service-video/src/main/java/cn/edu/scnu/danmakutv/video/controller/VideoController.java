package cn.edu.scnu.danmakutv.video.controller;

import cn.edu.scnu.danmakutv.common.authentication.AuthenticationSupport;
import cn.edu.scnu.danmakutv.common.response.CommonResponse;
import cn.edu.scnu.danmakutv.domain.elasticsearch.VideoEs;
import cn.edu.scnu.danmakutv.domain.video.Video;
import cn.edu.scnu.danmakutv.dto.video.GetRecommendedVideoDTO;
import cn.edu.scnu.danmakutv.dto.video.UpdateVideoDTO;
import cn.edu.scnu.danmakutv.dto.video.UserUploadVideoDTO;
// import cn.edu.scnu.danmakutv.video.service.ElasticSearchService;
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

import java.util.List;

@CrossOrigin
@Tag(name = "视频操作相关接口")
@RestController
@RequestMapping("/video")
public class VideoController {
    @Resource
    private AuthenticationSupport authenticationSupport;

    @Resource
    private VideoService videoService;

    // @Resource
    // private ElasticSearchService elasticSearchService;

    /**
     * 查询所有视频
     *
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
     *
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
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);

        IPage<VideoVO> result = videoService.selectVideo(page, size, wrapper);

        return CommonResponse.success(result);
    }

    // 上传视频 前端先去MinioService的uploadFile接口上传视频 得到视频的存储路径再调用该接口

    /**
     * 上传视频
     *
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
        Long videoId = videoService.uploadVideo(userUploadVideoDTO);
        // 在es中添加一条数据
        // elasticSearchService.addVideoEs(userUploadVideoDTO, videoId);
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

    @Operation(summary = "根据视频id列表批量查询视频")
    @PostMapping("/batch")
    public CommonResponse<List<Video>> getVideosByIds (
            @RequestBody @Parameter(description = "视频ID列表") @Size(min = 1) List<Long> videoIds
    ) {
        List<Video> result = videoService.getVideosByIds(videoIds);
        return CommonResponse.success(result);
    }

    /**
     * 删除视频
     *
     * @return 响应
     */
    @Operation(summary = "删除视频")
    @PostMapping("/delete-video")
    public CommonResponse<String> deleteVideo (
            @RequestBody Long videoId
    ) {
        Long userId = authenticationSupport.getCurrentUserId();
        videoService.deleteVideo(userId, videoId);
        return CommonResponse.success("视频删除成功");
    }

    /**
     * 修改视频信息(
     *
     * @param dto UpdateVideoDTO
     * @return 响应
     */
    @Operation(summary = "修改视频信息")
    @PostMapping("/update-video")
    public CommonResponse<String> updateVideo (
            @RequestBody @Valid UpdateVideoDTO dto
    ) {
        Long userId = authenticationSupport.getCurrentUserId();
        videoService.updateVideo(dto);
        return CommonResponse.success("视频更新");
    }

    /**
     * 视频相关推荐
     *
     * @param getRecommendedVideoDTO 推荐视频的查询参数, 包含标签ID列表和限制数量
     * @return 推荐视频列表
     */
    @Operation(summary = "视频相关推荐")
    @PostMapping("/recommendations")
    public CommonResponse<List<Video>> getRecommendedVideos (
            @RequestBody @Parameter(description = "推荐视频DTO, 包含标签ID列表和限制数量")
            GetRecommendedVideoDTO getRecommendedVideoDTO
    ) {
        List<Video> videos = videoService.getRecommendedVideos(getRecommendedVideoDTO);
        return CommonResponse.success(videos);
    }

    /**
     * 根据tag搜索相应视频
     *
     * @param tags
     * @return
     */
    @Operation(summary = "根据tag搜索相应视频")
    @GetMapping("/search")
    public CommonResponse<List<VideoEs>> searchVideo (@RequestParam List<String> tags) {
        // List<VideoEs> videoEsList = elasticSearchService.findByTagsIn(tags);
        // return CommonResponse.success(videoEsList);
        return CommonResponse.success(null);
    }
}
