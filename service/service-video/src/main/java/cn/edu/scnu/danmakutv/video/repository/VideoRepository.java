// package cn.edu.scnu.danmakutv.video.repository;
//
// import cn.edu.scnu.danmakutv.domain.elasticsearch.VideoEs;
// import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
// import org.springframework.stereotype.Repository;
//
// import java.util.List;
//
// @Repository
// public interface VideoRepository extends ElasticsearchRepository<VideoEs, Long> {
//
//     /**
//      * 根据tag搜索相应视频
//      *
//      * @param tags
//      * @return
//      */
//     List<VideoEs> findByTagsIn (List<String> tags);
// }
