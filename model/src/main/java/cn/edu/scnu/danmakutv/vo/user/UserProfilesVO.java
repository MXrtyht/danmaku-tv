package cn.edu.scnu.danmakutv.vo.user;

import cn.edu.scnu.danmakutv.enums.GenderType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Schema(name = "用户个人资料,视图")
@Data
public class UserProfilesVO {
    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "昵称")
    @NotBlank(message = "用户名不能为空")
    @Size(min = 1, max = 20, message = "用户名长度少于20")
    private String nickname;

    @Schema(description = "性别")
    private GenderType gender;

    @Schema(description = "生日")
    private LocalDate birthday;

    @Schema(description = "个性签名")
    private String sign;

    @Schema(description = "主页公告")
    private String announcement;

    @Schema(description = "头像url")
    private String avatar;

    @Schema(description = "用户硬币数量")
    private Integer coin;
}
