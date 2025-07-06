package cn.edu.scnu.danmakutv.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_tag")
public class VideoTag {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("name")
    private String name;
}
