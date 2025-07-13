package cn.edu.scnu.danmakutv.domain.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(name = "用户关注")
@Data
@TableName("t_user_follow")
public class UserFollow {
    @Schema(description = "id")
    @TableId(type = IdType.AUTO)
    private Long id;

    @Schema(description = "用户id")
    @TableField("user_id")
    private Long userId; // 用户ID

    @Schema(description = "被关注用户id")
    @TableField("follow_id")
    private Long followId; // 被关注用户ID

    @Schema(description = "关注分组ID, 为1时表示默认分组")
    @TableField("group_id")
    // 该字段为1时 表示默认分组
    private Long groupId; // 关注分组ID

    @Schema(description = "关注时间")
    @TableField("create_at")
    private LocalDateTime createAt; // 关注时间
}
