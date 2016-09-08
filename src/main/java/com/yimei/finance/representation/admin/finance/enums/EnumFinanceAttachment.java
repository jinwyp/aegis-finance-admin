package com.yimei.finance.representation.admin.finance.enums;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "金融单, 附件类型")
public enum EnumFinanceAttachment {
    OnlineTraderAuditAttachment(EnumFinanceEventType.onlineTraderAudit.toString(), "线上交易员审核附件"),
    SalesmanAuditAttachment(EnumFinanceEventType.salesmanAudit.toString(), "业务员审核附件"),
    SalesmanSupplyAttachment_Investigator(EnumFinanceEventType.salesmanSupplyInvestigationMaterial.toString(), "业务员补充尽调员材料"),
    SalesmanSupplyAttachment_Supervisor(EnumFinanceEventType.salesmanSupplySupervisionMaterial.toString(), "业务员补充监管员材料"),
    InvestigatorAuditAttachment(EnumFinanceEventType.investigatorAudit.toString(), "尽调员审核附件"),
    SupervisorAuditAttachment(EnumFinanceEventType.supervisorAudit.toString(), "监管员审核附件"),
    SalesmanSupplyAttachment_RiskManager(EnumFinanceEventType.salesmanSupplyRiskManagerMaterial.toString(), "业务员补充风控材料"),
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
