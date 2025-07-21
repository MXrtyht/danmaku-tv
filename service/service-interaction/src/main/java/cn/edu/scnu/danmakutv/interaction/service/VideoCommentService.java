package cn.edu.scnu.danmakutv.interaction.service;

import cn.edu.scnu.danmakutv.domain.interaction.VideoComment;
import cn.edu.scnu.danmakutv.dto.interaction.AddVideoCommentDTO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface VideoCommentService extends IService<VideoComment> {
    Long addComment (AddVideoCommentDTO addVideoCommentDTO);
}
