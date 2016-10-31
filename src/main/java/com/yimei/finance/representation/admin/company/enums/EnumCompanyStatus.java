package com.yimei.finance.representation.admin.company.enums;

public enum EnumCompanyStatus {
    Normal(1, "正常"),
    Deleted(2, "已删除"),
    ;

    public int id;
    public String name;

    EnumCompanyStatus() {}

    EnumCompanyStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
