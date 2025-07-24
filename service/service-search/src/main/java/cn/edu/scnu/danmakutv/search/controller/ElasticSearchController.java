package cn.edu.scnu.danmakutv.search.controller;

import cn.edu.scnu.danmakutv.common.response.CommonResponse;
import cn.edu.scnu.danmakutv.domain.elasticsearch.UserProfilesES;
import cn.edu.scnu.danmakutv.domain.elasticsearch.VideoES;
import cn.edu.scnu.danmakutv.search.service.ElasticSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@Tag(name = "ES操作相关接口")
@RequestMapping("/search")
public class ElasticSearchController {
    @Resource
    private ElasticSearchService elasticSearchService;

    @Operation(summary = "添加视频到Elasticsearch")
    @PostMapping("/add-video")
    public CommonResponse<Boolean> addVideo (
            @RequestBody VideoES videoES
    ) {
        elasticSearchService.addVideoToEs(videoES);
        return CommonResponse.success(true);
    }

    @Operation(summary = "分页模糊查找视频（标题或描述）")
    @GetMapping("/video-page")
    public CommonResponse<Page<VideoES>> searchVideos (
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<VideoES> result = elasticSearchService.searchVideosByKeywordWithPage(keyword, page, size);
        return CommonResponse.success(result);
    }


    @Operation(summary = "添加用户信息到ES")
    @PostMapping("/add-user")
    public CommonResponse<Boolean> addUser (
            @RequestBody UserProfilesES userProfilesES
    ) {
        elasticSearchService.addUserProfilesToEs(userProfilesES);
        return CommonResponse.success(true);
    }

    @Operation(summary = "分页模糊查找用户(用户名)")
    @GetMapping("/user-page")
    public CommonResponse<Page<UserProfilesES>> searchUsers (
            @RequestParam String nickname,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<UserProfilesES> result = elasticSearchService.searchUserProfilesByNicknameWithPage(nickname, page, size);
        return CommonResponse.success(result);
    }
}
