package com.yimei.finance.admin.representation.finance.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EnumFinanceEndType {
    EndByOnlineTrader("线上交易员审核不通过"),
    EndBySalesman("业务员审核不通过"),
    EndByRiskManager("风控人员审核不通过"),
    completeWorkFlowSuccess("审核通过,流程完成"),

    ;

    private String name;

    EnumFinanceEndType() {
    }

    EnumFinanceEndType(String name) {
        this.name = name;
    }

    public static List<String> getAllEndTypeList() {
        return Arrays.asList(EnumFinanceEndType.values()).parallelStream().map(type -> type.toString()).collect(Collectors.toList());
    }
}
