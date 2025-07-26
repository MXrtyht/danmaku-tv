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
    private Long id;

    @Field(name="user_id",type=FieldType.Long)
    private Long userId;

    @Field(name="video_url",type = FieldType.Text)
    private String videoUrl;

    @Field(name="cover_url",type = FieldType.Text)
    private String coverUrl;

    @Field(name="title",type = FieldType.Text)
    private String title;

    @Field(name="description",type = FieldType.Text)
    private String description;

    private Boolean type;

    private Integer duration;

    private Integer area;

    @Field(name = "create_at",type = FieldType.Date)
    private LocalDate createAt;

    @Field(name = "update_at",type = FieldType.Date)
    private LocalDate updateAt;
}
