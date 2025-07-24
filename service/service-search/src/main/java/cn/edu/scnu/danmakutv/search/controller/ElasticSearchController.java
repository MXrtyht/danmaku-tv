package cn.edu.scnu.danmakutv.search.controller;

import cn.edu.scnu.danmakutv.common.response.CommonResponse;
import cn.edu.scnu.danmakutv.domain.elasticsearch.VideoES;
import cn.edu.scnu.danmakutv.search.service.ElasticSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
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

    @Operation(summary = "查找视频")
    @GetMapping("/find-video")
    public CommonResponse<VideoES> findVideo (
            @RequestParam @Parameter(description = "关键词")
            String keyWord
    ) {
        VideoES videoES = elasticSearchService.searchVideosByKeyword(keyWord);
        return CommonResponse.success(videoES);
    }
}
