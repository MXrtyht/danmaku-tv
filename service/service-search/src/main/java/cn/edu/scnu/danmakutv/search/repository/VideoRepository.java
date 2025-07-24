package cn.edu.scnu.danmakutv.search.repository;

import cn.edu.scnu.danmakutv.domain.elasticsearch.VideoES;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface VideoRepository extends ElasticsearchRepository<VideoES, Long> {
    // find by title like
    VideoES findByTitleLike (String keyword);
}
