package cn.edu.scnu.danmakutv.video.service;

import cn.edu.scnu.danmakutv.domain.Video;
import cn.edu.scnu.danmakutv.vo.VideoVO;
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
}
