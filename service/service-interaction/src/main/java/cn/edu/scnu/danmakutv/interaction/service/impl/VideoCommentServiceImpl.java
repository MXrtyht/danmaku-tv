package cn.edu.scnu.danmakutv.interaction.service.impl;

import cn.edu.scnu.danmakutv.domain.interaction.VideoComment;
import cn.edu.scnu.danmakutv.dto.video.AddVideoCommentDTO;
import cn.edu.scnu.danmakutv.interaction.mapper.VideoCommentMapper;
import cn.edu.scnu.danmakutv.interaction.service.VideoCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VideoCommentServiceImpl extends ServiceImpl<VideoCommentMapper, VideoComment> implements VideoCommentService {
    @Override
    public Long addComment (AddVideoCommentDTO addVideoCommentDTO) {
        VideoComment videoComment = new VideoComment();
        BeanUtils.copyProperties(addVideoCommentDTO,videoComment);
        videoComment.setCreateTime(LocalDateTime.now());
        this.baseMapper.insert(videoComment);
        return videoComment.getId();
    }
}
