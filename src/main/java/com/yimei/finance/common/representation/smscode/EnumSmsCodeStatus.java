package com.yimei.finance.common.representation.smscode;

public enum EnumSmsCodeStatus {
    UNVerified(1),              //未验证
    Verified(3),                //已验证
    ;

    public int id;

    EnumSmsCodeStatus(int id) {
        this.id = id;
    }
}
