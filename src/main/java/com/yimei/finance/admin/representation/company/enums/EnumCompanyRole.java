package com.yimei.finance.admin.representation.company.enums;

import java.util.ArrayList;
import java.util.List;

public enum EnumCompanyRole {
    RiskManager_Organization(1, "风控线"),
    ;

    public int id;
    public String name;

    EnumCompanyRole() {}

    EnumCompanyRole(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static List<Integer> idList() {
        List<Integer> idList = new ArrayList<>();
        for (EnumCompanyRole role : EnumCompanyRole.values()) {
            idList.add(role.id);
        }
        return idList;
    }
}
