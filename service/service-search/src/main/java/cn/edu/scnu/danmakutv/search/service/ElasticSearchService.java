package cn.edu.scnu.danmakutv.search.service;

import cn.edu.scnu.danmakutv.domain.elasticsearch.UserProfilesES;
import cn.edu.scnu.danmakutv.domain.elasticsearch.VideoES;
import cn.edu.scnu.danmakutv.search.repository.UserProfilesRepository;
import cn.edu.scnu.danmakutv.search.repository.VideoRepository;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ElasticSearchService {
    @Resource
    private VideoRepository videoRepository;

    @Resource
    private UserProfilesRepository userProfilesRepository;

    // 添加视频到ES
    public void addVideoToEs (VideoES videoES) {
        this.videoRepository.save(videoES);
    }

    // 分页模糊查找视频（标题或描述）
    public Page<VideoES> searchVideosByKeywordWithPage (String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createAt"));
        return this.videoRepository.findByTitleLikeOrDescriptionLike(keyword, keyword, pageable);
    }

    // 添加用户信息到ES
    public void addUserProfilesToEs (UserProfilesES userProfilesES) {
        this.userProfilesRepository.save(userProfilesES);
    }

    // 分页模糊查找用户(用户名)
    public Page<UserProfilesES> searchUserProfilesByNicknameWithPage (String nickname, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createAt"));
        return this.userProfilesRepository.findUserProfilesESByNicknameLike(nickname, pageable);
    }
}
