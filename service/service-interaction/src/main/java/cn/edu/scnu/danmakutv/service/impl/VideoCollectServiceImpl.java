package cn.edu.scnu.danmakutv.service.impl;

import cn.edu.scnu.danmakutv.common.exception.DanmakuException;
import cn.edu.scnu.danmakutv.domain.interaction.VideoCollection;
import cn.edu.scnu.danmakutv.domain.video.Video;
import cn.edu.scnu.danmakutv.mapper.VideoCollectMapper;
import cn.edu.scnu.danmakutv.service.VideoCollectService;
import cn.edu.scnu.danmakutv.video.mapper.VideoMapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class VideoCollectServiceImpl extends ServiceImpl<VideoCollectMapper, VideoCollection> implements VideoCollectService {
    private VideoMapper videoMapper;
    private VideoCollectMapper videoCollectMapper;

    /**
     * 收藏视频
     * @param videoCollection 要被收藏的视频
     * @param userId 用户ID
     */
    @Override
    public void addVideoCollection(VideoCollection videoCollection, Long userId){
        Long videoId = videoCollection.getVideoId();
        Long groupId = videoCollection.getGroupId();
        if(videoId == null || groupId == null) {
            throw new DanmakuException("参数异常！", 3003);
        }
        Video video = videoMapper.selectById(videoId);
        if (video==null){
            throw new DanmakuException("非法视频！", 3004);
        }
        //删除原有视频收藏
        videoCollectMapper.delete(
                new QueryWrapper<VideoCollection>()
                        .eq("userId", userId)
                        .eq("videoId", videoId)
        );
        //添加新的视频收藏
        videoCollection.setVideoId(userId);
        videoCollection.setCreateTime(new Date());
        videoCollectMapper.insert(videoCollection);
    }

    /**
     * 删除收藏的视频
     * @param videoId 视频ID
     * @param userId 用户ID
     */
    @Override
    public void deleteVideoCollection(Long videoId, Long userId) {
        videoCollectMapper.delete(new QueryWrapper<VideoCollection>().eq("videoId", videoId)
                .eq("userId", userId));
    }

    /**
     * 获取收藏的视频数量、用户是否收藏过视频
     * @param videoId 视频ID
     * @param userId 用户ID
     * @return 包含收藏视频数量与用户是否收藏过视频的Map
     */
    @Override
    public Map<String, Object> getVideoCollections(Long videoId, Long userId) {
        Long count = videoCollectMapper.selectCount(new QueryWrapper<VideoCollection>().eq("videoId", videoId));
        boolean like = false;
        if (userId != null) {
            VideoCollection videoCollection = videoCollectMapper.selectOne(
                    new QueryWrapper<VideoCollection>().eq("videoId", videoId).eq("userId", userId)
            );
            like = videoCollection != null;
        }
        Map<String, Object> result = new HashMap<>();
        result.put("count", count);
        result.put("like", like);
        return result;
    }
}
