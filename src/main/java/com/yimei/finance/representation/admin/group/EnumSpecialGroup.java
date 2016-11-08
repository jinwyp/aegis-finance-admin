package com.yimei.finance.representation.admin.group;

import java.util.HashMap;
import java.util.Map;

public enum EnumSpecialGroup {
    SuperAdminGroup("SYSTEM00000", "超级管理员组", EnumGroupType.YIMEI_System_Group.id),
    ManageOnlineTraderGroup("GROUP00001", "线上交易员管理组", EnumGroupType.YIMEI_System_Group.id),
    OnlineTraderGroup("GROUP00002", "线上交易员组", EnumGroupType.YIMEI_System_Group.id),

    SystemAdminGroup("GROUP00000", "系统管理员组", EnumGroupType.Business_Company_Group.id),
    ManageSalesmanGroup("GROUP00003", "业务员管理组", EnumGroupType.Business_Company_Group.id),
    SalesmanGroup("GROUP00004", "业务员组", EnumGroupType.Business_Company_Group.id),
    ManageInvestigatorGroup("GROUP00005", "尽调员管理组", EnumGroupType.Business_Company_Group.id),
    InvestigatorGroup("GROUP00006", "尽调员组", EnumGroupType.Business_Company_Group.id),
    ManageSupervisorGroup("GROUP00007", "监管员管理组", EnumGroupType.Business_Company_Group.id),
    SupervisorGroup("GROUP00008", "监管员组", EnumGroupType.Business_Company_Group.id),
    ManageRiskGroup("GROUP00009", "风控管理组", EnumGroupType.Business_Company_Group.id),
    RiskGroup("GROUP00010", "风控员组", EnumGroupType.Business_Company_Group.id),
    ;

    public String id;
    public String name;
    public String type;

    EnumSpecialGroup() {}

    EnumSpecialGroup(String id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
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
