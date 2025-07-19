package cn.edu.scnu.danmakutv.service.impl;

import cn.edu.scnu.danmakutv.common.exception.DanmakuException;
import cn.edu.scnu.danmakutv.domain.interaction.CollectionGroup;
import cn.edu.scnu.danmakutv.domain.interaction.VideoCollection;
import cn.edu.scnu.danmakutv.domain.user.FollowGroup;
import cn.edu.scnu.danmakutv.domain.user.UserFollow;
import cn.edu.scnu.danmakutv.domain.video.Video;
import cn.edu.scnu.danmakutv.dto.interaction.AddVideoCollectionDTO;
import cn.edu.scnu.danmakutv.mapper.VideoCollectionMapper;
import cn.edu.scnu.danmakutv.service.CollectionGroupService;
import cn.edu.scnu.danmakutv.service.VideoCollectionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VideoCollectionServiceImpl extends ServiceImpl<VideoCollectionMapper, VideoCollection> implements VideoCollectionService {
    @Resource
    private CollectionGroupService collectionGroupService;

    @Override
    public void addVideoCollection (AddVideoCollectionDTO addVideoCollectionDTO) {
        Long groupId = addVideoCollectionDTO.getGroupId();

        // 如果groupId为null或非法，则设置为默认值1
        if (groupId == null || groupId <= 0) {
            addVideoCollectionDTO.setGroupId(1L);
        } else {
            // 检查groupId是否存在
            CollectionGroup collectionGroup = collectionGroupService.getById(groupId);
            if (collectionGroup == null) {
                throw new DanmakuException("关注分组不存在", 400);
            }
        }

        Long videoId = addVideoCollectionDTO.getVideoId();
        // TODO 检查被收藏的视频是否存在

        // 先删除再添加
        this.baseMapper.delete(
                new QueryWrapper<>(VideoCollection.class)
                        .eq("user_id", addVideoCollectionDTO.getUserId())
                        .eq("video_id", videoId)
        );
        addVideoCollectionDTO.setCreateAt(LocalDateTime.now());

        VideoCollection videoCollection = new VideoCollection();
        BeanUtils.copyProperties(addVideoCollectionDTO, videoCollection);

        this.baseMapper.insert(videoCollection);
    }
}
