package com.yimei.finance.representation.admin.finance.enums;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "金融单类型")
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
