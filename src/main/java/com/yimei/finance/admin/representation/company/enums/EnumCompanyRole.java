package com.yimei.finance.admin.representation.company.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        return Arrays.asList(EnumCompanyRole.values()).parallelStream().map(role -> role.id).collect(Collectors.toList());
    }
}
