package cn.edu.scnu.danmakutv.video.service;

import cn.edu.scnu.danmakutv.domain.video.Video;
import cn.edu.scnu.danmakutv.dto.video.GetRecommendedVideoDTO;
import cn.edu.scnu.danmakutv.dto.video.UpdateVideoDTO;
import cn.edu.scnu.danmakutv.dto.video.UserUploadVideoDTO;
import cn.edu.scnu.danmakutv.vo.video.VideoVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.validation.constraints.Size;

import java.util.List;

public interface VideoService extends IService<Video> {
    /**
     * 分页获取视频列表
     *
     * @param page 当前页码
     * @param size 每页显示条数
     * @return 分页视频信息
     */
    IPage<VideoVO> selectVideo (int page, int size, QueryWrapper<Video> wrapper);

    /**
     * 上传视频
     *
     * @param userUploadVideoDTO 视频上传的数据传输对象
     */
    Long uploadVideo (UserUploadVideoDTO userUploadVideoDTO);

    /**
     * 根据视频ID获取视频信息
     *
     * @param videoId 视频ID
     * @return 视频信息对象
     */
    VideoVO getVideoById (Long videoId);

    /**
     * 根据视频ID数组获取视频列表
     *
     * @param videoIds 视频ID数组
     * @return 包含视频信息的分页结果
     */
    List<Video> getVideosByIds (@Size(min = 1) List<Long> videoIds);

    // 以下可能要作修改

    /**
     * 删除视频
     *
     * @param videoId 视频ID
     */
    void deleteVideo (Long userId, Long videoId);

    /**
     * 修改视频信息
     *
     * @param dto UpdateVideoDTO
     */
    void updateVideo (UpdateVideoDTO dto);

    /**
     * 视频相关推荐
     *
     * @param getRecommendedVideoDTO 推荐视频的查询参数
     * @return List<Video> 推荐视频列表
     */
    List<Video> getRecommendedVideos (GetRecommendedVideoDTO getRecommendedVideoDTO);
}
