package cn.edu.scnu.danmakutv.vo;

import cn.edu.scnu.danmakutv.enums.GenderType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserProfilesVO {
    private Long uid;

    @NotBlank(message = "用户名不能为空")
    @Size(min = 1, max = 20, message = "用户名长度少于20")
    private String nickname;

    private GenderType gender;

    private LocalDate birthday;

    private String sign;

    private String announcement;

    private String avatar;

    private Integer coin;
}
