package cn.edu.scnu.danmakutv.search.repository;

import cn.edu.scnu.danmakutv.domain.elasticsearch.VideoES;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface VideoRepository extends ElasticsearchRepository<VideoES, Long> {

    Page<VideoES> findByTitleLikeOrDescriptionLike (String title, String description, Pageable pageable);
}
