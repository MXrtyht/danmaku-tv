package cn.edu.scnu.danmakutv.interaction.service;

import cn.edu.scnu.danmakutv.domain.interaction.VideoComment;
import cn.edu.scnu.danmakutv.dto.interaction.AddVideoCommentDTO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.validation.constraints.Size;

public interface VideoCommentService extends IService<VideoComment> {
    Long addComment (AddVideoCommentDTO addVideoCommentDTO);

    void deleteComment (Long userId, Long commentId);

    IPage<VideoComment> getVideoComments (@Size(min = 1) Long videoId, @Size(min = 1) int page, @Size(min = 4) int size);
}
