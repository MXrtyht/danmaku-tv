package cn.edu.scnu.danmakutv.domain.elasticsearch;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;

@Data
@Document(indexName = "user_profiles")
public class UserProfilesES {
    @Id
    private Long id;

    @Field(name="user_id",type=FieldType.Long)
    private Long userId;

    private String nickname;

    private Boolean gender;

    @Field(name="birthday",type = FieldType.Date)
    private LocalDate birthday;

    private String sign;

    private String announcement;

    private String avatar;

    private Integer coin;

    @Field(name="create_at",type = FieldType.Date)
    private LocalDate createAt;

    @Field(name="update_at",type = FieldType.Date)
    private LocalDate updateAt;
}
