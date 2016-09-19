package com.yimei.finance.representation.common.enums;

public enum EnumCommonString {
    AdminUser_InitPwd("123456"),
    ;
    public String name;

    EnumCommonString() {
    }

    EnumCommonString(String name) {
        this.name = name;
    }

    public static final String LocalDate_Pattern = "yyyy-MM-dd";
    public static final String LocalDateTime_Pattern = "yyyy-MM-dd HH:mm:ss";
}
