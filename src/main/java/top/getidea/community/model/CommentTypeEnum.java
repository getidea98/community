package top.getidea.community.model;

import org.springframework.beans.factory.annotation.Autowired;
import top.getidea.community.mapper.CommentMapper;

public enum CommentTypeEnum {

    QUESTION(1),
    COMMENT(2);

    private Integer type;

    public Integer getType() {
        return type;
    }

    @Autowired
    CommentMapper commentmapper;

    CommentTypeEnum(Integer type) {
        this.type = type;
    }

    public static boolean isExists(Integer type) {
        for (CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()) {
            if (type == commentTypeEnum.getType()){
                return true;
            }
        }
        return false;
    }
}
