package top.getidea.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.getidea.community.dto.CommentCreateDTO;
import top.getidea.community.dto.ResultCode;
import top.getidea.community.exception.CustomizeExceptionCode;
import top.getidea.community.model.Comment;
import top.getidea.community.model.User;
import top.getidea.community.service.CommentService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {

    @Autowired
    CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultCode.errorof(CustomizeExceptionCode.NO_LOGIN);
        }
        Comment comment = new Comment();
        comment.setCommentator(user.getAccount());
        comment.setContent(commentCreateDTO.getContent());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setLikeCount(0);
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setParentType(commentCreateDTO.getParentType());
        comment.setGmtModified(System.currentTimeMillis());
        commentService.insert(comment);
        return ResultCode.okof();
    }
}
