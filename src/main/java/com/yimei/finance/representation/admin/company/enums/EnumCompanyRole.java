package com.yimei.finance.representation.admin.company.enums;

import java.util.HashMap;
import java.util.Map;

public enum EnumCompanyRole {
    Business_Organization(1, "业务组织"),
    Fund_Provider(2, "资金方"),
    ;

    public int id;
    public String name;

    EnumCompanyRole() {}

    EnumCompanyRole(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Map<Integer, String> typeList = new HashMap() {
        {
            this.put(Integer.valueOf(1), "业务组织");
            this.put(Integer.valueOf(2), "资金方");
        }
    };


    public static String getTypeName(int type) {
        return (String)typeList.get(Integer.valueOf(type));
    }

}
