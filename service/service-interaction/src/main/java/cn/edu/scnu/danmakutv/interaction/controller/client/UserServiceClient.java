package cn.edu.scnu.danmakutv.interaction.controller.client;

import cn.edu.scnu.danmakutv.common.response.CommonResponse;
import cn.edu.scnu.danmakutv.dto.user.UpdateUserCoinDTO;
import cn.edu.scnu.danmakutv.vo.user.UserProfilesVO;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("service-user")
public interface UserServiceClient {
    @GetMapping("/user/info-by-id")
    CommonResponse<UserProfilesVO> getUserProfilesById (
            @Parameter(description = "用户ID", required = true) @RequestParam Long userId
    );

    @PostMapping("/user/update-coin")
    CommonResponse<Boolean> updateUserCoin (
            @RequestBody UpdateUserCoinDTO updateUserCoinDTO
    );
}
