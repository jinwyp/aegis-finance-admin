package com.yimei.finance.entity.admin.finance;

/**
 * Created by liuxinjie on 16/8/22.
 */
public enum EnumFinanceTaskType {
    onlineTraderAddMaterial("线上交易员填写材料"),
    ;

    public String name;

    EnumFinanceTaskType(){}

    EnumFinanceTaskType(String name) {
        this.name = name;
    }
}
