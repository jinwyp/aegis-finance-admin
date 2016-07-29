package com.yimei.boot.ext.mvc.support;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Maps;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by joe on 11/7/14.
 */
public class JsonController {
    public Object json(Errors errors) {
        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put("success", !errors.hasErrors());
        if (errors.hasErrors()) {
            ret.put("errors", errors.getAllErrors());
        }
        return ret;
    }


    public Object json(Map<String, Object> errorList) {
        Map<String, Object> result = Maps.newHashMap();
        if (errorList.size() >= 1) {
            result.put("success", false);
            result.put("errors", errorList);
        } else {
            result.put("success", true);
        }
        return result;
    }

    public Object json(BindResult result) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("success", true);
        //如果有错误的逻辑验证信息,返回格式:{"success":false,"errors":[{"field":"companyStatus","errMsg":"您的公司信息正在审核中,请您耐心等待!"}]}
        if (result.errors.size() >= 1) {
            params.put("success", false);
            params.put("errors", result.errors);
        } else if(result.maps.size()>=1) {
         //没有错误信息，直接把map数据结构响应到客户端
            result.maps.put("success", true);
            return result.maps;
        }
        return params;
    }

    //返回错误信息
    public static class BindResult {

        public String field;
        public String errMsg;
        //绑定错误信息
        @JsonIgnore
        public List<BindResult> errors = new ArrayList<>();
        //返回响应参数
        @JsonIgnore
        public Map<String,Object> maps = Maps.newLinkedHashMap();

        public BindResult() {

        }

        public BindResult(String field, String errMsg) {
            this.field = field;
            this.errMsg = errMsg;
        }

        public void addError(String field, String errMsg) {
            errors.add(new BindResult(field, errMsg));
        }
        public void addAttribute(String key,Object value){
             maps.put(key,value);
        }

    }


}


