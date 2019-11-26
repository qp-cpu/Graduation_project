package life.majiang.community.exception;

public class CustmizeException  extends  RuntimeException{
    private  String message;
    private Integer code;

    public CustmizeException(ICustomizeErrorcodde errorcodde)
    {
        this.message=errorcodde.getMessage();
        this.code=errorcodde.getCode();
    }
    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
