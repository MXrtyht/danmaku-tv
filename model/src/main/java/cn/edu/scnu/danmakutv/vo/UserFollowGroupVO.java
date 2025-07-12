package cn.edu.scnu.danmakutv.vo;

import cn.edu.scnu.danmakutv.domain.UserProfiles;
import lombok.Data;

import java.util.List;

@Data
public class UserFollowGroupVO {

    private Long id;

    private Long userId; // 用户ID

    private String name; // 分组名称

    private String createdAt; // 分组创建时间

    private String updatedAt; // 分组更新时间

    private List<UserProfiles> userProfilesList; // 分组内用户列表
}
