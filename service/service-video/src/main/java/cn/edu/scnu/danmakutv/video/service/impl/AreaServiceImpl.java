package cn.edu.scnu.danmakutv.video.service.impl;

import cn.edu.scnu.danmakutv.domain.video.Area;
import cn.edu.scnu.danmakutv.video.mapper.AreaMapper;
import cn.edu.scnu.danmakutv.video.service.AreaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements AreaService {
    /**
     * 获取所有的分区
     *
     * @return 分区列表
     */
    @Override
    public List<Area> getAllAreas () {
        return this.lambdaQuery().list();
    }

    /**
     * 根据分区ID查找分区名称
     *
     * @param areaId 分区id
     * @return 分区名称，如果不存在则返回null
     */
    @Override
    public String findAreaNameById (Integer areaId) {
        if (areaId == null) {
            return null;
        }
        Area area = this.getById(areaId);
        if (area == null) {
            return null;
        }
        return area.getName();
    }
}
