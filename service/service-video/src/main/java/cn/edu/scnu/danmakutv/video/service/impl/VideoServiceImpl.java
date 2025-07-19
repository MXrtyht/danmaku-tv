package cn.edu.scnu.danmakutv.video.service.impl;

import cn.edu.scnu.common.utils.IpUtil;
import cn.edu.scnu.danmakutv.domain.video.Video;
import cn.edu.scnu.danmakutv.domain.video.VideoTagRelation;
import cn.edu.scnu.danmakutv.domain.video.VideoView;
import cn.edu.scnu.danmakutv.dto.video.RecommendedVideoDTO;
import cn.edu.scnu.danmakutv.dto.video.UpdateVideoDTO;
import cn.edu.scnu.danmakutv.dto.video.UserUploadVideoDTO;
import cn.edu.scnu.danmakutv.dto.video.VideoDetailDTO;
import cn.edu.scnu.danmakutv.video.mapper.VideoMapper;
import cn.edu.scnu.danmakutv.video.mapper.VideoTagMapper;
import cn.edu.scnu.danmakutv.video.mapper.VideoTagRelationMapper;
import cn.edu.scnu.danmakutv.video.service.VideoService;
import cn.edu.scnu.danmakutv.vo.video.VideoVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import eu.bitwalker.useragentutils.UserAgent;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {
    private final VideoMapper videoMapper;
    private final VideoTagMapper videoTagMapper;
    private final VideoTagRelationMapper videoTagRelationMapper;

    public VideoServiceImpl(VideoMapper videoMapper, VideoTagMapper videoTagMapper, VideoTagRelationMapper videoTagRelationMapper) {
        this.videoMapper = videoMapper;
        this.videoTagMapper = videoTagMapper;
        this.videoTagRelationMapper = videoTagRelationMapper;
    }

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
        video.setCreatedAt(LocalDateTime.now());
        video.setUpdatedAt(LocalDateTime.now());

        // TODO 插入视频-tag关联表
        // TODO 返回视频ID
        for (Long tag : userUploadVideoDTO.getTags()) {
            System.out.println("Tag ID: " + tag);
        }
        baseMapper.insert(video);
        Long videoId = video.getId();
    }

    /**
     * 删除视频
     * @param videoId 视频ID
     */
    @Transactional
    @Override
    public void deleteVideo(Long videoId) {
        // 1. 删除视频标签关联关系
        videoTagRelationMapper.deleteByVideoId(videoId);

        // 2. 删除视频记录
        videoMapper.deleteById(videoId);
    }

    /**
     * 根据视频id获取视频信息
     * @param id  视频ID
     * @return VideoDetailDTO
     */
    @Override
    public VideoDetailDTO getVideoById(Long id) {
        // 1. 查询视频基本信息
        Video video = videoMapper.selectById(id);
        if (video == null) {
            throw new RuntimeException("视频不存在");
        }

        // 2. 查询关联标签名称列表
        List<String> tags = videoTagMapper.selectTagsByVideoId(id);

        // 3. 组装DTO
        VideoDetailDTO dto = new VideoDetailDTO();
        dto.setId(video.getId());
        dto.setUserId(video.getUserId());
        dto.setVideoUrl(video.getVideoUrl());
        dto.setCoverUrl(video.getCoverUrl());
        dto.setTitle(video.getTitle());
        dto.setType(video.isType());
        dto.setDuration(video.getDuration());
        dto.setArea(video.getArea());
        dto.setTags(tags);
        dto.setCreatedAt(video.getCreatedAt());

        return dto;
    }

    /**
     * 修改视频信息
     * @param id 要修改视频的ID
     * @param dto UpdateVideoDTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateVideo(Long id, UpdateVideoDTO dto) {
        // 1. 检查视频是否存在
        Video video = videoMapper.selectById(id);
        if (video == null) {
            throw new RuntimeException("视频不存在");
        }

        // 2. 更新视频基本信息
        video.setTitle(dto.getTitle());
        video.setCoverUrl(dto.getCoverUrl());
        video.setType(dto.getType());
        video.setArea(dto.getArea());
        video.setUpdatedAt(LocalDateTime.now());
        videoMapper.updateById(video);

        // 3. 更新标签关联（先删除旧关联，再添加新关联）
        videoTagRelationMapper.deleteByVideoId(id); // 先删除旧关联
        if (dto.getTags() != null && !dto.getTags().isEmpty()) {
            dto.getTags().forEach(tagId -> {
                VideoTagRelation relation = new VideoTagRelation();
                relation.setVideoId(id);
                relation.setTagId(tagId);
                videoTagRelationMapper.insert(relation);
            });
        }
    }

    /**
     * 视频相关推荐
     * @param tagIds 视频标签列表
     * @param limit 返回的推荐视频数量限制
     * @return List<RecommendedVideoDTO>
     */
    @Override
    public List<RecommendedVideoDTO> getRecommendedVideos(List<Long> tagIds, int limit) {
        if (tagIds == null || tagIds.isEmpty()) {
            return Collections.emptyList();
        }

        // 1. 查询包含相同标签的视频ID列表（按匹配标签数排序）
        List<Long> videoIds = videoTagRelationMapper.findVideoIdsByTagIds(tagIds, limit);

        // 2. 查询视频详细信息
        if (videoIds.isEmpty()) {
            return Collections.emptyList();
        }
        List<Video> videos = videoMapper.selectBatchIds(videoIds);

        // 3. 组装DTO
        return videos.stream().map(video -> {
            RecommendedVideoDTO dto = new RecommendedVideoDTO();
            dto.setId(video.getId());
            dto.setTitle(video.getTitle());
            dto.setVideoUrl(video.getVideoUrl());
            dto.setCoverUrl(video.getCoverUrl());
            dto.setType(video.isType());
            dto.setDuration(video.getDuration());
            dto.setArea(video.getArea());
            dto.setCreatedAt(video.getCreatedAt());

            // 查询视频标签
            List<String> tags = videoTagMapper.selectTagsByVideoId(video.getId());
            dto.setTags(tags);

            return dto;
        }).collect(Collectors.toList());
    }

    /**
     * 添加视频观看记录
     * @param videoView 视频观看记录
     * @param request
     */
    @Override
    public void addVideoView(VideoView videoView, HttpServletRequest request) {
        Long videoId = videoView.getVideoId();
        Long userId = videoView.getUserId();
        //生成clientId
        String agent = request.getHeader("User-Agent");
        UserAgent userAgent= UserAgent.parseUserAgentString(agent);
        String clientId = String.valueOf(userAgent.getId());
        String ip= IpUtil.getIpAddr(request);
        Map<String,Object> params = new HashMap<>();
        if(userId!=null){
            params.put("userId",userId);
        }else {
            params.put("ip",ip);
            params.put("clientId",clientId);
        }
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        params.put("today",sdf.format(now));
        params.put("videoId",videoId);
        //添加观看记录
        VideoView dbVideoView = videoMapper.getVideoView(params);
        if(dbVideoView==null){
            videoView.setIp(ip);
            videoView.setClientId(clientId);
            videoView.setCreatedAt(new Date());
            videoMapper.addVideoView(videoView);
        }
    }

    /**
     * 获取视频播放量
     * @param videoId
     * @return
     */
    @Override
    public Integer getVideoViewCounts(Long videoId) {
        return videoMapper.getVideoViewCounts(videoId);
    }
}
