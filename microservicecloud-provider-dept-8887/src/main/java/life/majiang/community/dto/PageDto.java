package life.majiang.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageDto {
    private List<PublishDto> publishDtos;
    private boolean showPrevous;
    private boolean showFristpage;
    private boolean showNext;
    private boolean showEndpage;
    private Integer page;
    private List<Integer> pages=new ArrayList<>();
    private Integer totalpage;

    public void setPagenation(Integer totalcount, Integer page, Integer size) {



        if(totalcount % size ==0)
        {
            totalpage=totalcount /size;
        }
        else {
            totalpage=totalcount/size +1 ;
        }
        //对违规值进行处理
        if(page>totalpage)
        {
            page=totalpage;
        }

        this.page=page;
        pages.add(page);
        for(int i=1;i<=3;i++)
        {
            if(page-i>0)
            {
                pages.add(0,page-i);
            }
            if(page+i<=totalpage)
            {
                pages.add(page+i);
            }
        }

        //是否展示上一页
        if(page==1){
            showPrevous=false;
        }
        else {
            showPrevous=true;
        }
        //是否展示下一页
        if(page==totalpage)
        {
            showNext=false;
        }
        else {
            showNext=true;
        }
        //是否显示第一页
        if(pages.contains(1))
        {
           showFristpage=false;
        }else{
            showFristpage=true;
        }

        //是否显示最后一页
        if(pages.contains(showEndpage))
        {
            showEndpage=false;
        }
        else {
            showEndpage=true;
        }
    }
}
