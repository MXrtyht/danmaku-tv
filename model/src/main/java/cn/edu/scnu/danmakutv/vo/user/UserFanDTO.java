package cn.edu.scnu.danmakutv.vo.user;

import cn.edu.scnu.danmakutv.domain.user.UserProfiles;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(name = "用户粉丝,视图")
@Data
public class UserFanDTO {
    @Schema(description = "用户粉丝资料")
    UserProfiles userProfiles; // 粉丝用户资料

    @Schema(description = "是否回关")
    Boolean isFollowBack; // 是否回关
}
