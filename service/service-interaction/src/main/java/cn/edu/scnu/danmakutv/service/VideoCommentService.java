package cn.edu.scnu.danmakutv.service;

import cn.edu.scnu.danmakutv.domain.interaction.VideoComment;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface VideoCommentService extends IService<VideoComment> {
    void addVideoComment(VideoComment videoComment, Long userId);
    Page<VideoComment> pageListVideoComments(Integer size, Integer page, Long videoId);
}
