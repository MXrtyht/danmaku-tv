package cn.edu.scnu.danmakutv.video.service;

import cn.edu.scnu.danmakutv.domain.video.Area;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface AreaService extends IService<Area> {
    List<Area> getAllAreas ();

    String findAreaNameById (Integer areaId);
}
