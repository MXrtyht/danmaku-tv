package cn.edu.scnu.danmakutv.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_follow_group")
public class FollowGroup {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("user_id")
    private Long userId; // 用户ID

    @TableField("name")
    private String name; // 分组名称

    @TableField("created_at")
    private String createdAt; // 分组创建时间

    @TableField("updated_at")
    private String updatedAt; // 分组更新时间
}
