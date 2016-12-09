package com.yimei.finance.warehouse.representation.user.enums;

public enum EnumWarehouseUserStatus {
    Normal(1, "正常"),
    Deleted(2, "已删除"),
    ;

    public int id;
    public String name;

    EnumWarehouseUserStatus() {}

    EnumWarehouseUserStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
