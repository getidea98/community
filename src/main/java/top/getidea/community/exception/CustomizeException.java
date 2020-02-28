package top.getidea.community.exception;

import lombok.Data;

@Data
public class CustomizeException extends RuntimeException {
    private String message;
    private Integer code;
    public CustomizeException(Integer code,String message) {
        this.message = message;
        this.code = code;
    }
    public CustomizeException(ICustomizeExceptionCode iCustomizeExceptionCode) {
        this.message = iCustomizeExceptionCode.getMessage();
        this.code = iCustomizeExceptionCode.getCode();
    }
}
