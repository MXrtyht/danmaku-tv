package cn.edu.scnu.danmakutv.domain.elasticsearch;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;

@Data
@Document(indexName = "video")
public class VideoES {
    @Id
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private String videoUrl;

    private String coverUrl;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text)
    private String description;

    private boolean type;

    private Integer duration;

    private Integer area;

    @Field(type = FieldType.Date)
    private LocalDate createAt;

    @Field(type = FieldType.Date)
    private LocalDate updateAt;
}
