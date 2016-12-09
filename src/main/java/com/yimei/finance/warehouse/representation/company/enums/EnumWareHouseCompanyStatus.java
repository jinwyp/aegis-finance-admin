package com.yimei.finance.warehouse.representation.company.enums;

public enum EnumWarehouseCompanyStatus {
    Normal(1, "正常"),
    Deleted(2, "已删除"),
    ;

    public int id;
    public String name;

    EnumWarehouseCompanyStatus() {}

    EnumWarehouseCompanyStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
