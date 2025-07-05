package cn.edu.scnu.danmakutv.dto;

import cn.edu.scnu.danmakutv.enums.GenderType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserProfilesDTO {

    private String nickname;

    private GenderType gender;

    private LocalDate birthday;

    private String sign;

    private String announcement;

    private String avatar;

    private Integer coin;
}
