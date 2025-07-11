package cn.edu.scnu.danmakutv.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("t_user_follow")
public class UserFollow {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("user_id")
    private Long userId; // 用户ID

    @TableField("follow_id")
    private Long followId; // 被关注用户ID

    @TableField("group_id")
    // 该字段为1时 表示默认分组
    private Long groupId; // 关注分组ID

    @TableField("create_at")
    private LocalDateTime createAt; // 关注时间
}
