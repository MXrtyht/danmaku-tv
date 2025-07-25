package cn.edu.scnu.danmakutv.video.service.impl;

import cn.edu.scnu.common.utils.IpUtil;
import cn.edu.scnu.danmakutv.domain.video.VideoView;
import cn.edu.scnu.danmakutv.video.mapper.VideoViewMapper;
import cn.edu.scnu.danmakutv.video.service.VideoViewService;
import cn.edu.scnu.danmakutv.vo.video.GetVideoPlayCountVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import eu.bitwalker.useragentutils.UserAgent;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class VideoViewServiceImpl extends ServiceImpl<VideoViewMapper, VideoView> implements VideoViewService {

    /**
     * 添加视频观看记录
     *
     * @param videoView 视频观看记录
     * @param request   HttpServletRequest
     */
    @Override
    public void addVideoView (VideoView videoView, HttpServletRequest request) {
        // 先获取当前视频id和用户id
        Long videoId = videoView.getVideoId();
        Long userId = videoView.getUserId();

        // 获取IP地址
        String ip = IpUtil.getIpAddr(request);
        // 获取客户端信息
        String agent = request.getHeader("User-Agent");
        UserAgent userAgent = UserAgent.parseUserAgentString(agent);
        // 生成clientId
        String clientId = String.valueOf(userAgent.getId());

        // 构造查询条件
        QueryWrapper<VideoView> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("video_id", videoId)
                    .ge("create_at", LocalDateTime.now().withHour(0).withMinute(0).withSecond(0)) // 今天零点
                    .lt("create_at", LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0)); // 明天零点

        if (userId != null) {
            queryWrapper.eq("user_id", userId);
        } else {
            queryWrapper.eq("ip", ip)
                        .eq("client_id", clientId);
        }

        // 查询是否存在今天的观看记录, 若没有则插入新记录
        VideoView existView = baseMapper.selectOne(queryWrapper);
        if (existView == null) {
            videoView.setIp(ip);
            videoView.setClientId(clientId);
            videoView.setCreateAt(LocalDateTime.now());
            baseMapper.insert(videoView);
        }
    }

    /**
     * 获取视频播放量
     *
     * @param videoId
     * @return
     */
    @Override
    public Long getVideoViewCounts (Long videoId) {
        return baseMapper.selectCount(
                new QueryWrapper<VideoView>().eq("video_id", videoId)
        );
    }
    
    /**
     * 批量获取视频播放量
     *
     * @param videoId列表
     * @return
     */
    @Override
    public List<GetVideoPlayCountVO> getVideoViewCountsBatch(List<Long> videoIds) {
        // 1. 使用 selectMaps 获取结果
        List<Map<String, Object>> result = baseMapper.selectMaps(
            new QueryWrapper<VideoView>()
                .select("video_id as videoId", "COUNT(*) as viewCount")
                .in("video_id", videoIds)
                .groupBy("video_id")
        );

        // 2. 显式指定 Stream 类型
        return result.stream()
            .<GetVideoPlayCountVO>map(map -> {  // 显式指定泛型类型
                Long videoId = ((Number) map.get("videoId")).longValue();
                Long viewCount = ((Number) map.get("viewCount")).longValue();
                return new GetVideoPlayCountVO(videoId, viewCount);
            })
            .collect(Collectors.toList());
    }


    /**
     * 获取用户观看历史
     *
     * @param userId 用户ID
     * @return 视频ID列表
     */
    @Override
    public List<Long> getVideoViewsHistory (Long userId) {
        if (userId == null) {
            return null;
        }

        // 查询用户的观看记录
        List<VideoView> videoViews = baseMapper.selectList(
                new QueryWrapper<VideoView>().eq("user_id", userId)
        );

        // 提取视频ID列表
        return videoViews.stream()
                         .map(VideoView::getVideoId)
                         .toList();
    }
}
