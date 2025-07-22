package cn.edu.scnu.danmakutv.interaction.service;

import cn.edu.scnu.danmakutv.domain.interaction.VideoCoinRecord;
import cn.edu.scnu.danmakutv.dto.interaction.AddVideoCoinDTO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface VideoCoinService extends IService<VideoCoinRecord> {
    void coinVideoById (AddVideoCoinDTO addVideoCoinDTO);

    Boolean checkVideoCoinRecord (Long userId, Long videoId);

    Long getVideoCoinCount (Long videoId);
}
