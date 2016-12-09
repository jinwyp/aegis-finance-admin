package com.yimei.finance.warehouse.representation.company.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EnumWarehouseAdminCompanyRole {
    Port(1, "港口"),
    Supervise(2, "监管"),
    Trafficker(3, "贸易商"),
    FundProvider(4, "资金方"),
    ;

    public int id;
    public String name;

    EnumWarehouseAdminCompanyRole() {}

    EnumWarehouseAdminCompanyRole(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static List<Integer> idList() {
        return Arrays.asList(EnumWarehouseAdminCompanyRole.values()).parallelStream().map(role -> role.id).collect(Collectors.toList());
    }

}
