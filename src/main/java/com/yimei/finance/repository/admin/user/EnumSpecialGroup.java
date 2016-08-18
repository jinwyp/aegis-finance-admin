package com.yimei.finance.repository.admin.user;

/**
 * Created by liuxinjie on 16/8/15.
 */
public enum EnumSpecialGroup {

    ManageTraderGroup("GROUP00001", "线上交易员管理群"),
    TraderGroup("GROUP00002", "线上交易员群"),
    ManageSalesmanGroup("GROUP00003", "业务员管理群"),
    SalesmanGroup("GROUP00004", "业务员群"),
    ManageInvestigatorGroup("GROUP00005", "尽调员管理群"),
    InvestigatorGroup("GROUP00006", "尽调员群"),
    ManageSupervisorGroup("GROUP00007", "监管员管理群"),
    SupervisorGroup("GROUP00008", "监管员群"),
    ManageRiskGroup("GROUP00009", "风控管理群"),
    RiskGroup("GROUP00010", "风控员群");

    public String id;
    public String name;

    EnumSpecialGroup(){}

    EnumSpecialGroup(String id, String name) {
        this.id = id;
        this.name = name;
    }


}
