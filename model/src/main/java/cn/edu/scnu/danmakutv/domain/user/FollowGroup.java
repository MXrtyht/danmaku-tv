package cn.edu.scnu.danmakutv.domain.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema( name = "用户关注分组")
@Data
@TableName("t_follow_group")
public class FollowGroup {
    @Schema(description = "id")
    @TableId(type = IdType.AUTO)
    private Long id;

    @Schema(description = "用户id")
    @TableField("user_id")
    private Long userId; // 用户ID

    @Schema(description = "分组名称")
    @TableField("name")
    private String name; // 分组名称

    @Schema(description = "分组创建时间")
    @TableField("create_at")
    private String createAt; // 分组创建时间

    @Schema(description = "分组更新时间")
    @TableField("update_at")
    private String updateAt; // 分组更新时间
}
