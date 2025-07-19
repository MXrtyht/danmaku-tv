package cn.edu.scnu.danmakutv.domain.video;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(name = "视频与标签标签联系实体集")
@Data
@TableName("r_video_tag")
public class VideoTagRelation {
    @Schema(description = "id")
    @TableId(type = IdType.AUTO)
    private Long id;

    @Schema(description = "视频id")
    @TableField("video_id")
    private Long videoId;

    @Schema(description = "标签id")
    @TableField("tag_id")
    private Long tagId;
}
