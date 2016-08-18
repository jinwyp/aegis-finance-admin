package com.yimei.finance.entity.admin.finance;

/**
 * 金融申请单,指派人事件
 */
public enum EnumFinanceAssignEvent {
    assignOnlineTrader("分配线上交易员"),
    assignSalesman("分配业务员"),
    assignInvestigator("分配尽调员"),
    assignSupervisor("分配监管员"),
    assignRiskManager("分配风控人员"),
    ;

    public String name;

    EnumFinanceAssignEvent(){};

    EnumFinanceAssignEvent(String name) {
        this.name = name;
    }
}
