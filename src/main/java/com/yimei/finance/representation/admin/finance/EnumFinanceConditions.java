package com.yimei.finance.representation.admin.finance;

/**
 * Created by liuxinjie on 16/8/22.
 */
public enum EnumFinanceConditions {
    salesmanAudit("业务员审核"),
    needSalesmanSupplyInvestigationMaterial("需要业务员补充尽调材料"),
    investigatorAudit("尽调员审核"),
    needSalesmanSupplySupervisionMaterial("需要业务员补充监管材料"),
    supervisorAudit("监管员审核"),
    needInvestigatorSupplyRiskMaterial("需要尽调员补充风控材料"),
    needSupervisorSupplyRiskMaterial("需要监管员补充风控材料"),
    riskManagerAudit("风控人员审核"),
    ;

    public String name;

    EnumFinanceConditions(){}

    EnumFinanceConditions(String name) {
        this.name = name;
    }
}
