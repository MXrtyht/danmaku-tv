package cn.edu.scnu.danmakutv.vo;

import cn.edu.scnu.danmakutv.domain.VideoTag;
import cn.edu.scnu.danmakutv.enums.AreaType;
import cn.edu.scnu.danmakutv.enums.VideoType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class VideoVO {

    private Long id;

    private String userId;

    private String videoUrl;

    private String coverUrl;

    private String title;

    private VideoType type;

    private int duration;

    private AreaType area;

    private List<VideoTag> tags;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;
}
