package cn.edu.scnu.danmakutv.video.service.impl;

import cn.edu.scnu.danmakutv.domain.video.VideoTag;
import cn.edu.scnu.danmakutv.video.mapper.VideoTagMapper;
import cn.edu.scnu.danmakutv.video.service.VideoTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoTagServiceImpl extends ServiceImpl<VideoTagMapper, VideoTag> implements VideoTagService {

    /**
     * 根据视频id获取视频标签
     * @param videoId
     * @return
     */
    @Override
    public List<String> selectTagsByVideoId(Long videoId) {
        return baseMapper.selectTagsByVideoId(videoId);
    }
}
