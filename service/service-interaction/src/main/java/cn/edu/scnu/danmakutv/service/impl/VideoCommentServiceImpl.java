package cn.edu.scnu.danmakutv.service.impl;

import cn.edu.scnu.danmakutv.common.exception.DanmakuException;
import cn.edu.scnu.danmakutv.domain.interaction.VideoComment;
import cn.edu.scnu.danmakutv.domain.user.UserProfiles;
import cn.edu.scnu.danmakutv.domain.video.Video;
import cn.edu.scnu.danmakutv.mapper.VideoCommentMapper;
import cn.edu.scnu.danmakutv.service.VideoCommentService;
import cn.edu.scnu.danmakutv.user.mapper.UserProfilesMapper;
import cn.edu.scnu.danmakutv.video.mapper.VideoMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class VideoCommentServiceImpl extends ServiceImpl<VideoCommentMapper, VideoComment> implements VideoCommentService {
    private VideoMapper videoMapper;
    private VideoCommentMapper videoCommentMapper;
    private UserProfilesMapper userProfilesMapper;

    /**
     * 添加视频评论
     * @param videoComment 视频评论
     * @param userId 用户id
     */
    @Override
    public void addVideoComment(VideoComment videoComment, Long userId) {
        Long videoId = videoComment.getVideoId();
        if(videoId==null){
            throw new DanmakuException("参数异常！", 3003);
        }
        Video video = videoMapper.selectById(videoId);
        if (video==null){
            throw new DanmakuException("非法视频！", 3004);
        }
        videoComment.setUserId(userId);
        videoComment.setCreateTime(new Date());
        videoCommentMapper.insert(videoComment);
    }


    @Override
    public Page<VideoComment> pageListVideoComments(Integer size, Integer pages, Long videoId) {
// 1. 校验视频是否存在
        Video video = videoMapper.selectById(videoId);
        if (video == null) {
            throw new DanmakuException("非法视频！", 3004);
        }

        // 2. 创建MyBatis-Plus分页对象
        Page<VideoComment> page = new Page<>(pages, size);

        // 3. 查询一级评论（自动分页）
        page = videoCommentMapper.selectPage(page,
                new QueryWrapper<VideoComment>()
                        .eq("videoId", videoId)
                        .isNull("rootId")
                        .orderByDesc("id")
        );

        // 4. 如果有一级评论，查询二级评论和用户信息
        if (!page.getRecords().isEmpty()) {
            // 批量查询二级评论
            List<Long> parentIdList = page.getRecords().stream()
                    .map(VideoComment::getId)
                    .collect(Collectors.toList());

            List<VideoComment> childCommentList = videoCommentMapper.selectList(
                    new QueryWrapper<VideoComment>()
                            .in("rootId", parentIdList)
                            .orderByAsc("id")
            );

            // 批量查询用户信息
            Set<Long> userIdList = Stream.concat(
                    page.getRecords().stream().map(VideoComment::getUserId),
                    childCommentList.stream().map(VideoComment::getReplyUserId)
            ).collect(Collectors.toSet());

            Map<Long, UserProfiles> userInfoMap = userProfilesMapper.selectList(
                            new QueryWrapper<UserProfiles>()
                                    .in("user_id", userIdList)
                    ).stream()
                    .collect(Collectors.toMap(UserProfiles::getUserId, Function.identity()));

            // 组装评论树结构
            page.getRecords().forEach(comment -> {
                comment.setUserInfo(userInfoMap.get(comment.getUserId()));

                comment.setChildList(
                        childCommentList.stream()
                                .filter(child -> child.getRootId().equals(comment.getId()))
                                .peek(child -> {
                                    child.setUserInfo(userInfoMap.get(child.getUserId()));
                                    child.setReplyUserInfo(userInfoMap.get(child.getReplyUserId()));
                                })
                                .collect(Collectors.toList())
                );
            });
        }
        return page;
    }
}
