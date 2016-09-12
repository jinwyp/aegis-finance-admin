package com.yimei.finance.representation.common.result;

public enum ErrorMessage {
    User_Company_Not_AuditSuccess(1501, "您的企业信息未认证"),

    ;

    public int code;
    public String error;

    ErrorMessage(){}

    ErrorMessage(int code, String error) {
        this.code = code;
        this.error = error;
    }
}
