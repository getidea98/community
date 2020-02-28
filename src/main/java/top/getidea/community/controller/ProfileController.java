package top.getidea.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import top.getidea.community.dto.PageInfo;
import top.getidea.community.mapper.UserMapper;
import top.getidea.community.model.User;
import top.getidea.community.service.QuestionService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    QuestionService questionService;

    @Autowired
    UserMapper userMapper;
    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action")String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "currentPage",defaultValue = "1") Integer currentPage,
                          @RequestParam(name = "pageSize",defaultValue = "5") Integer pageSize){
        User user = (User) request.getSession().getAttribute("user");
        if("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
            PageInfo pageInfo = questionService.listById(user.getAccount(),currentPage,pageSize);
            model.addAttribute(pageInfo);
        }else if ("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","我的回复");
        }

        return "profile";
    }
}
