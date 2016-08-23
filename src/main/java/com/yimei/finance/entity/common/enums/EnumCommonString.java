package com.yimei.finance.entity.common.enums;

/**
 * Created by liuxinjie on 16/8/23.
 */
public enum EnumCommonString {
    AdminUser_InitPwd("123456"),
    ;
    public String name;

    EnumCommonString() {
    }

    EnumCommonString(String name) {
        this.name = name;
    }
}
