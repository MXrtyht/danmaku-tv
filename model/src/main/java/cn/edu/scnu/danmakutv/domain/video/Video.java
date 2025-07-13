package cn.edu.scnu.danmakutv.domain.video;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(name = "视频实体类")
@Data
@TableName("t_video")
public class Video {
    @Schema(description = "id")
    @TableId(type = IdType.AUTO)
    private Long id;

    @Schema(description = "用户id")
    @TableField("user_id")
    private Long userId;

    @Schema(description = "视频URL")
    @TableField("video_url")
    private String videoUrl;

    @Schema(description = "封面URL")
    @TableField("cover_url")
    private String coverUrl;

    @Schema(description = "标题")
    @TableField("title")
    private String title;

    @Schema(description = "视频类型, 0自制, 1转载")
    @TableField("type")
    private boolean type;

    @Schema(description = "视频时长 单位秒")
    @TableField("duration")
    private Integer duration;

    @Schema(description = "视频分区")
    @TableField("area")
    private Integer area;

    @Schema(description = "创建时间")
    @TableField("create_at")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    @TableField("update_at")
    private LocalDateTime updatedAt;
}
