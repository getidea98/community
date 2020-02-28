package top.getidea.community.exception;


public enum CustomizeExceptionCode implements ICustomizeExceptionCode{
    NO_LOGIN(2001,"您还没有登录"),
    QUESTION_NOTFOUND_PAGE(2002,"这个问题找不到了，要不咱换个?"),
    TARGET_PARENT_NOT_FOUND(2003,"未选中任何评论或问题"),
    ERROR_SEVER(2004,"服务器冒烟了，稍后再试"),
    COMMENT_NOTFOUND_PAGE(2005,"评论找不到喽"),
    COMMENT_CONTENT_IS_NULL(2006,"评论为空"),
    TARGET_ERROR(2007,"评论出错"),
    ;


    @Override
    public String getMessage(){
        return this.message;
    }
    @Override
    public Integer getCode(){
        return this.code;
    }
    CustomizeExceptionCode(Integer code,String message){
        this.code = code;
        this.message = message;
    }
    private String message;
    private Integer code;
}
