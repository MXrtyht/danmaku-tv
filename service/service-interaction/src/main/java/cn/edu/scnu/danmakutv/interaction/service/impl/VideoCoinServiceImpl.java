package cn.edu.scnu.danmakutv.interaction.service.impl;

import cn.edu.scnu.danmakutv.common.exception.DanmakuException;
import cn.edu.scnu.danmakutv.common.response.CommonResponse;
import cn.edu.scnu.danmakutv.domain.interaction.VideoCoinRecord;
import cn.edu.scnu.danmakutv.dto.interaction.AddVideoCoinDTO;
import cn.edu.scnu.danmakutv.dto.user.UpdateUserCoinDTO;
import cn.edu.scnu.danmakutv.interaction.controller.client.UserServiceClient;
import cn.edu.scnu.danmakutv.interaction.controller.client.VideoServiceClient;
import cn.edu.scnu.danmakutv.interaction.mapper.VideoCoinMapper;
import cn.edu.scnu.danmakutv.interaction.service.VideoCoinService;
import cn.edu.scnu.danmakutv.vo.user.UserProfilesVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VideoCoinServiceImpl extends ServiceImpl<VideoCoinMapper, VideoCoinRecord> implements VideoCoinService {
    @Resource
    private UserServiceClient userServiceClient;

    @Resource
    private VideoServiceClient videoService;

    /**
     * 给视频投币
     *
     * @param addVideoCoinDTO 包含视频ID、用户ID和投币数量的DTO
     */
    @Override
    public void coinVideoById (AddVideoCoinDTO addVideoCoinDTO) {
        // 投币数量在DTO类用 @Valid 注解校验了
        // 先检查视频是否存在
        CommonResponse<?> videoVOResponse = videoService.getVideoById(addVideoCoinDTO.getVideoId());
        if (videoVOResponse.getCode() != 200) {
            throw new DanmakuException("视频不存在", 400);
        }

        // 检查用户硬币是否足够
        CommonResponse<UserProfilesVO> userProfilesVOResponse = userServiceClient.getUserProfilesById(addVideoCoinDTO.getUserId());
        if (userProfilesVOResponse.getData().getCoin() <= addVideoCoinDTO.getCoin()) {
            throw new DanmakuException("硬币不足", 400);
        }

        // 检查用户是否已经投过币
        if (checkVideoCoinRecord(addVideoCoinDTO.getUserId(), addVideoCoinDTO.getVideoId())) {
            throw new DanmakuException("用户已投过币", 400);
        }

        // 投币
        // 修改用户的硬币数量
        UpdateUserCoinDTO updateUserCoinDTO = new UpdateUserCoinDTO();
        updateUserCoinDTO.setUserId(addVideoCoinDTO.getUserId());
        updateUserCoinDTO.setCoin(addVideoCoinDTO.getCoin());
        updateUserCoinDTO.setIsAdd(false);

        CommonResponse<Boolean> updateCoinResponse = userServiceClient.updateUserCoin(
                updateUserCoinDTO
        );


        // 添加投币记录
        VideoCoinRecord videoCoinRecord = new VideoCoinRecord();
        BeanUtils.copyProperties(addVideoCoinDTO, videoCoinRecord);
        videoCoinRecord.setCreateAt(LocalDateTime.now());
        this.baseMapper.insert(videoCoinRecord);
    }

    @Override
    public Boolean checkVideoCoinRecord (Long userId, Long videoId) {
        VideoCoinRecord videoCoinRecord = baseMapper.selectOne(
                new QueryWrapper<VideoCoinRecord>()
                        .eq("user_id", userId)
                        .eq("video_id", videoId)
        );
        return videoCoinRecord != null;
    }

    @Override
    public Long getVideoCoinCount (Long videoId) {
        Long countOne = baseMapper.selectCount(
                new QueryWrapper<VideoCoinRecord>()
                        .eq("video_id", videoId)
        );

        Long countTwo = baseMapper.selectCount(
                new QueryWrapper<VideoCoinRecord>()
                        .eq("video_id", videoId)
                        .eq("coin", 2)
        );

        return countOne + countTwo;
    }
}
