package com.yimei.api.common;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hongpf on 16/6/1.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result {
    private boolean success;
    private String error;
    private String errorCode;

    private String message ;
    private Map<String, Object> data;

    public boolean isSuccess() {
        return success;
    }

    public Result setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getError() {
        return error;
    }

    public Result setError(String error) {
        this.error = error;
        return this;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public Result setErrorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public Object getData() {
        return data;
    }

    public Result putData(String name, Object object) {
        if(data == null){ data = new HashMap<>(); }
        this.data.put(name, object);
        return this;
    }


    public String getMessage() {
        return message;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this  ;
    }

    public static Result success(String message){
        return new Result().setSuccess(true).setMessage(message);
    }

    public static Result success(){
        return new Result().setSuccess(true);
    }

    public static Result error(String message){
        return new Result().setSuccess(false).setError(message);
    }
}
