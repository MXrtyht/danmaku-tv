package cn.edu.scnu.danmakutv.controller;

import cn.edu.scnu.danmakutv.common.authentication.AuthenticationSupport;
import cn.edu.scnu.danmakutv.common.exception.DanmakuException;
import cn.edu.scnu.danmakutv.common.response.CommonResponse;
import cn.edu.scnu.danmakutv.domain.interaction.CollectionGroup;
import cn.edu.scnu.danmakutv.domain.interaction.VideoCollection;
import cn.edu.scnu.danmakutv.dto.interaction.AddVideoCollectionDTO;
import cn.edu.scnu.danmakutv.service.CollectionGroupService;
import cn.edu.scnu.danmakutv.service.VideoCollectionService;
import cn.edu.scnu.danmakutv.vo.interaction.CollectedVideosWithGroupVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Tag(name = "视频收藏相关接口")
@RequestMapping("/video")
@RestController
public class VideoCollectionController {
    @Resource
    private VideoCollectionService videoCollectionService;

    @Resource
    private  CollectionGroupService collectionGroupService;

    @Resource
    private AuthenticationSupport authenticationSupport;

    @Operation(summary = "根据视频id添加到收藏")
    @PostMapping("/add-collection")
    public CommonResponse<String> addVideoCollection (
            @RequestBody AddVideoCollectionDTO addVideoCollectionDTO
    ) {
        Long userId = authenticationSupport.getCurrentUserId();
        addVideoCollectionDTO.setUserId(userId);

        videoCollectionService.addVideoCollection(addVideoCollectionDTO);
        return CommonResponse.success("收藏成功");
    }

    @Operation(summary = "根据视频id取消收藏")
    @PostMapping("/delete-collection")
    public CommonResponse<String> deleteVideoCollection (
        @RequestBody @Parameter(description = "视频id", required = true) Long videoId
    ) {
        Long userId = authenticationSupport.getCurrentUserId();
        videoCollectionService.deleteVideoCollection(userId, videoId);
        return CommonResponse.success("取消收藏成功");
    }

    @Operation(summary = "以分组形式获取用户收藏的视频")
    @GetMapping("/collected-videos")
    public CommonResponse<List<CollectedVideosWithGroupVO>> getUserCollectedVideos (
    ) {
        Long userId = authenticationSupport.getCurrentUserId();
        List<CollectedVideosWithGroupVO> collectedVideos = videoCollectionService.getUserCollectedVideos(userId);
        return CommonResponse.success(collectedVideos);
    }

    @Operation(summary = "获取用户收藏分组列表")
    @GetMapping("/collection-groups")
    public CommonResponse<List<CollectionGroup>> getUserCollectionGroups () {
        Long userId = authenticationSupport.getCurrentUserId();
        List<CollectionGroup> collectionGroups = collectionGroupService.getCollectionGroupsByUserId(userId);
        return CommonResponse.success(collectionGroups);
    }

    @Operation(summary = "根据收藏分组ID获取用户收藏的视频")
    @GetMapping("/collections-by-group")
    public CommonResponse<List<VideoCollection>> getUserCollectionsByCollectionGroupId(
            @RequestParam @Parameter(description = "收藏分组ID", required = true) Long groupId
    ) {
        Long userId = authenticationSupport.getCurrentUserId();
        List<VideoCollection> videoCollections = videoCollectionService.getUserCollectionsByGroupId(userId, groupId);
        return CommonResponse.success(videoCollections);
    }

    @Operation(summary = "添加用户收藏分组")
    @PostMapping("/add-collection-group")
    public CommonResponse<String> addCollectionGroup (
            @RequestBody @Parameter(description = "收藏分组名称", required = true) String groupName
    ) {
        Long userId = authenticationSupport.getCurrentUserId();
        CollectionGroup collectionGroup = new CollectionGroup();
        collectionGroup.setUserId(userId);
        collectionGroup.setName(groupName);

        boolean isSaved = collectionGroupService.save(collectionGroup);
        if (isSaved) {
            return CommonResponse.success("收藏分组添加成功");
        } else {
            return CommonResponse.fail("收藏分组添加失败");
        }
    }

    @Operation(summary = "删除用户收藏分组, 同时也会删除该分组下的视频收藏")
    @PostMapping("/delete-collection-group")
    public CommonResponse<String> deleteCollectionGroup (
            @RequestBody @Parameter(description = "收藏分组ID", required = true) Long groupId
    ) {
        Long userId = authenticationSupport.getCurrentUserId();
        if( groupId == null || groupId <= 1 ){
            throw new DanmakuException("不能删除默认分组", 400);
        }
        videoCollectionService.deleteCollectionGroup(userId, groupId);
        return CommonResponse.success("删除成功");
    }

    @Operation(summary = "获取视频收藏数量")
    @GetMapping("/video-collect-count")
    public CommonResponse<Long> getVideoCollectCount(@RequestParam Long videoId) {
        Long count = videoCollectionService.getVideoCollectCount(videoId);
        return CommonResponse.success(count);
    }

    @Operation(summary = "获取某个视频用户是否已收藏; 已收藏 则返回相应的分组id, 否则返回null")
    @GetMapping("/is-video-collected")
    public CommonResponse<Long> isVideoCollected (@RequestParam Long videoId) {
        Long userId = authenticationSupport.getCurrentUserId();
        Long groupId = videoCollectionService.isVideoCollected(userId, videoId);
        return CommonResponse.success(groupId);
    }
}
