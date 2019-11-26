package life.majiang.community.dto;

import life.majiang.community.entity.UserEntity;
import lombok.Data;

@Data
public class CommentDto {
    private Long id;
    private Long parentId;
    private Integer type;
    private Integer commentor;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private String content;
    private Integer commentCount;
    private UserEntity userEntity;
}
