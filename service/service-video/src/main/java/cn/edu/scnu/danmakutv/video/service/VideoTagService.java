package cn.edu.scnu.danmakutv.video.service;

import cn.edu.scnu.danmakutv.domain.video.VideoTag;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoTagService extends IService<VideoTag> {

    /**
     * 根据视频id获取视频标签
     * @param videoId
     * @return
     */
    List<String> selectTagsByVideoId (@Param("videoId") Long videoId);
}
