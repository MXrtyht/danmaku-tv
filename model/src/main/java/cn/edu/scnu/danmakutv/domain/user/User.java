package cn.edu.scnu.danmakutv.domain.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(name = "用户实体类")
@Data
@TableName("t_user")
public class User {
    @Schema(name = "用户id, 主键")
    @TableId(type = IdType.AUTO)
    private Long id;

    @Schema(name = "手机号")
    @TableField("phone")
    private String phone;

    @Schema(name = "邮箱")
    @TableField("email")
    private String email;

    @Schema(name = "密码")
    @TableField("password")
    private String password;

    @Schema(name = "盐值, 用于加密密码")
    @TableField("salt")
    private String salt;

    @Schema(name = "状态, 是否被封禁")
    @TableField("is_banned")
    private Boolean isBanned;

    @Schema(name = "最后登录时间")
    @TableField("last_login_at")
    private LocalDateTime lastLoginAt;

    @Schema(name = "创建时间")
    @TableField("create_at")
    private LocalDateTime createAt;
}
