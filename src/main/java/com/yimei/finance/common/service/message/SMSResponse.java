package com.yimei.finance.common.service.message;

/**
 * Created by joe on 11/18/14.
 */
public class SMSResponse {
    private boolean isSuccess;
    private String code;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
