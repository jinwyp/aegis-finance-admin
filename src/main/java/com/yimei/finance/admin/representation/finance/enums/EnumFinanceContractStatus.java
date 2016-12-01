package com.yimei.finance.admin.representation.finance.enums;

public enum EnumFinanceContractStatus {
    UnWrite(1, "未填写"),
    Submitted(2, "已提交"),

    ;
    public int id;
    public String name;

    EnumFinanceContractStatus(){}

    EnumFinanceContractStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
