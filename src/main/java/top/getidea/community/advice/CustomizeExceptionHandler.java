package top.getidea.community.advice;

import com.alibaba.fastjson.JSON;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import top.getidea.community.dto.ResultCode;
import top.getidea.community.exception.CustomizeException;
import top.getidea.community.exception.CustomizeExceptionCode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handle(Throwable e, Model model, HttpServletRequest request, HttpServletResponse response) {
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)) {
            ResultCode resultCode;
            // 返回 JSON
            if (e instanceof CustomizeException) {
                resultCode = ResultCode.errorof((CustomizeException) e);
            } else {
                resultCode = ResultCode.errorof(CustomizeExceptionCode.ERROR_SEVER);
            }
            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultCode));
                writer.close();
            } catch (IOException ioe) {
            }
            return null;
        } else {
            // 错误页面跳转
            if (e instanceof CustomizeException) {
                model.addAttribute("message", e.getMessage());
            } else {
                model.addAttribute("message", CustomizeExceptionCode.ERROR_SEVER.getMessage());
            }
            return new ModelAndView("error");
        }
    }
}
