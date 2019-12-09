package life.majiang.community.enums;

public enum NtificationStatusEnums {
    UNREAD(0),
    READ(1)
    ;
    private int status;

    public int getStatus() {
        return status;
    }

    NtificationStatusEnums(int status) {
        this.status = status;
    }
}
