package cn.edu.scnu.danmakutv.video.service.impl;

import cn.edu.scnu.danmakutv.domain.Video;
import cn.edu.scnu.danmakutv.dto.UserUploadVideoDTO;
import cn.edu.scnu.danmakutv.video.mapper.VideoMapper;
import cn.edu.scnu.danmakutv.video.service.VideoService;
import cn.edu.scnu.danmakutv.vo.VideoVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {
    @Override
    public IPage<VideoVO> selectVideo (int page, int size, QueryWrapper<?> wrapper) {
        Page<VideoVO> pageRequest = new Page<>(page, size);
        return baseMapper.selectVideo(pageRequest, wrapper);
    }

    @Transactional
    @Override
    public void uploadVideo (UserUploadVideoDTO userUploadVideoDTO) {
        Video video = new Video();

        BeanUtils.copyProperties(userUploadVideoDTO, video);
        video.setCreatedAt(LocalDateTime.now());
        video.setUpdatedAt(LocalDateTime.now());

        // TODO 插入视频-tag关联表
        for(Long tag : userUploadVideoDTO.getTags()){
            System.out.println("Tag ID: " + tag);
        }
        baseMapper.insert(video);
    }
}
