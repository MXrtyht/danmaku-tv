package cn.edu.scnu.danmakutv.domain.interaction;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Schema(name = "用户硬币数")
@Data
@TableName("t_user_coin")
public class UserCoin {
    @Schema(description = "主键id")
    @TableId(type = IdType.AUTO)
    private Long id;

    @Schema(description = "用户id")
    @TableField("userId")
    private Long userId;

    @Schema(description = "硬币总数")
    @TableField("amount")
    private Integer amount;

    @Schema(description = "创建时间")
    @TableField("createTime")
    private Date createTime;

    @Schema(description = "更新时间")
    @TableField("updateTime")
    private Date updateTime;
}
