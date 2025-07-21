package cn.edu.scnu.danmakutv.video.service.impl;

import cn.edu.scnu.danmakutv.common.exception.DanmakuException;
import cn.edu.scnu.danmakutv.domain.video.Video;
import cn.edu.scnu.danmakutv.domain.video.VideoTagRelation;
import cn.edu.scnu.danmakutv.dto.video.GetRecommendedVideoDTO;
import cn.edu.scnu.danmakutv.dto.video.UpdateVideoDTO;
import cn.edu.scnu.danmakutv.dto.video.UserUploadVideoDTO;
import cn.edu.scnu.danmakutv.video.mapper.VideoMapper;
import cn.edu.scnu.danmakutv.video.service.AreaService;
import cn.edu.scnu.danmakutv.video.service.TagService;
import cn.edu.scnu.danmakutv.video.service.VideoService;
import cn.edu.scnu.danmakutv.video.service.VideoTagRelationService;
import cn.edu.scnu.danmakutv.vo.video.VideoVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.Size;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {
    @Resource
    private TagService tagService;

    @Resource
    private VideoTagRelationService videoTagRelationService;

    @Resource
    private AreaService areaService;

    // 以上可能要修改

    /**
     * 分页查询视频列表
     *
     * @param page    分页页码
     * @param size    分页大小
     * @param wrapper 查询条件
     * @return 包含视频列表的分页结果
     */
    @Override
    public IPage<VideoVO> selectVideo (int page, int size, QueryWrapper<Video> wrapper) {
        // 先查分页的Video
        IPage<Video> pageRequest = new Page<>(page, size);
        IPage<Video> videoPage = baseMapper.selectPage(pageRequest, wrapper);

        // 2. 转换为 VideoVO 分页结果
        IPage<VideoVO> voPage = new Page<>(page, size, videoPage.getTotal());

        // 3. 遍历每个 Video，组装成 VideoVO
        List<VideoVO> videoVOList = videoPage.getRecords().stream()
                                             .map(video -> {
                                                 VideoVO vo = new VideoVO();
                                                 BeanUtils.copyProperties(video, vo);

                                                 // 4. 调用其他 service 获取标签列表
                                                 // 先获取该视频所有的标签的id
                                                 List<Long> tagIds = videoTagRelationService.getIdsByVideoId(video.getId());
                                                 // 再根据标签id获取标签名称
                                                 List<String> tags = tagService.getTagsByIds(tagIds);
                                                 vo.setTags(tags);

                                                 return vo;
                                             })
                                             .collect(Collectors.toList());

        voPage.setRecords(videoVOList);
        return voPage;
    }


    /**
     * 上传视频
     *
     * @param userUploadVideoDTO 包含视频信息
     */
    @Transactional
    @Override
    public Long uploadVideo (UserUploadVideoDTO userUploadVideoDTO) {
        Video video = new Video();

        // 复制属性
        BeanUtils.copyProperties(userUploadVideoDTO, video);
        // 设置视频的上传时间和更新时间
        video.setCreateAt(LocalDateTime.now());
        video.setUpdateAt(LocalDateTime.now());

        // 验证视频分区
        Integer areaId = userUploadVideoDTO.getArea();
        if (areaService.findAreaNameById(areaId) == null) {
            throw new DanmakuException("视频分区不存在", 400);
        }

        // 设置视频标签
        // 拿到传过来的tag名称表
        List<String> tagNames = userUploadVideoDTO.getTags();
        // 根据标签名称获取标签ID列表
        List<Long> tagIds = tagService.findTagIdsByNames(tagNames);

        // videoId字段是自增 要先插入数据才能获得videoId
        // 插入视频信息
        this.baseMapper.insert(video);
        System.out.println("-----------" + video.getId());

        // TODO: 设置视频时长

        // 插入视频-tag关联表
        videoTagRelationService.addVideoTagRelation(video.getId(), tagIds);

        return video.getId();
    }

    /**
     * 根据视频ID获取视频信息(包括标签)
     *
     * @param videoId 视频ID
     * @return 视频信息对象
     */
    @Override
    public VideoVO getVideoById (Long videoId) {
        // 先获取该视频, 并检查其是否存在
        Video video = baseMapper.selectById(videoId);
        if (video == null) {
            throw new RuntimeException("视频不存在");
        }

        // 构建 VideoVO 对象
        VideoVO videoVO = new VideoVO();
        BeanUtils.copyProperties(video, videoVO);
        // 获取当前视频的标签名字列表
        List<String> tags = tagService.getTagsByIds(
                videoTagRelationService.getIdsByVideoId(videoId)
        );
        // 设置标签
        videoVO.setTags(tags);

        return videoVO;
    }

    /**
     * 根据视频ID数组批量获取视频
     *
     * @param videoIds 视频ID数组
     * @return 视频列表
     */
    @Override
    public List<Video> getVideosByIds (@Size(min = 1) List<Long> videoIds) {
        if (videoIds == null || videoIds.isEmpty()) {
            throw new IllegalArgumentException("视频ID列表不能为空");
        }
        List<Video> videos = baseMapper.selectByIds(videoIds);
        return videos;
    }

    /**
     * 删除视频
     *
     * @param videoId 视频ID
     */
    @Transactional
    @Override
    public void deleteVideo (Long userId, Long videoId) {
        Video video = baseMapper.selectById(videoId);
        if(video == null || !video.getUserId().equals(userId) ) {
            throw new RuntimeException("无权限删除该视频");
        }
        // 1. 删除视频标签关联关系
        videoTagRelationService.deleteByVideoId(videoId);

        // 2. 删除视频记录
        baseMapper.deleteById(videoId);
    }

    /**
     * 修改视频信息
     *
     * @param dto UpdateVideoDTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateVideo (UpdateVideoDTO dto) {
        // 1. 检查视频是否存在
        Video video = baseMapper.selectById(dto.getId());
        if (video == null) {
            throw new RuntimeException("视频不存在");
        }

        // 2. 更新视频基本信息
        video.setTitle(dto.getTitle());
        video.setCoverUrl(dto.getCoverUrl());
        video.setType(dto.getType());
        video.setArea(dto.getArea());
        video.setUpdateAt(LocalDateTime.now());
        baseMapper.updateById(video);

        // 3. 更新标签关联（先删除旧关联，再添加新关联）
        videoTagRelationService.deleteByVideoId(dto.getId()); // 先删除旧关联
        if (dto.getTags() != null && !dto.getTags().isEmpty()) {
            dto.getTags().forEach(tagId -> {
                VideoTagRelation relation = new VideoTagRelation();
                relation.setVideoId(dto.getId());
                relation.setTagId(tagId);
                videoTagRelationService.insert(relation);
            });
        }
    }

    /**
     * 视频相关推荐
     *
     * @param getRecommendedVideoDTO 推荐视频的查询参数, 包含视频ID和限制数量
     * @return List<Video> 推荐视频列表
     */
    @Override
    public List<Video> getRecommendedVideos (GetRecommendedVideoDTO getRecommendedVideoDTO) {
        Long videoId = getRecommendedVideoDTO.getVideoId();
        Long limit = getRecommendedVideoDTO.getLimit();
        List<Long> tagIds = videoTagRelationService.getIdsByVideoId(videoId);

        // 1. 查询包含相同标签的视频ID列表（按匹配标签数排序）
        List<Long> videoIds = videoTagRelationService.findVideoIdsByTagIds(tagIds, limit);

        // 2. 根据视频ID列表批量获取视频信息
        List<Video> recommendedVideos = this.getVideosByIds(videoIds);

          // 3. 过滤掉当前视频本身
        return recommendedVideos.stream()
                                .filter(video -> !Objects.equals(video.getId(), videoId))
                                .collect(Collectors.toList());
    }
}
