package com.yimei.finance.entity.admin.finance;

/**
 * Created by liuxinjie on 16/8/22.
 */
public enum EnumFinanceEventType {
    onlineTraderAudit("线上交易员审核并填写材料"),
    salesmanAudit("业务员审核并填写材料"),
    investigatorAudit("尽调员审核"),
    salesmanSupplyInvestigationMaterial("业务员补充尽调材料"),
    supervisorAudit("监管员审核"),
    salesmanSupplySupervisorMaterial("业务员补充监管材料"),
    riskManagerAudit("风控人员审核"),
    riskManagerAuditSuccess("风控审核通过,填写合同模板,通知用户"),
    investigatorSupplyMaterial("尽调员补充材料"),
    ;

    public String name;

    EnumFinanceEventType(){}

    EnumFinanceEventType(String name) {
        this.name = name;
    }
}
