package com.yimei.finance.representation.admin.user;

import java.util.HashMap;
import java.util.Map;

public enum EnumSpecialGroup {
    SuperAdminGroup("GROUP00000", "系统管理员组"),
    ManageOnlineTraderGroup("GROUP00001", "线上交易员管理组"),
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

    public static EnumSpecialGroup getSonGroup(String id) {
        EnumSpecialGroup group = getGroupById(id);
        if (!group.toString().startsWith("Manage")) return null;
        else {
            String groupName = group.toString();
            return EnumSpecialGroup.valueOf(groupName.substring(6, groupName.length()));
        }
    }

    public static Map<String, EnumSpecialGroup> groups = new HashMap() {
        {
            for (EnumSpecialGroup group : EnumSpecialGroup.values()) {
                this.put(group.id, group);
            }
        }
    };

    public static EnumSpecialGroup getGroupById(String id) {
        return groups.get(id);
    }

}
