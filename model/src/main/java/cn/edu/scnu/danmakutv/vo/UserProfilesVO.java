package cn.edu.scnu.danmakutv.vo;

import cn.edu.scnu.danmakutv.enums.GenderType;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserProfilesVO {
    private Long uid;

    private String nickname;

    private GenderType gender;

    private LocalDate birthday;

    private String sign;

    private String announcement;

    private String avatar;

    private Integer coin;
}
