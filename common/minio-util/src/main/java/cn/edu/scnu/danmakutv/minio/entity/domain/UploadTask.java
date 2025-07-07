package cn.edu.scnu.danmakutv.minio.entity.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("t_upload_task")
public class UploadTask {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("uuid")
    private String uuid;

    @TableField("md5")
    private String md5;

    @TableField("upload_id")
    private String uploadId;

    @TableField("total_chunk")
    private Integer totalChunk;

    @TableField("name")
    private String name;

    @TableField("size")
    private Long size;

    @TableField("is_uploaded")
    private Boolean isUploaded;

    @TableField("create_at")
    private LocalDateTime createAt;
}
