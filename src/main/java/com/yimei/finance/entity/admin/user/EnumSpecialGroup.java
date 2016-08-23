package com.yimei.finance.entity.admin.user;

/**
 * Created by liuxinjie on 16/8/15.
 */
public enum EnumSpecialGroup {
    SuperAdminGroup("GROUP00000", "超级管理员组"),
    ManageTraderGroup("GROUP00001", "线上交易员管理组"),
    OnlineTraderGroup("GROUP00002", "线上交易员组"),
    ManageSalesmanGroup("GROUP00003", "业务员管理组"),
    SalesmanGroup("GROUP00004", "业务员组"),
    ManageInvestigatorGroup("GROUP00005", "尽调员管理组"),
    InvestigatorGroup("GROUP00006", "尽调员组"),
    ManageSupervisorGroup("GROUP00007", "监管员管理组"),
    SupervisorGroup("GROUP00008", "监管员组"),
    ManageRiskGroup("GROUP00009", "风控管理组"),
    RiskGroup("GROUP00010", "风控员组");

    public String id;
    public String name;

    EnumSpecialGroup(){}

    EnumSpecialGroup(String id, String name) {
        this.id = id;
        this.name = name;
    }


}
