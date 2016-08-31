package com.yimei.finance.representation.admin.finance;

/**
 * 金融申请单 备注/批注 类型
 */
public enum EnumFinanceCommentType {

    OnlineTraderAuditComment("AA_AA_AA_FLAG_AA_AA_AA_AA_01", "线上交易员审核备注"),
    OnlineTraderManageComment("AA_AA_AA_FLAG_AA_AA_AA_01", "线上交易员管理员分配备注"),
    SalesmanAuditComment("AA_AA_AA_FLAG_AA_AA_01", "业务员审核备注"),
    SalesmanSupplyInvestigationMaterialComment("AA_AA_AA_FLAG_AA_AA_02", "业务员补充尽调材料备注"),
    SalesmanSupplySupervisionMaterialComment("AA_AA_AA_FLAG_AA_AA_03", "业务员补充监管材料备注"),
    SalesmanManageComment("AA_AA_AA_FLAG_AA_01", "业务员管理员分配备注"),
    InvestigatorAuditComment("AA_AA_AA_AA_FLAG_AA_01", "尽调员审核备注"),
    InvestigatorSupplyRiskMaterial("AA_AA_AA_AA_FLAG_AA_02", "尽调员补充风控材料备注"),
    InvestigatorManageComment("AA_AA_AA_DD_FLAG_01", "尽调员管理员分配备注"),
    SupervisorAuditComment("AA_AA_AA_DD_FLAG_AA_01", "监管员审核备注"),
    SupervisorSupplyRiskMaterial("AA_AA_AA_DD_FLAG_AA_02", "监管员补充风控材料备注"),
    SupervisorManageComment("AA_AA_AA_DD_FLAG_01", "监管员管理员分配备注"),
    RiskManagerAuditComment("AA_AA_01", "风控人员审核备注"),
    RiskManagerContractComment("AA_AA_02", "风控人员上传合同备注"),
    RiskManagerManageComment("AA_01", "风控人员管理员分配备注"),

    ;

    public String id;
    public String name;

    public static final String RiskManagerManageRight = "AA";
    public static final String RiskManagerRight = "AA_AA";
    public static final String InvestigatorManageRight = "AA_AA_AA_AA_FLAG";
    public static final String InvestigatorRight = "AA_AA_AA_AA_FLAG_AA";
    public static final String SupervisorManageRight = "AA_AA_AA_DD_FLAG";
    public static final String SupervisorRight = "AA_AA_AA_DD_FLAG_AA";
    public static final String SalesmanManageRight = "AA_AA_AA_FLAG_AA";
    public static final String SalesmanRight = "AA_AA_AA_FLAG_AA_AA";
    public static final String OnlineTraderManageRight = "AA_AA_AA_FLAG_AA_AA_AA";
    public static final String OnlineTraderRight = "AA_AA_AA_FLAG_AA_AA_AA_AA";


    EnumFinanceCommentType() {
    }

    EnumFinanceCommentType(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
