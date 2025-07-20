package cn.edu.scnu.danmakutv.service.impl;

import cn.edu.scnu.danmakutv.common.exception.DanmakuException;
import cn.edu.scnu.danmakutv.domain.interaction.CollectionGroup;
import cn.edu.scnu.danmakutv.domain.interaction.VideoCollection;
import cn.edu.scnu.danmakutv.dto.interaction.AddVideoCollectionDTO;
import cn.edu.scnu.danmakutv.mapper.VideoCollectionMapper;
import cn.edu.scnu.danmakutv.service.CollectionGroupService;
import cn.edu.scnu.danmakutv.service.VideoCollectionService;
import cn.edu.scnu.danmakutv.vo.interaction.CollectedVideosWithGroupVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Override
    public void deleteVideoCollection (Long userId, Long videoId) {
        if (videoId == null) {
            throw new DanmakuException("视频ID不能为空", 400);
        }

        int rowsAffected = this.baseMapper.delete(
                new QueryWrapper<VideoCollection>()
                        .eq("user_id", userId)
                        .eq("video_id", videoId)
        );
        if (rowsAffected == 0) {
            throw new DanmakuException("取消收藏失败，可能视频未被收藏", 400);
        }
    }

    /**
     * 获取用户收藏分组和对应的视频id列表
     *
     * @param userId 用户id
     * @return 包含用户收藏分组和对应的视频id列表
     */
    @Override
    public List<CollectedVideosWithGroupVO> getUserCollectedVideos (Long userId) {
        List<CollectedVideosWithGroupVO> collectedVideos = new ArrayList<>();

        // 先获取所有收藏的视频
        List<VideoCollection> videoCollections = this.baseMapper.selectList(
                new QueryWrapper<VideoCollection>()
                        .eq("user_id", userId)
        );

        if (videoCollections.isEmpty()) {
            return collectedVideos; // 如果没有收藏的视频，直接返回空列表
        }

        // 获取所有收藏的视频的id
        Set<Long> videoIds = videoCollections.stream()
                                             .map(VideoCollection::getVideoId)
                                             .collect(Collectors.toSet());

        // 获取所有的关注分组
        List<CollectionGroup> collectionGroups = collectionGroupService.getCollectionGroupsByUserId(userId);

        // 对关注分组进行分类
        for (CollectionGroup collectionGroup : collectionGroups) {
            CollectedVideosWithGroupVO collectedVideosWithGroupVO = new CollectedVideosWithGroupVO();

            BeanUtils.copyProperties(collectionGroup, collectedVideosWithGroupVO);
            collectedVideosWithGroupVO.setVideoIdList(new ArrayList<>());

            // 获取当前分组下 收藏的视频
            // 先遍历收藏的视频
            for (VideoCollection videoCollection : videoCollections) {
                // 如果收藏的视频在当前分组下
                if (videoCollection.getGroupId().equals(collectionGroup.getId())) {
                    // 将视频id添加到当前分组的视频列表中
                    collectedVideosWithGroupVO.getVideoIdList().add(videoCollection.getVideoId());
                }
            }

            // 添加到结果列表
            collectedVideos.add(collectedVideosWithGroupVO);
        }

        return collectedVideos;
    }

    /**
     * 删除用户收藏分组, 及其下收藏的视频
     *
     * @param userId
     * @param groupId
     * @return
     */
    @Transactional
    @Override
    public void deleteCollectionGroup (Long userId, Long groupId) {
        // 先删除分组下收藏的视频
        this.baseMapper.delete(
                new QueryWrapper<VideoCollection>()
                        .eq("user_id", userId)
                        .eq("group_id", groupId)
        );
        // 再删除分组
        boolean isDeleted = collectionGroupService.removeById(groupId);
        if (!isDeleted) {
            throw new DanmakuException("删除收藏分组失败", 400);
        }
    }

    /**
     * 用户指定分组下的所有收藏
     *
     * @param userId 用户ID
     * @param groupId 分组ID
     * @return 用户指定分组下的所有收藏
     */
    @Override
    public List<VideoCollection> getUserCollectionsByGroupId (Long userId, Long groupId) {
        if (groupId == null || groupId <= 0) {
            throw new DanmakuException("分组ID不能为空或非法", 400);
        }

        // 检查分组是否存在
        CollectionGroup collectionGroup = collectionGroupService.getById(groupId);
        if ( collectionGroup == null || (collectionGroup.getUserId() != null && !collectionGroup.getUserId().equals(userId)) ) {
            throw new DanmakuException("分组不存在或不属于当前用户", 400);
        }

        // 查询该分组下的所有收藏
        return this.baseMapper.selectList(
                new QueryWrapper<VideoCollection>()
                        .eq("user_id", userId)
                        .eq("group_id", groupId)
        );
    }

    /**
     * 获取视频收藏数量
     * @param videoId 视频ID
     * @return 视频收藏数量
     */
    @Override
    public Long getVideoCollectCount (Long videoId) {
        if (videoId == null || videoId <= 0) {
            throw new DanmakuException("视频ID不能为空或非法", 400);
        }

        // 查询视频收藏数量
        return this.baseMapper.selectCount(
                new QueryWrapper<VideoCollection>()
                        .eq("video_id", videoId)
        );
    }

    @Override
    public Long isVideoCollected (Long userId, Long videoId) {
        if (userId == null || videoId == null) {
            throw new DanmakuException("用户ID或视频ID不能为空", 400);
        }

        // 查询是否已收藏
        VideoCollection videoCollection = this.baseMapper.selectOne(
                new QueryWrapper<VideoCollection>()
                        .eq("user_id", userId)
                        .eq("video_id", videoId)
        );

        // 如果已收藏，返回分组ID，否则返回null
        return videoCollection != null ? videoCollection.getGroupId() : null;
    }
}
