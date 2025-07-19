package cn.edu.scnu.danmakutv.video.service.impl;

import cn.edu.scnu.danmakutv.domain.video.Video;
import cn.edu.scnu.danmakutv.dto.video.UserUploadVideoDTO;
import cn.edu.scnu.danmakutv.video.mapper.VideoMapper;
import cn.edu.scnu.danmakutv.video.service.VideoService;
import cn.edu.scnu.danmakutv.vo.video.VideoVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.validation.constraints.Size;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {
    /**
     * 查询视频列表
     *
     * @param page    分页页码
     * @param size    分页大小
     * @param wrapper 查询条件
     * @return 包含视频列表的分页结果
     */
    @Override
    public IPage<VideoVO> selectVideo (int page, int size, QueryWrapper<?> wrapper) {
        Page<VideoVO> pageRequest = new Page<>(page, size);
        return baseMapper.selectVideo(pageRequest, wrapper);
    }


    /**
     * 上传视频
     *
     * @param userUploadVideoDTO 包含视频信息
     */
    @Transactional
    @Override
    public void uploadVideo (UserUploadVideoDTO userUploadVideoDTO) {
        Video video = new Video();

        BeanUtils.copyProperties(userUploadVideoDTO, video);
        video.setCreateAt(LocalDateTime.now());
        video.setUpdateAt(LocalDateTime.now());

        // TODO 插入视频-tag关联表
        // TODO 返回视频ID
        for (Long tag : userUploadVideoDTO.getTags()) {
            System.out.println("Tag ID: " + tag);
        }
        baseMapper.insert(video);
        Long videoId = video.getId();
    }

    /**
     * 根据视频ID获取视频信息
     * @param videoId 视频ID
     * @return 视频信息对象
     */
    @Override
    public VideoVO getVideoById (Long videoId) {
        Video video        = baseMapper.selectById(videoId);
        if (video == null) {
            throw new RuntimeException("视频不存在");
        }
        VideoVO videoVO = new VideoVO();
        BeanUtils.copyProperties(video, videoVO);
        return videoVO;
    }

    @Override
    public List<Video> getVideosByIds (@Size(min = 1) Long[] videoIds) {
        if (videoIds == null || videoIds.length == 0) {
            throw new IllegalArgumentException("视频ID列表不能为空");
        }
        List<Video> videos = baseMapper.selectByIds(List.of(videoIds));
        return videos;
    }
}
