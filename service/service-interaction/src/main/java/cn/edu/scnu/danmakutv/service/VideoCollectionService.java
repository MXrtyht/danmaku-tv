package cn.edu.scnu.danmakutv.service;

import cn.edu.scnu.danmakutv.domain.interaction.VideoCollection;
import cn.edu.scnu.danmakutv.dto.interaction.AddVideoCollectionDTO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface VideoCollectionService extends IService<VideoCollection> {
    void addVideoCollection (AddVideoCollectionDTO addVideoCollectionDTO);
}
