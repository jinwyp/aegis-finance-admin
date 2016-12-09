package com.yimei.finance.warehouse.representation.user.enums;

public enum EnumWarehouseAdminUserStatus {
    Normal(1, "正常"),
    Deleted(2, "已删除"),
    ;

    public int id;
    public String name;

    EnumWarehouseAdminUserStatus() {}

    EnumWarehouseAdminUserStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
