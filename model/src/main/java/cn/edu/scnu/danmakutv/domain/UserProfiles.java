package cn.edu.scnu.danmakutv.domain;

import cn.edu.scnu.danmakutv.enums.GenderType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("t_user_profiles")
public class UserProfiles {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("user_id")
    private Long userId;

    @TableField("nickname")
    private String nickname;

    @TableField("gender")
    private GenderType gender;

    @TableField("birthday")
    private LocalDate birthday;

    @TableField("sign")
    private String sign;

    @TableField("announcement")
    private String announcement;

    @TableField("avatar")
    private String avatar;

    @TableField("coin")
    private Integer coin;

    @TableField("create_at")
    private LocalDateTime createAt;

    @TableField("update_at")
    private LocalDateTime updateAt;
}
