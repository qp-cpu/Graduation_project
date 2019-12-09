package life.majiang.community.dto;

import life.majiang.community.exception.CustmizeException;
import life.majiang.community.exception.CustomizeErrorcode;
import lombok.Data;

@Data
public class ResultDto<T>{
    private Integer code;
    private String message;
    private T data;

    public static  ResultDto errorOf(Integer code,String mesage)
    {
        ResultDto resultDto=new ResultDto();
        resultDto.setCode(code);
        resultDto.setMessage(mesage);
        return  resultDto;
    }
    public static ResultDto errorof(CustomizeErrorcode errorcode)
    {
        return  errorOf(errorcode.getCode(),errorcode.getMessage());
    }
    public static  ResultDto okOf()
    {
        ResultDto resultDto=new ResultDto();
        resultDto.setCode(200);
        resultDto.setMessage("请求成功");
        return  resultDto;
    }

    public static ResultDto errorof(CustmizeException ex) {
        return errorOf(ex.getCode(),ex.getMessage());
    }

    public static<T>  ResultDto okOf(Object t)
    {
        ResultDto resultDto=new ResultDto();
        resultDto.setCode(200);
        resultDto.setMessage("请求成功");
        resultDto.setData(t);
        return  resultDto;
    }
}
