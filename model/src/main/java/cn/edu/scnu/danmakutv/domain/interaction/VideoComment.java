package cn.edu.scnu.danmakutv.domain.interaction;

import cn.edu.scnu.danmakutv.domain.user.UserProfiles;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Schema(name = "视频评论")
@Data
@TableName("t_video_comment")
public class VideoComment {
    @Schema(description = "主键id")
    @TableId(type = IdType.AUTO)
    private Long id;

    @Schema(description = "视频id")
    @TableField("videoId")
    private Long videoId;

    @Schema(description = "用户id")
    @TableField("userId")
    private Long userId;

    @Schema(description = "评论")
    @TableField("comment")
    private String comment;

    @Schema(description = "回复用户id")
    @TableField("replyUserId")
    private Long replyUserId;

    @Schema(description = "根节点评论id")
    @TableField("rootId")
    private Long rootId;

    @Schema(description = "创建时间")
    @TableField("createTime")
    private Date createTime;

    @Schema(description = "更新时间")
    @TableField("updateTime")
    private Date updateTime;

    @Schema(description = "二级结构")
    private List<VideoComment> childList;

    @Schema(description = "二级用户信息")
    private UserProfiles userInfo;

    @Schema(description = "二级回复用户信息")
    private UserProfiles replyUserInfo;

}
