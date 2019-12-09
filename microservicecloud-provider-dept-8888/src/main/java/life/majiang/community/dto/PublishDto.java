package life.majiang.community.dto;

import life.majiang.community.entity.UserEntity;
import lombok.Data;

@Data
public class PublishDto {
    private Integer id;

    private String title;

    private String descrition;

    private Long gmtCreate;

    private Long gmtModified;

    private Integer creator;

    private Integer commentCount;

    private Integer viewCount;

    private Integer likeCount;

    private String tag;

    private UserEntity userEntity;
}
