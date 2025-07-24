package cn.edu.scnu.danmakutv.video.controller.client;

import cn.edu.scnu.danmakutv.common.response.CommonResponse;
import cn.edu.scnu.danmakutv.domain.elasticsearch.VideoES;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("service-search")
public interface ElasticSearchClient {
    @PostMapping("/search/add-video")
    public CommonResponse<Boolean> addVideo (
            @RequestBody VideoES videoES
    );
}
