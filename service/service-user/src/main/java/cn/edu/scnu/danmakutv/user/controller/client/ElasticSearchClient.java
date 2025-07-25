package cn.edu.scnu.danmakutv.user.controller.client;

import cn.edu.scnu.danmakutv.common.response.CommonResponse;
import cn.edu.scnu.danmakutv.domain.elasticsearch.UserProfilesES;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("service-search")
public interface ElasticSearchClient {
    @Operation(summary = "添加用户信息到ES")
    @PostMapping("/search/add-user")
    public CommonResponse<Boolean> addUser (
            @RequestBody UserProfilesES userProfilesES
    );
}
