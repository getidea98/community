package top.getidea.community.dto;

import lombok.Data;

@Data
public class CommentCreateDTO {
    private Integer parentType;
    private String content;
    private Integer parentId;
}
