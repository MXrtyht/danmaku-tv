package cn.edu.scnu.danmakutv.video.service.impl;

import cn.edu.scnu.danmakutv.domain.elasticsearch.VideoEs;
import cn.edu.scnu.danmakutv.dto.video.UserUploadVideoDTO;
import cn.edu.scnu.danmakutv.video.repository.VideoRepository;
import cn.edu.scnu.danmakutv.video.service.ElasticSearchService;
import cn.edu.scnu.danmakutv.video.service.VideoTagService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {

    @Resource
    private VideoTagService videoTagService;

    @Resource
    private VideoRepository videoRepository;

    /**
     * 在es中添加数据
     * @param userUploadVideoDTO 视频上传对象
     * @param videoId 视频id
     */
    @Override
    public void addVideoEs(UserUploadVideoDTO userUploadVideoDTO,Long videoId) {
        VideoEs videoEs = new VideoEs();
        videoEs.setId(videoId);
        videoEs.setTitle(userUploadVideoDTO.getTitle());
        videoEs.setArea(userUploadVideoDTO.getArea());
        // 查询标签名称并设置
        List<String> tags = videoTagService.selectTagsByVideoId(videoId);
        videoEs.setTags(tags);
        videoRepository.save(videoEs);
    }

    /**
     * 根据tag搜索相应视频
     * @param tags
     * @return
     */
    @Override
    public List<VideoEs> findByTagsIn(List<String> tags) {
        return videoRepository.findByTagsIn(tags);
    }

}
