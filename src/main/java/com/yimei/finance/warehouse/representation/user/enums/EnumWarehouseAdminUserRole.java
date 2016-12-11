package com.yimei.finance.warehouse.representation.user.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EnumWarehouseAdminUserRole {
    Port(1, "港口"),
    Supervise(2, "监管"),
    Trafficker(3, "贸易商"),
    Trafficker_Finance(4, "贸易商财务"),
    FundProvider(5, "资金方"),
    FundProvider_Finance(6, "资金方财务"),

    ;

    public int id;
    public String name;

    EnumWarehouseAdminUserRole() {}

    EnumWarehouseAdminUserRole(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static List<Integer> idList() {
        return Arrays.asList(EnumWarehouseAdminUserRole.values()).parallelStream().map(role -> role.id).collect(Collectors.toList());
    }
}
