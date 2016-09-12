package com.yimei.finance.representation.common.result;

public enum ErrorMessage {
    User_Company_Not_AuditSuccess(1501, "您的企业信息未认证"),

    User_Finance_Times(1601, "您今天已经申请过了,请明天再申请"),
    ;

    public int code;
    public String error;

    ErrorMessage(){}

    ErrorMessage(int code, String error) {
        this.code = code;
        this.error = error;
    }
}
