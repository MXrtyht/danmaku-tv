package cn.edu.scnu.danmakutv.domain.elasticsearch;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Data
@Document(indexName = "videos")
public class VideoEs {
    @Id
    private Long id;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title;

    @Field(type = FieldType.Keyword)
    private List<String> tags; // 标签名称列表

    @Field(type = FieldType.Integer)
    private Integer area;

    // 其他字段（coverUrl, duration等根据需要添加）
}