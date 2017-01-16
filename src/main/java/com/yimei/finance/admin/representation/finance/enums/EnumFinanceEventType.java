package com.yimei.finance.admin.representation.finance.enums;

public enum EnumFinanceEventType {
    onlineTraderAudit("线上交易员审核并填写材料"),
    salesmanAudit("业务员审核并填写材料"),
    investigatorAudit("尽调员审核"),
    salesmanSupplyInvestigationMaterial("业务员补充尽调材料"),
    supervisorAudit("监管员审核"),
    salesmanSupplySupervisionMaterial("业务员补充监管材料"),
    riskManagerAudit("风控人员审核"),
    salesmanSupplyRiskManagerMaterial("业务员补充风控材料"),
    ;

    public String name;

    EnumFinanceEventType(){}

    EnumFinanceEventType(String name) {
        this.name = name;
    }
}
