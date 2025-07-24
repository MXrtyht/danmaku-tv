package cn.edu.scnu.danmakutv.search.service;

import cn.edu.scnu.danmakutv.domain.elasticsearch.VideoES;
import cn.edu.scnu.danmakutv.search.repository.VideoRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class ElasticSearchService {
    @Resource
    private VideoRepository videoRepository;

    public void addVideoToEs (VideoES videoES) {
        System.out.println("test");
        this.videoRepository.save(videoES);
    }

    public VideoES searchVideosByKeyword (String keyword) {
        return this.videoRepository.findByTitleLike(keyword);
    }
}
