package cn.edu.scnu.danmakutv.video.mapper;

import cn.edu.scnu.danmakutv.domain.Video;
import cn.edu.scnu.danmakutv.vo.VideoVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface VideoMapper extends BaseMapper<Video> {
    IPage<VideoVO> selectVideo (IPage<?> page, @Param("ew") QueryWrapper<?> wrapper);
}
