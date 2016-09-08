package com.yimei.finance.representation.admin.finance.enums;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "金融单, 流程条件类型")
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
