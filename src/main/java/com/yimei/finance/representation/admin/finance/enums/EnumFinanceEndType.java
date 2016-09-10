package com.yimei.finance.representation.admin.finance.enums;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "金融单, 流程的几种结束方式")
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
}
