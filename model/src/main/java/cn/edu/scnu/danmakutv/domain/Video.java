package cn.edu.scnu.danmakutv.domain;

import cn.edu.scnu.danmakutv.enums.VideoType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("t_video")
public class Video {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("user_id")
    private Long userId;

    @TableField("video_url")
    private String videoUrl;

    @TableField("cover_url")
    private String coverUrl;

    @TableField("title")
    private String title;

    @TableField("type")
    private VideoType type;

    @TableField("duration")
    private Integer duration;

    @TableField("area")
    private Integer area;

    @TableField("create_at")
    private LocalDateTime createdAt;

    @TableField("update_at")
    private LocalDateTime updatedAt;
}
