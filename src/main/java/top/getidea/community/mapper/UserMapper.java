package top.getidea.community.mapper;


import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import top.getidea.community.model.User;

import java.util.List;

@Component
public interface UserMapper {

    @Select("INSERT INTO USER(account,name,GMT_Modified,GMT_Create,token,avatarUrl,bio) VALUES (#{account},#{name},#{gmtModified},#{gmtCreate},#{token},#{avatarUrl},#{bio})")
    Integer insertUser(User user);

    @Select("SELECT * FROM USER WHERE ACCOUNT = #{CREATOR}")
    List<User> queryByAccount(@Param("CREATOR") String creator);

    @Select("SELECT * FROM USER WHERE TOKEN = #{TOKEN}")
    List<User> queryByToken(@Param("TOKEN") String Token);

    @Select("SELECT * FROM USER WHERE ACCOUNT = #{ACCOUNT} order by id desc limit 1")
    User queryByContainAccount(@Param("ACCOUNT")String account);
}