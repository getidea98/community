package top.getidea.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import top.getidea.community.dto.CommentDTO;
import top.getidea.community.dto.QuestionDTO;
import top.getidea.community.service.CommentService;
import top.getidea.community.service.QuestionService;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    CommentService commentService;
    /**
     * 问题浏览页面*/
    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Integer id,
                           Model model){
        questionService.incView(id,1);    //浏览数+1
        QuestionDTO questionDTO = questionService.getById(id); //获取当前浏览问题的数据，并封装到 QuestionDTO，以便于在页面展示
        List<CommentDTO> commentDTOList =commentService.getByQuestionId(id);
        model.addAttribute("questionDTO",questionDTO);
        model.addAttribute("commentDTOList",commentDTOList);
        return "question";
    }
}
