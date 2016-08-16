package com.yimei.finance.entity.admin;

/**
 * Created by liuxinjie on 16/8/15.
 */
public enum EnumGroupId {

    ManageTraderGroup("GROUP001", "线上交易员管理群"),
    TraderGroup("GROUP002", "线上交易员群"),
    ManageSalesmanGroup("GROUP003", "业务员管理群"),
    SalesmanGroup("GROUP004", "业务员群"),
    ManageInvestigatorGroup("GROUP005", "尽调员管理群"),
    InvestigatorGroup("GROUP006", "尽调员群"),
    ManageSupervisorGroup("GROUP007", "监管员管理群"),
    SupervisorGroup("GROUP008", "监管员群"),
    ManageRiskGroup("GROUP009", "风控管理群"),
    RiskGroup("GROUP010", "风控群");

    public String id;
    public String name;

    EnumGroupId(String id, String name) {
        this.id = id;
        this.name = name;
    }
    EnumGroupId(){}
}
