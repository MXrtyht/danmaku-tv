package cn.edu.scnu.danmakutv.domain;

import cn.edu.scnu.danmakutv.enums.AreaType;
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
    private String userId;

    @TableField("video_url")
    private String videoUrl;

    @TableField("cover_url")
    private String coverUrl;

    @TableField("title")
    private String title;

    @TableField("type")
    private VideoType type;

    @TableField("duration")
    private int duration;

    @TableField("area")
    private AreaType area;

    @TableField("create_at")
    private LocalDateTime createdAt;

    @TableField("update_at")
    private LocalDateTime updatedAt;
}
