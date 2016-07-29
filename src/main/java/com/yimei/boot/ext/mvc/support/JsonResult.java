package com.yimei.boot.ext.mvc.support;

import com.yimei.boot.utils.JsonUtils;
import org.springframework.util.Assert;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by xiangyang on 16/3/30.
 */
public class JsonResult {
    List<Errors> errors = null;
    Map<String, Object> maps = null;

    public JsonResult() {

    }

    public void addError(String field, String errMsg) {
        Assert.hasLength(field);
        Assert.hasLength(errMsg);
        if (this.errors == null) {
            errors = new LinkedList<Errors>();
        }
        errors.add(new Errors(field, errMsg));
    }

    public void addAttribute(String field, Object value) {
        Assert.hasLength(field);
        Assert.notNull(value);
        if (this.maps == null) {
            maps = new LinkedHashMap<String, Object>();
        }
        maps.put(field, value);
    }

    public static class Errors {
        private String field;
        private String errMsg;

        public String getErrMsg() {
            return errMsg;
        }

        public void setErrMsg(String errMsg) {
            this.errMsg = errMsg;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public Errors(String field, String errMsg) {
            this.field = field;
            this.errMsg = errMsg;
        }
    }


    @Override
    public String toString() {
        Map<String, Object> result = new LinkedHashMap<String, Object>();
        if (this.errors != null && this.errors.size() > 0) {
            result.put("success", false);
            result.put("errors", this.errors);
        } else {
            result.put("success", true);
            if (maps != null) {
                result.putAll(this.maps);
            }

        }
        return JsonUtils.toJson(result);
    }
}
