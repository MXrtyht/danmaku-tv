package cn.edu.scnu.danmakutv.video.service;

import cn.edu.scnu.danmakutv.domain.video.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface TagService extends IService<Tag> {
    Long addTag (String tagName);

    Long findTagIdByName (String tagName);

    List<Long> findTagIdsByNames (List<String> tagNames);

    List<String> getTagsByIds (List<Long> tagIds);
}
