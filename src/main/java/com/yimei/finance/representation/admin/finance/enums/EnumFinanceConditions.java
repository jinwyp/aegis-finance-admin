package com.yimei.finance.representation.admin.finance.enums;

public enum EnumFinanceConditions {
    needSalesmanSupplyInvestigationMaterial("需要业务员补充尽调材料"),
    needSalesmanSupplySupervisionMaterial("需要业务员补充监管材料"),
    needSalesmanSupplyRiskManagerMaterial("需要业务员员补充风控材料"),
    ;

    public String name;

    EnumFinanceConditions(){}

    EnumFinanceConditions(String name) {
        this.name = name;
    }
}
