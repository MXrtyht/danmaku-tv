package cn.edu.scnu.danmakutv.video.service.impl;

import cn.edu.scnu.danmakutv.domain.Video;
import cn.edu.scnu.danmakutv.video.mapper.VideoMapper;
import cn.edu.scnu.danmakutv.video.service.VideoService;
import cn.edu.scnu.danmakutv.vo.VideoVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {
    @Override
    public IPage<VideoVO> selectVideo (int page, int size, QueryWrapper<?> wrapper) {
        Page<VideoVO> pageRequest = new Page<>(page, size);
        return baseMapper.selectVideo(pageRequest, wrapper);
    }
}
