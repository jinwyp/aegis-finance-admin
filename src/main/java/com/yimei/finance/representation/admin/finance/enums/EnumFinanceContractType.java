package com.yimei.finance.representation.admin.finance.enums;

public enum EnumFinanceContractType {
    GoodsMortgage_Upstream_Purchase(1, "买方仓押上游合同"),
    GoodsMortgage_Downstream_Purchase(2, "买家仓押下游合同"),
    ;

    public int id;
    public String name;

    EnumFinanceContractType() {}

    EnumFinanceContractType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static String getName(EnumFinanceContractType status) {
        return status.name;
    }

}
