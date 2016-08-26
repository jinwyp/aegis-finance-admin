package com.yimei.finance.entity.admin.finance;

/**
 * 金融申请单 备注/批注 类型
 */
public enum EnumFinanceCommentType {

    OnlineTraderAuditComment("AA_AA_AA_FLAG_AA_AA_AA_01", "线上交易员审核备注"),
    OnlineTraderManageComment("AA_AA_AA_FLAG_AA_AA_AA_01", "线上交易员管理员备注"),
    SalesmanAuditComment("AA_AA_AA_DD_FLAG_AA_01", "业务员审核备注"),
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
    public static final String RiskManagerRight = "AA_AA";
    public static final String InvestigatorManageRight = "AA_AA_AA_AA_FLAG";
    public static final String InvestigatorRight = "AA_AA_AA_AA_FLAG_AA";
    public static final String SupervisorManageRight = "AA_AA_AA_DD_FLAG";
    public static final String SupervisorRight = "AA_AA_AA_DD_FLAG_AA";
    public static final String SalesmanManageRight = "AA_AA_AA_FLAG_AA";
    public static final String SalesmanRight = "AA_AA_AA_FLAG_AA_AA";
    public static final String OnlineTraderManageRight = "AA_AA_AA_FLAG_AA_AA_AA";
    public static final String OnlineTraderRight = "AA_AA_AA_FLAG_AA_AA_AA";


    EnumFinanceCommentType() {
    }

    EnumFinanceCommentType(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
