package cn.edu.scnu.danmakutv.domain.user;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import cn.edu.scnu.danmakutv.enums.GenderType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(name = "用户动态")
@Data
@TableName("t_user_moments")
public class UserMoments {
	
    @Schema(description = "id")
    @TableId(type = IdType.AUTO)
    private Long id;
    
    @Schema(description = "用户id")
    @TableField("user_id")
    private Long userId;
    
    @Schema(description = "内容id")
    @TableField("content_id")
    private Long contentId;
    
    @Schema(description = "创建时间")
    @TableField("create_time")
    private Date createTime;
}
