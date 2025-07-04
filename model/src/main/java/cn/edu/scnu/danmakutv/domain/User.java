package cn.edu.scnu.danmakutv.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("t_user")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("phone")
    private String phone;

    @TableField("email")
    private String email;

    @TableField("password")
    private String password;

    @TableField("salt")
    private String salt;

    @TableField("is_banned")
    private Boolean isBanned;

    @TableField("last_login_at")
    private LocalDateTime lastLoginAt;

    @TableField("create_at")
    private LocalDateTime createAt;
}
