package top.getidea.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import top.getidea.community.model.Question;

import java.util.List;

@Component
public interface QuestionMapper {

    @Select("SELECT count(1) FROM QUESTION")
    long queryAll();

    @Select("SELECT * FROM QUESTION LIMIT #{START},#{SIZE}")
    List<Question> queryQuestions(@Param("START") long start, @Param("SIZE") long size);

    @Select("SELECT COUNT(*) FROM QUESTION WHERE CREATOR = #{ACCOUNT}")
    Integer queryCountByCreator(@Param("ACCOUNT") String account);

    @Select("SELECT * FROM QUESTION WHERE ID = #{ID}")
    Question queryById(@Param("ID") Integer id);

    @Insert("INSERT INTO QUESTION (title, description,GMT_Create,GMT_Modified,CREATOR,COMMENTCOUNT,VIEWCOUNT,LIKECOUNT,TAG) VALUES (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{commentCount},#{viewCount},#{likeCount},#{tag})")
    boolean insert(Question question);

    @Update("UPDATE QUESTION SET title = #{title}, description = #{description},GMT_Create = #{gmtCreate},GMT_Modified = #{gmtModified},CREATOR = #{creator},COMMENTCOUNT = #{commentCount}, VIEWCOUNT = #{viewCount}, LIKECOUNT = #{likeCount},TAG = #{tag} WHERE ID = #{id}")
    int updateById(Question question);

    @Select("SELECT * FROM QUESTION WHERE CREATOR = #{ACCOUNT} LIMIT #{START},#{SIZE}")
    List<Question> queryLimitByCreator(@Param("ACCOUNT") String account, @Param("START") long start, @Param("SIZE") long size);

    @Update("UPDATE QUESTION SET VIEWCOUNT = VIEWCOUNT+#{COUNT} WHERE ID=#{ID}")
    Integer updateViewCountById(@Param("ID") Integer id,@Param("COUNT") Integer count);

    @Update("UPDATE QUESTION SET COMMENTCOUNT = COMMENTCOUNT+#{COUNT} WHERE ID=#{ID}")
    Integer updateCommentCountById(@Param("ID") Integer id,@Param("COUNT") Integer count);

    @Select("SELECT * FROM QUESTION WHERE SUBSTRING(TITLE) = #{KEY} OR SUBSTRING(TAG) = #{TAG}")
    List<Question> queryByKey(@Param("KEY")String key);

}