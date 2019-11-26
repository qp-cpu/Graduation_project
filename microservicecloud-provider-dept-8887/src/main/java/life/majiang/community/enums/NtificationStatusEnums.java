package life.majiang.community.enums;

public enum NtificationStatusEnums {
     UNREAD(0),
    READ(1)
    ;

    private int status;

    NtificationStatusEnums(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
