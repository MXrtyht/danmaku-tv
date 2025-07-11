package cn.edu.scnu.danmakutv.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserFollowDTO {

    private Long userId; // 用户ID

    private Long followId; // 被关注用户ID

    // 该字段为1时 表示默认分组
    private Long groupId; // 关注分组ID

    private LocalDateTime createAt; // 关注时间
}
