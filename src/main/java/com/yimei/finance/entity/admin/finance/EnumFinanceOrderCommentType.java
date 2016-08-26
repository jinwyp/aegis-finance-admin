package com.yimei.finance.entity.admin.finance;

/**
 * 金融申请单 备注/批注 类型
 */
public enum EnumFinanceOrderCommentType {

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

    public String RiskManagerManageRight = "01";
    public String RiskManagerRight = "01_01";
    public String InvestigatorManageRight = "01_01_0101";
    public String InvestigatorRight = "01_01_0101_01";
    public String SupervisorManageRight = "01_01_0103";
    public String SupervisorRight = "01_01_0103_01";
    public String SalesmanManageRight = "01_01_01_";


    EnumFinanceOrderCommentType() {
    }

    EnumFinanceOrderCommentType(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
