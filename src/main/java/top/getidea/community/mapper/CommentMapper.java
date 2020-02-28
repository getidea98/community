package top.getidea.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import top.getidea.community.model.Comment;

import java.util.List;

@Component
public interface CommentMapper {

    @Insert("INSERT INTO COMMENT(parentId,parentType,commentator,GMT_Create,GMT_Modified,likeCount,content) VALUES (#{parentId},#{parentType},#{commentator},#{gmtCreate},#{gmtModified},#{likeCount},#{content})")
    Integer insert(Comment comment);

    @Select("SELECT * FROM COMMENT WHERE ID = #{ID}")
    Comment queryById(@Param("ID") long id);

    @Select("SELECT * FROM COMMENT WHERE PARENTID = #{PARENTID} AND PARENTTYPE = #{PARENTTYPE}")
    List<Comment> queryByParentIdAndParentType(@Param("PARENTID") Integer ParentId, @Param("PARENTTYPE") Integer ParentType);
}