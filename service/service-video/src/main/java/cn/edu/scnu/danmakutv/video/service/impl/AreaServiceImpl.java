package cn.edu.scnu.danmakutv.video.service.impl;

import cn.edu.scnu.danmakutv.domain.video.Area;
import cn.edu.scnu.danmakutv.video.mapper.AreaMapper;
import cn.edu.scnu.danmakutv.video.service.AreaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper,Area> implements AreaService {
}
