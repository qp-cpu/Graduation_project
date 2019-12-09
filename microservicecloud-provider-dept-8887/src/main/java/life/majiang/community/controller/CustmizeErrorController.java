package life.majiang.community.controller;


import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("${server.error.path:${eror.path:/path}}")
public class CustmizeErrorController implements ErrorController {
    @Override
    public String getErrorPath() {
        return "error";
    }
    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView errorHtml(HttpServletRequest request,
                                  Model model)
    {
        HttpStatus status=getStatus(request);
        if(status.is4xxClientError())
        {
            model.addAttribute("message","请求出了问题！！！！");
        }
        if(status.is5xxServerError())
        {
            model.addAttribute("message","服务器出了问题！！！！");
        }
        return new ModelAndView("error");
    }

    private  HttpStatus getStatus(HttpServletRequest request)
    {
        Integer statuCode= (Integer) request.getAttribute("javax.servlet.erroe.status.status_code");
        if(statuCode==null)
        {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statuCode);
        }
        catch (Exception e)
        {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
