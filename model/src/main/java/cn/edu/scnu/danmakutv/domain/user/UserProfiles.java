package cn.edu.scnu.danmakutv.domain.user;

import cn.edu.scnu.danmakutv.enums.GenderType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(name = "用户个人资料")
@Data
@TableName("t_user_profiles")
public class UserProfiles {
    @Schema(description = "id")
    @TableId(type = IdType.AUTO)
    private Long id;

    @Schema(description = "用户id")
    @TableField("user_id")
    private Long userId;

    @Schema(description = "昵称")
    @TableField("nickname")
    private String nickname;

    @Schema(description = "性别")
    @TableField("gender")
    private GenderType gender;

    @Schema(description = "生日")
    @TableField("birthday")
    private LocalDate birthday;

    @Schema(description = "个性签名")
    @TableField("sign")
    private String sign;

    @Schema(description = "个人主页")
    @TableField("announcement")
    private String announcement;

    @Schema(description = "头像url")
    @TableField("avatar")
    private String avatar;

    @Schema(description = "硬币数")
    @TableField("coin")
    private Integer coin;

    @Schema(description = "创建时间")
    @TableField("create_at")
    private LocalDateTime createAt;

    @Schema(description = "更新时间")
    @TableField("update_at")
    private LocalDateTime updateAt;
}
