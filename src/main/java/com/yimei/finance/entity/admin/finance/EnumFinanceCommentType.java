package com.yimei.finance.entity.admin.finance;

/**
 * 金融申请单 备注/批注 类型
 */
public enum EnumFinanceCommentType {

    OnlineTraderAuditComment("GROUP00002_Audit", "线上交易员审核备注"),
    OnlineTraderManageComment("GROUP0000", "线上交易员管理员备注"),
    SalesmanAuditComment("12", "业务员审核备注"),
    SalesmanManageComment("9", "业务员管理员备注"),
    InvestigatorAuditComment("18", "尽调员审核备注"),
    InvestigatorManageComment("15", "尽调员管理员备注"),
    SupervisorManageComment("21", "监管员管理员备注"),
    SupervisorAuditComment("24", "监管员审核备注"),
    RiskManagerManageComment("27", "风控人员管理员备注"),
    RiskManagerAuditComment("30", "风控人员审核备注"),
    RiskManagerContractComment("33", "风控人员上传合同备注"),

    ;

    public String id;
    public String name;

    public static final String RiskManagerManageRight = "AA";
    public static final String RiskManagerRight = "AA_BB";
    public static final String InvestigatorManageRight = "AA_BB_FLAG_EE";
    public static final String InvestigatorRight = "AA_BB_CCDEE_FF";
    public static final String SupervisorManageRight = "AA_BB_CCDGG";
    public static final String SupervisorRight = "AA_BB_CCDGG_HH";
    public static final String SalesmanManageRight = "AA_BB_CCD_";


    EnumFinanceCommentType() {
    }

    EnumFinanceCommentType(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
