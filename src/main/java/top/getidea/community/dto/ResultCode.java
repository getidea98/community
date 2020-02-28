package top.getidea.community.dto;

import lombok.Data;
import top.getidea.community.exception.CustomizeException;
import top.getidea.community.exception.CustomizeExceptionCode;
@Data
public class ResultCode {
    private Integer code;
    private String message;
    public static ResultCode errorof(Integer code,String message){
        ResultCode resultCode = new ResultCode(null,null);
        resultCode.code = code;
        resultCode.message = message;
        return resultCode;
    }

    public static ResultCode errorof(CustomizeException customizeException) {
        return errorof(customizeException.getCode(),customizeException.getMessage());
    }

    public static ResultCode errorof(CustomizeExceptionCode questionNotfoundPage) {
        return errorof(questionNotfoundPage.getCode(),questionNotfoundPage.getMessage());
    }

    public static ResultCode okof(){
        return new ResultCode(200,"请求成功");
    }

    private ResultCode(Integer code,String message){
        this.message = message;
        this.code = code;
    }
}
