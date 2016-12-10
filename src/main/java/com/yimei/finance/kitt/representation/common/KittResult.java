package com.yimei.finance.kitt.representation.common;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hongpf on 16/6/1.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KittResult {
    private boolean success;
    private String error;
    private String errorCode;

    private String message ;
    private Map<String, Object> data;

    public boolean isSuccess() {
        return success;
    }

    public KittResult setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getError() {
        return error;
    }

    public KittResult setError(String error) {
        this.error = error;
        return this;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public KittResult setErrorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public Object getData() {
        return data;
    }

    public KittResult putData(String name, Object object) {
        if(data == null){ data = new HashMap<>(); }
        this.data.put(name, object);
        return this;
    }


    public String getMessage() {
        return message;
    }

    public KittResult setMessage(String message) {
        this.message = message;
        return this  ;
    }

    public static KittResult success(String message){
        return new  KittResult().setSuccess(true).setMessage(message);
    }

    public static KittResult success(){
        return new  KittResult().setSuccess(true);
    }

    public static KittResult error(String message){
        return new  KittResult().setSuccess(false).setError(message);
    }
}
