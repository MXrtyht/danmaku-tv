package cn.edu.scnu.danmakutv.interaction.service.impl;

import cn.edu.scnu.danmakutv.common.exception.DanmakuException;
import cn.edu.scnu.danmakutv.domain.interaction.VideoComment;
import cn.edu.scnu.danmakutv.domain.video.Video;
import cn.edu.scnu.danmakutv.dto.interaction.AddVideoCommentDTO;
import cn.edu.scnu.danmakutv.interaction.mapper.VideoCommentMapper;
import cn.edu.scnu.danmakutv.interaction.service.VideoCommentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VideoCommentServiceImpl extends ServiceImpl<VideoCommentMapper, VideoComment> implements VideoCommentService {
    /**
     * 添加视频评论
     * @param addVideoCommentDTO 评论数据传输对象
     * @return 评论ID
     */
    @Override
    public Long addComment (AddVideoCommentDTO addVideoCommentDTO) {
        VideoComment videoComment = new VideoComment();
        BeanUtils.copyProperties(addVideoCommentDTO,videoComment);
        videoComment.setCreateTime(LocalDateTime.now());
        this.baseMapper.insert(videoComment);
        return videoComment.getId();
    }

    /**
     * 删除视频评论
     * @param userId 用户ID
     * @param commentId 评论ID
     */
    @Override
    public void deleteComment (Long userId, Long commentId) {

        VideoComment videoComment = this.baseMapper.selectById(commentId);
        if(videoComment==null || !videoComment.getUserId().equals(userId)){
            throw new DanmakuException("评论不存在",400);
        }

        this.baseMapper.delete(
        new QueryWrapper<VideoComment>()
                .eq("id", commentId)
                .eq("user_id", userId)
        );
    }

    /**
     * 分页获取视频评论列表
     * @param videoId 视频ID
     * @param page 当前页码
     * @param size 每页显示条数
     * @return 分页结果
     */
    @Override
    public IPage<VideoComment> getVideoComments (Long videoId, int page, int size) {
        // 分页查询视频评论
        IPage<VideoComment> pageRequest = new Page<>(page, size);
        QueryWrapper<VideoComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("video_id", videoId).orderByDesc("create_at");

        IPage<VideoComment> videoCommentIPage = this.baseMapper.selectPage(pageRequest, queryWrapper);

        return videoCommentIPage;
    }
}
