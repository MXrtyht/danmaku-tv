package cn.edu.scnu.danmakutv.search.repository;

import cn.edu.scnu.danmakutv.domain.elasticsearch.UserProfilesES;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UserProfilesRepository extends ElasticsearchRepository<UserProfilesES, Long> {
    Page<UserProfilesES> findByNicknameContaining(String nickname, Pageable pageable);
}
