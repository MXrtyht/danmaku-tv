package cn.edu.scnu.danmakutv.video.service.impl;

import cn.edu.scnu.danmakutv.domain.video.Tag;
import cn.edu.scnu.danmakutv.video.mapper.TagMapper;
import cn.edu.scnu.danmakutv.video.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
    /**
     * 添加新的标签
     *
     * @param tagName 标签名
     * @return 新标签的ID
     */
    @Override
    public Long addTag (String tagName) {
        Tag tag = new Tag();
        tag.setName(tagName);
        this.save(tag);
        return tag.getId();
    }

    /**
     * 根据标签名查找标签ID
     *
     * @param tagName 标签名
     * @return 标签ID，如果不存在则返回null
     */
    @Override
    public Long findTagIdByName (String tagName) {
        Tag tag = this.lambdaQuery().eq(Tag::getName, tagName).one();
        if (tag == null) {
            return null;
        }
        return tag.getId();
    }

    /**
     * 根据标签名称列表批量获取对应的标签ID, 如果某个标签不存在则创建新标签并返回其ID。
     *
     * @param tagNames 标签名称列表
     * @return 标签ID列表，如果输入为空则返回空列表
     */
    @Override
    public List<Long> findTagIdsByNames (List<String> tagNames) {
        if (tagNames == null || tagNames.isEmpty()) {
            return List.of();
        }

        // 1. 批量查询已存在的标签
        List<Tag> existingTags = this.lambdaQuery()
                                     .in(Tag::getName, tagNames)
                                     .list();

        Map<String, Long> existingTagMap = existingTags.stream()
                                                       .collect(Collectors.toMap(Tag::getName, Tag::getId));

        List<Long> result = new ArrayList<>();

        // 2. 处理每个标签名
        for (String tagName : tagNames) {
            if (existingTagMap.containsKey(tagName)) {
                // 已存在，直接添加ID
                result.add(existingTagMap.get(tagName));
            } else {
                // 不存在，创建新标签
                Long newId = addTag(tagName);
                result.add(newId);
            }
        }

        return result;
    }

    /**
     * 根据标签ID列表批量获取对应的标签名称, 如果某个
     *
     * @param tagIds 标签ID列表
     * @return 标签名称列表，如果输入为空则返回空列表
     */
    @Override
    public List<String> getTagsByIds (List<Long> tagIds) {
        if (tagIds == null || tagIds.isEmpty()) {
            return List.of();
        }
        return this.lambdaQuery()
                   .in(Tag::getId, tagIds)
                   .list()
                   .stream()
                   .map(Tag::getName)
                   .toList();
    }
}
