package com.yimei.finance.warehouse.representation.company.enums;

public enum EnumWarehouseAdminCompanyStatus {
    Normal(1, "正常"),
    Deleted(2, "已删除"),
    ;

    public int id;
    public String name;

    EnumWarehouseAdminCompanyStatus() {}

    EnumWarehouseAdminCompanyStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
