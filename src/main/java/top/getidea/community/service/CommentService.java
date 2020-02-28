package top.getidea.community.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.getidea.community.dto.CommentDTO;
import top.getidea.community.exception.CustomizeException;
import top.getidea.community.exception.CustomizeExceptionCode;
import top.getidea.community.mapper.CommentMapper;
import top.getidea.community.mapper.UserMapper;
import top.getidea.community.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    QuestionService questionService;

    @Autowired
    UserMapper userMapper;

    @Transactional
    public void insert(Comment comment) {
        if(comment.getParentId() == null || comment.getParentId() == 0){
            throw new CustomizeException(CustomizeExceptionCode.TARGET_PARENT_NOT_FOUND);
        }
        if(comment.getContent() == null || comment.getContent().equals("")){
            //评论内容为空
            throw new CustomizeException(CustomizeExceptionCode.COMMENT_CONTENT_IS_NULL);
        }
        if(comment.getParentType() == null || !CommentTypeEnum.isExists(comment.getParentType())){
            throw new CustomizeException(CustomizeExceptionCode.TARGET_ERROR);
        }else{
            if(comment.getParentType() == CommentTypeEnum.QUESTION.getType()){
                //对问题评论
                questionService.getQuestionById(comment.getParentId());
                commentMapper.insert(comment);
                questionService.incComment(comment.getParentId(),1);
            }else{
                //对评论评论
                Comment dbcomment = commentMapper.queryById((long) comment.getParentId());
                if(dbcomment == null){
                    throw new CustomizeException(CustomizeExceptionCode.COMMENT_NOTFOUND_PAGE);
                }
                commentMapper.insert(comment);
            }
        }
    }

    public List<CommentDTO> getByQuestionId(Integer id) {
        List<Comment> commentsList = commentMapper.queryByParentIdAndParentType(id,CommentTypeEnum.QUESTION.getType()); //根据父类的id和父类的类型选择评论
        if(commentsList.size() == 0){
            return new ArrayList<>();
        }
        //获取去重的评论人
        Set<String> commentators = commentsList.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());


        //获取评论人并转换map
        List<String> userIds = new ArrayList<>();
        userIds.addAll(commentators);
        List<User> userList = new ArrayList<>();
        for (String userId : userIds) {
            userList.add(userMapper.queryByContainAccount(userId));
        }
        Map<String, User> userMap = userList.stream().collect(Collectors.toMap(user -> user.getAccount(), user -> user));

        //装欢comment 为commemtDTO
        List<CommentDTO> commentDTOList = commentsList.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOList;
    }
}