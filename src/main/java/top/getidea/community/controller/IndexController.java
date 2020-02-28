package top.getidea.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.getidea.community.dto.PageInfo;
import top.getidea.community.service.QuestionService;

@Controller
public class IndexController {

    @Autowired
    QuestionService questionService;
    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "currentPage",defaultValue = "1")String currentPage,
                        @RequestParam(name = "pageSize",defaultValue = "5")String pageSize) {
        PageInfo pageInfo = questionService.list(Integer.valueOf(currentPage), Integer.valueOf(pageSize));
        model.addAttribute("pageInfo",pageInfo);
        return "index";
    }
}