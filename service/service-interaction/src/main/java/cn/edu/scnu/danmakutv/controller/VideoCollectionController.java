package cn.edu.scnu.danmakutv.controller;

import cn.edu.scnu.danmakutv.common.authentication.AuthenticationSupport;
import cn.edu.scnu.danmakutv.common.response.CommonResponse;
import cn.edu.scnu.danmakutv.dto.interaction.AddVideoCollectionDTO;
import cn.edu.scnu.danmakutv.service.CollectionGroupService;
import cn.edu.scnu.danmakutv.service.VideoCollectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Tag(name = "视频收藏相关接口")
@RequestMapping("/video")
@RestController
public class VideoCollectionController {
    @Resource
    private VideoCollectionService videoCollectionService;

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
}
