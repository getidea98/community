package top.getidea.community.dto;

import lombok.Data;
import top.getidea.community.model.User;

@Data
public class CommentDTO {
    private Long id;
    private Integer parentId;
    private Integer parentType;
    private String commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer likeCount;
    private String content;
    private User user;
}
