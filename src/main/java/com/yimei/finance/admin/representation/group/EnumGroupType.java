package com.yimei.finance.admin.representation.group;

public enum EnumGroupType {
    YIMEI_System_Group("0", "易煤网管理员负责的组"),
    Business_Company_Group("1", "业务公司组"),
    Company_User_Group("2", "公司用户组"),
    ;

    public String id;
    public String name;

    EnumGroupType() {}

    EnumGroupType(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
