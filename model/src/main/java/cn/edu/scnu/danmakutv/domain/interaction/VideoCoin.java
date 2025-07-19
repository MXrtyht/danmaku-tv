package cn.edu.scnu.danmakutv.domain.interaction;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Schema(name = "视频投币")
@Data
@TableName("t_video_coin")
public class VideoCoin {
    @Schema(description = "视频投稿id")
    @TableId(type = IdType.AUTO)
    private Long id;

    @Schema(description = "用户id")
    @TableField("userId")
    private Long userId;

    @Schema(description = "视频投稿id")
    @TableField("videoId")
    private Long videoId;

    @Schema(description = "投币数")
    @TableField("amount")
    private Integer amount;

    @Schema(description = "创建时间")
    @TableField("createTime")
    private Date createTime;

    @Schema(description = "更新时间")
    @TableField("updateTime")
    private Date updateTime;
}
