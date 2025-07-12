package cn.edu.scnu.danmakutv.service.impl;

import cn.edu.scnu.danmakutv.common.exception.DanmakuException;
import cn.edu.scnu.danmakutv.domain.VideoLike;
import cn.edu.scnu.danmakutv.mapper.VideoLikeMapper;
import cn.edu.scnu.danmakutv.service.VideoLikeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class VideoLikeServiceImpl extends ServiceImpl<VideoLikeMapper, VideoLike> implements VideoLikeService {
    /**
     * 添加视频点赞
     * @param userId 用户ID
     * @param videoId 视频ID
     */
    @Override
    public void addVideoLike (Long userId, Long videoId) {
        // TODO 检查videoId是否存在 集成微服务 调用video-service的接口
        VideoLike videoLike = baseMapper.selectOne(
                new QueryWrapper<VideoLike>().eq("video_id", videoId)
                                             .eq("user_id", userId)
        );
        if (videoLike != null) {
            throw new DanmakuException("已点赞", 3001);
        }

        videoLike = new VideoLike();
        videoLike.setUserId(userId);
        videoLike.setVideoId(videoId);

        baseMapper.insert(videoLike);
    }

    /**
     * 删除视频点赞
     * @param userId 用户ID
     * @param videoId 视频ID
     */
    @Override
    public void deleteVideoLike (Long userId, Long videoId) {
        // TODO 检查videoId是否存在 集成微服务 调用video-service的接口
        VideoLike videoLike = baseMapper.selectOne(
                new QueryWrapper<VideoLike>().eq("video_id", videoId)
                                             .eq("user_id", userId)
        );
        if (videoLike == null) {
            throw new DanmakuException("未点赞", 3002);
        }

        baseMapper.delete(new QueryWrapper<VideoLike>().eq("video_id", videoId)
                                                       .eq("user_id", userId));
    }

    /**
     * 获取视频点赞数量和用户是否点赞
     * @param videoId 视频ID
     * @param userId 用户ID
     * @return 包含点赞数量和当前用户是否点赞的Map
     */
    @Override
    public Map<String, Object> getVideoLikeCount (Long videoId, Long userId) {
        // TODO 检查videoId是否存在 集成微服务 调用video-service的接口
        Long likeCount = baseMapper.selectCount(new QueryWrapper<VideoLike>().eq("video_id", videoId));
        boolean isLiked = false;
        if (userId != null) {
            VideoLike videoLike = baseMapper.selectOne(
                    new QueryWrapper<VideoLike>().eq("video_id", videoId)
                                                 .eq("user_id", userId)
            );
            isLiked = videoLike != null;
        }
        Map<String, Object> result = new HashMap<>();
        result.put("likeCount", likeCount);
        result.put("isLiked", isLiked);
        return result;
    }
}
