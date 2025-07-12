package cn.edu.scnu.danmakutv.dto;

import lombok.Data;

@Data
public class CreateFollowGroupDTO {
    Long userId;
    String name; // 分组名称
}
