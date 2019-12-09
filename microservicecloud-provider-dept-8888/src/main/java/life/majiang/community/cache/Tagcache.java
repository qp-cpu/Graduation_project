package life.majiang.community.cache;

import life.majiang.community.dto.Tagdto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class Tagcache {
    public static List<Tagdto> get(){
        List<Tagdto> tagdtos=new ArrayList<>();
        Tagdto tagdto = new Tagdto();
        tagdto.setCachegoryName("开发语言");
        tagdto.setTags(Arrays.asList("js","php","css","html","java","node.js","python"));

        Tagdto framework=new Tagdto();
        framework.setCachegoryName("平台框架");
        framework.setTags(Arrays.asList("spring","express","django","flask","struts"));

        Tagdto server=new Tagdto();
        server.setCachegoryName("服务器");
        server.setTags(Arrays.asList("Liunx","apache","unix","缓存","负载均衡","hadoop"));

        Tagdto database=new Tagdto();
        database.setCachegoryName("数据库");
        database.setTags(Arrays.asList("Redis","mysql","oracle","sqlserver"));

        Tagdto developtools=new Tagdto();
        developtools.setCachegoryName("开发工具");
        developtools.setTags(Arrays.asList("git","eclipse","idea","navicat"));

        tagdtos.add(tagdto);
        tagdtos.add(framework);
        tagdtos.add(server);
        tagdtos.add(database);
        tagdtos.add(developtools);
        return tagdtos;
    }
    //对不合法输入的tag标签传出
    public String filterTag(String tags)
    {
        String[] split = StringUtils.split(tags, ",");
        List<Tagdto> tagDTOS = get();

        List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(split).filter(t -> StringUtils.isBlank(t) || !tagList.contains(t)).collect(Collectors.joining(","));
        return invalid;
    }
}
