package life.majiang.community.enums;

public enum NtificationTypeEnums {
    REPY_QUESTION(1,"回复了问题"),
    REPY_COMMENT(2,"回复了评论")
    ;
    private int status;
    private String name;

    public int getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    NtificationTypeEnums(int status, String name) {
        this.status = status;
        this.name = name;
    }

    public static String nameOfType(int type)
    {
        for (NtificationTypeEnums ntificationTypeEnums : NtificationTypeEnums.values()) {
            if (ntificationTypeEnums.getStatus()==type)
            {
                return ntificationTypeEnums.getName();
            }
        }
        return "";
    }
}
