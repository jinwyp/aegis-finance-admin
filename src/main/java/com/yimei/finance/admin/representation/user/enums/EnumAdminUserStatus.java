package com.yimei.finance.admin.representation.user.enums;

public enum EnumAdminUserStatus {
    Normal(1, "正常"),
    Deleted(2, "已删除"),
    ;

    public int id;
    public String name;

    EnumAdminUserStatus() {}

    EnumAdminUserStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
