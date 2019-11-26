package life.majiang.community.exception;

public enum CustomizeErrorcode implements  ICustomizeErrorcodde{
    QUESTION_NOT(500,"问题没有没有找到，换个问题试试呗"),
    TARGET_PARAM_NOT_FIND(500,"未选中任何问题或者评论进行回复"),
    NO_LOGIN(300,"未登录，不能进行操作 "),
    SYSTEM_ERROR(500,"服务器错误！！！！！ "),
    TYPE_PARAM_ERROE(500,"评论类型错误或者不存在！！！"),
    COMMENT_NOT_FIND(500,"评论不存在了！！！"),
    COMMENT_IS_EMPTY(301,"评论不能为空！！！");
   ;
    private String message;
    private Integer code;
    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    CustomizeErrorcode(Integer code, String message)
    {
        this.message=message;
        this.code=code;
    }
}
