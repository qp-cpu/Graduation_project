package life.majiang.community.enums;

public enum  ContentTypeEnums {
    QUESTION(1),
    COMMENT(2)
    ;
    private Integer type;
    public static boolean isExist(Integer type) {
       for(ContentTypeEnums contentTypeEnums: ContentTypeEnums.values())
       {
           if(contentTypeEnums.getType()==type)
           {
               return  true;
           }
       }
        return false;
    }
    public Integer getType() {
        return type;
    }
    ContentTypeEnums(Integer type){
        this.type=type;
    }
}
