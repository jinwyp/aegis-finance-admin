package com.yimei.finance.entity.admin.finance;

/**
 * Created by liuxinjie on 16/8/22.
 */
public enum EnumFinanceStatus {

    WaitForAudit(2, "待审核"),
    Auditing(4, "审核中"),
    SupplyMaterial(6, "补充材料"),
    AuditPass(8, "审核通过"),
    AuditNotPass(10, "审核不通过"),
    ;

    public int id;
    public String name;

    EnumFinanceStatus() {
    }

    EnumFinanceStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
