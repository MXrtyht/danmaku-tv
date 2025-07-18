package cn.edu.scnu.danmakutv.video.service;

import cn.edu.scnu.danmakutv.domain.video.Video;
import cn.edu.scnu.danmakutv.dto.video.UserUploadVideoDTO;
import cn.edu.scnu.danmakutv.dto.video.VideoDetailDTO;
import cn.edu.scnu.danmakutv.vo.video.VideoVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

public interface VideoService extends IService<Video> {
    /**
     * 分页获取视频列表
     *
     * @param page 当前页码
     * @param size 每页显示条数
     * @return 分页视频信息
     */
    IPage<VideoVO> selectVideo (int page, int size, QueryWrapper<?> wrapper);

    /**
     * 上传视频
     *
     * @param userUploadVideoDTO 视频上传的数据传输对象
     */
    void uploadVideo (UserUploadVideoDTO userUploadVideoDTO);

    /**
     * 删除视频
     * @param videoId 视频ID
     */
    void deleteVideo(Long videoId);

    /**
     * 根据视频id获取视频信息
     * @param id  视频ID
     * @return  视频详情信息
     */
    VideoDetailDTO getVideoById(Long id);
}
