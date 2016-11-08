package com.yimei.finance.representation.admin.user.enums;

public enum EnumUserStatus {
    Normal(1, "正常"),
    Deleted(2, "已删除"),
    ;

    public int id;
    public String name;

    EnumUserStatus() {}

    EnumUserStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
