package life.majiang.community.controller;

import life.majiang.community.cache.Tagcache;
import life.majiang.community.dto.PublishDto;
import life.majiang.community.entity.PublishEntity;
import life.majiang.community.entity.UserEntity;
import life.majiang.community.exception.CustomizeErrorcode;
import life.majiang.community.service.PublishService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;


@Controller
public class PublishController {

    @Autowired
    private PublishService publishService;

    @Autowired
    private Tagcache tagcache;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable("id") Integer id,
                       Model model)
    {
        PublishDto publishDto = publishService.getBYid(id);
        model.addAttribute("title",publishDto.getTitle());
        model.addAttribute("description",publishDto.getDescrition());
        model.addAttribute("tag",publishDto.getTag());
        model.addAttribute("id",publishDto.getId());
        model.addAttribute("update","修改");
        model.addAttribute("tags",tagcache.get());
        return "publish";
    }

    @GetMapping("/publish")
    public String publish(Model model){
        model.addAttribute("tags",tagcache.get());
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam(value = "title",required = false) String title,
                            @RequestParam(value = "description",required = false) String description,
                            @RequestParam(value = "tag",required = false) String tag,
                            @RequestParam(value="id",required = false) Integer id,
                            HttpServletRequest request,
                            Model model)
    {
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        model.addAttribute("tags",tagcache.get());

        if(description == null || description == "")
        {
            model.addAttribute("error","问题描述不能为空");
            return "publish";
        }
        if(title == null || title == "")
        {
            model.addAttribute("error","问题标题不能为空");
            return "publish";
        }
        if(tag == null || tag == "")
        {
            model.addAttribute("error","标签不能为空");
            return "publish";
        }

        if(StringUtils.isBlank(tag))
        {
            String tag1=tagcache.filterTag(tag);
            model.addAttribute("error","输入非法标签"+"  "+tag1);
            return "publish";
        }

        UserEntity userEntity=(UserEntity) request.getSession().getAttribute("user");
        if(userEntity == null)
        {
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        PublishEntity publishEntity = new PublishEntity();
        publishEntity.setTitle(title);
        publishEntity.setDescrition(description);
        publishEntity.setTag(tag);
        publishEntity.setCreator(userEntity.getId());
        publishEntity.setId(id);
        publishService.inserOrUpdatetpublish(publishEntity);
        return "redirect:/";
    }
}
