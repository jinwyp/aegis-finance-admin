package com.yimei.finance.entity.admin.finance;

/**
 * Created by liuxinjie on 16/8/18.
 */
public enum EnumFinanceOrderType {
    MYR("煤易融"),
    MYG("煤易购"),
    MYD("煤易贷"),
    ;
    public String name;

    EnumFinanceOrderType() {}

    EnumFinanceOrderType(String name) {
        this.name = name;
    }
}
