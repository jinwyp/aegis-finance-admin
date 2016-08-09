package com.yimei.boot.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.yimei.boot.utils.PageUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by liuxinjie on 16/8/9.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result implements Serializable {
    private boolean success;
    private Error error;
    private PageUtils meta;
    private Object data;

    public Result setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public Result setError(Error error) {
        this.error = error;
        return this;
    }

    public Result setMeta(PageUtils meta) {
        this.meta = meta;
        return this;
    }

    public Result setData(Object data) {
        this.data = data;
        return this;
    }

    public static Result success(){
        return new  Result().setSuccess(true);
    }

    public static Result error(Error error){
        return new  Result().setSuccess(false).setError(error);
    }
}
