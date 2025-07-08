package cn.edu.scnu.danmakutv.dto;

import jakarta.validation.constraints.Size;
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

    @Size(min = 1, max = 10, message = "标签数量必须在1到10之间")
    List<Long> tags; // 标签id列表
}
