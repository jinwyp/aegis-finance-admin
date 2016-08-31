package com.yimei.finance.representation.admin.finance;

/**
 * 金融申请单 备注/批注 类型
 */
public enum EnumFinanceAttachment {

    OnlineTraderAuditAttachment(EnumFinanceEventType.onlineTraderAudit.toString(), "线上交易员审核附件"),
    SalesmanAuditAttachment(EnumFinanceEventType.salesmanAudit.toString(), "业务员审核附件"),
    SalesmanSupplyAttachment_Investigator(EnumFinanceEventType.salesmanSupplyInvestigationMaterial.toString(), "业务员补充尽调员材料"),
    SalesmanSupplyAttachment_Supervisor(EnumFinanceEventType.salesmanSupplySupervisionMaterial.toString(), "业务员补充监管员材料"),
    InvestigatorAuditAttachment(EnumFinanceEventType.investigatorAudit.toString(), "尽调员审核附件"),
    InvestigatorSupplyRiskAttachment(EnumFinanceEventType.investigatorSupplyRiskMaterial.toString(), "尽调员补充风控材料"),
    SupervisorAuditAttachment(EnumFinanceEventType.supervisorAudit.toString(), "监管员审核附件"),
    SupervisorSupplyRiskAttachment(EnumFinanceEventType.supervisorSupplyRiskMaterial.toString(), "监管员补充风控材料"),
    RiskManagerAuditAttachment(EnumFinanceEventType.riskManagerAudit.toString(), "风控人员审核附件"),

    ;

    public String type;
    public String name;

    EnumFinanceAttachment() {
    }

    EnumFinanceAttachment(String type, String name) {
        this.type = type;
        this.name = name;
    }
}
