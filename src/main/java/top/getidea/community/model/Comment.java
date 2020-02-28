package top.getidea.community.model;

import lombok.Data;

@Data
public class Comment {
    private Long id;
    private Integer parentId;
    private Integer parentType;
    private String commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer likeCount;
    private String content;
}