package cn.edu.scnu.danmakutv.video.service.impl;

import cn.edu.scnu.danmakutv.common.exception.DanmakuException;
import cn.edu.scnu.danmakutv.domain.video.Video;
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
import java.util.stream.Collectors;

@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {
    @Resource
    private TagService tagService;

    @Resource
    private VideoTagRelationService videoTagRelationService;

    @Resource
    private AreaService areaService;

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
    public void uploadVideo (UserUploadVideoDTO userUploadVideoDTO) {
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
     * 根据视频ID数组获取视频列表
     *
     * @param videoIds 视频ID数组
     * @return 视频列表
     */
    @Override
    public List<Video> getVideosByIds (@Size(min = 1) Long[] videoIds) {
        if (videoIds == null || videoIds.length == 0) {
            throw new IllegalArgumentException("视频ID列表不能为空");
        }
        List<Video> videos = baseMapper.selectByIds(List.of(videoIds));
        return videos;
    }
}
