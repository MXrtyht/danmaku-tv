package cn.edu.scnu.danmakutv.vo.authentication;

import cn.edu.scnu.danmakutv.enums.GenderType;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserProfilesVO {

    private String nickname;

    private GenderType gender;

    private LocalDate birthday;

    private String sign;

    private String announcement;

    private String avatar;

    private Integer coin;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
