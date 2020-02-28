package top.getidea.community.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.getidea.community.dto.GitHubAccess;
import top.getidea.community.dto.GitHubAccessTokenDTO;
import top.getidea.community.model.User;
import top.getidea.community.provider.GitHubprovider;
import top.getidea.community.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GitHubAccessTokenDTO gitHubAccessTokenDTO;

    @Value("${github.Client.id}")
    private String Client_id;
    @Value("${github.Client.secret}")
    private String Client_secret;
    @Value("${github.Redirect.uri}")
    private String Redirect_uri;

    @Autowired
    UserService userService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code,
                           @RequestParam(name="state") String state,
                           HttpServletResponse response){
        gitHubAccessTokenDTO.setClient_id(Client_id);
        gitHubAccessTokenDTO.setClient_secret(Client_secret);
        gitHubAccessTokenDTO.setState(state);
        gitHubAccessTokenDTO.setCode(code);
        gitHubAccessTokenDTO.setRedirect_uri(Redirect_uri);
        GitHubprovider gitHubprovider = new GitHubprovider();
        String responseCode =  gitHubprovider.getGitHubAccessToken(gitHubAccessTokenDTO);
        GitHubAccess gitHubAccessInfo = gitHubprovider.getGitHubAccessInfo(responseCode);
        if(gitHubAccessInfo != null){
            //登陆成功
            User user = new User();
            user.setAccount(gitHubAccessInfo.getId());
            user.setName(gitHubAccessInfo.getName());
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(gitHubAccessInfo.getAvatar_url());
            user.setBio(gitHubAccessInfo.getBio());
            userService.insertUser(user);
            response.addCookie(new Cookie("token",token));
            //返回主页面
            return "redirect:/";
        }else{
            //登陆失败，返回主页面
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response,
                         HttpServletRequest request){
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
