package com.yimei.finance.entity.admin.finance;

/**
 * Created by liuxinjie on 16/8/22.
 */
public enum EnumFinanceStatus {
    Auditing("审核中"),
    PendingAudit("待审核"),
    AuditPass("审核通过"),
    AuditNotPass("审核不通过"),
    ;

    public String name;

    EnumFinanceStatus() {
    }

    EnumFinanceStatus(String name) {
        this.name = name;
    }

    public static String getName(EnumFinanceStatus status) {
        return status.name;
    }
}
