package top.getidea.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.getidea.community.mapper.UserMapper;
import top.getidea.community.model.User;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    /**
     * 更新/创建 user数据*/
    public Integer insertUser(User user){
        return userMapper.insertUser(user);
    }
}
