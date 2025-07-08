package cn.edu.scnu.danmakutv.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserUploadVideoDTO {

    Long userId;

    String videoUrl;

    String coverUrl;

    String title;

    boolean type; // true:原创，false:转载

    Integer duration;

    Integer area;

    List<Long> tags; // 标签id列表
}
