package cn.edu.scnu.danmakutv.service;

import cn.edu.scnu.danmakutv.domain.interaction.VideoCollection;
import cn.edu.scnu.danmakutv.dto.interaction.AddVideoCollectionDTO;
import cn.edu.scnu.danmakutv.vo.interaction.CollectedVideosWithGroupVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface VideoCollectionService extends IService<VideoCollection> {
    void addVideoCollection (AddVideoCollectionDTO addVideoCollectionDTO);

    void deleteVideoCollection (Long userId, Long videoId);

    List<CollectedVideosWithGroupVO> getUserCollectedVideos (Long userId);

    void deleteCollectionGroup (Long userId, Long groupId);
}
