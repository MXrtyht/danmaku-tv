// package cn.edu.scnu.danmakutv.video.service;
//
// import cn.edu.scnu.danmakutv.domain.elasticsearch.VideoEs;
// import cn.edu.scnu.danmakutv.dto.video.UserUploadVideoDTO;
//
// import java.util.List;
//
// public interface ElasticSearchService {
//
//     /**
//      * 在es中添加数据
//      *
//      * @param userUploadVideoDTO 视频上传对象
//      * @param videoId            视频id
//      */
//     void addVideoEs (UserUploadVideoDTO userUploadVideoDTO, Long videoId);
//
//     /**
//      * 根据tag搜索相应视频
//      *
//      * @param tags 标签列表
//      * @return 视频列表
//      */
//     List<VideoEs> findByTagsIn (List<String> tags);
// }
