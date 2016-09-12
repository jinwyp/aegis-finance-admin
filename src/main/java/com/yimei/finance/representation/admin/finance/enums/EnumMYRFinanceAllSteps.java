package com.yimei.finance.representation.admin.finance.enums;

public enum EnumMYRFinanceAllSteps {
    Client_Apply(2, "客户发起申请"),
    Wait_Assign_OnlineTrader(4, "待分配线上交易员"),
    Wait_Audit_OnlineTrader(6, "待线上交易员审核"),
    Audit_NotPass_OnlineTrader(8, "线上交易员审核不通过"),
    Wait_Assign_Salesman(10, "待分配业务员"),
    Wait_Audit_Salesman(12, "待业务员审核"),
    Audit_NotPass_Salesman(14, "业务员审核不通过"),
    Wait_Assign_Investigator(16, "待分配尽调员"),
    Wait_Audit_Investigator(18, "待尽调员审核"),
    Wait_SupplyMaterial_Salesman(20, "待业务员补充尽调材料"),
    Audit_NotPass_Investigator(22, "尽调员审核不通过"),
    Wait_Assign_RiskManager(24, "待分配风控人员"),
    Wait_SupplyRiskMaterial_Salesman(26, "待业务员补充风控材料"),
    Audit_NotPass_RiskManager(28, "风控人员审核不通过"),
    Complete_WorkFlow_Success(30, "审核通过,流程完成"),
    ;

    public int id;
    public String name;

    EnumMYRFinanceAllSteps() {
    }

    EnumMYRFinanceAllSteps(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
