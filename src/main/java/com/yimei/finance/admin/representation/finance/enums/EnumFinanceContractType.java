package com.yimei.finance.admin.representation.finance.enums;

import java.util.HashMap;
import java.util.Map;

public enum EnumFinanceContractType {
    GoodsMortgage_Upstream_Purchase(1, "仓押上游合同"),
    GoodsMortgage_Downstream_Purchase(2, "仓押下游合同"),
    ;

    public int id;
    public String name;

    EnumFinanceContractType() {}

    EnumFinanceContractType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static String getName(EnumFinanceContractType status) {
        return status.name;
    }

    public static Map<Integer, String> typeList = new HashMap() {
        {
            this.put(Integer.valueOf(1), "仓押上游合同");
            this.put(Integer.valueOf(2), "仓押下游合同");
        }
    };


    public static String getTypeName(int type) {
        return (String)typeList.get(Integer.valueOf(type));
    }


}
