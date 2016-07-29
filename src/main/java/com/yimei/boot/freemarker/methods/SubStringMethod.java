package com.yimei.boot.freemarker.methods;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.util.List;

/**
 * Created by xiajing on 15-5-13.
 */

public class SubStringMethod implements TemplateMethodModelEx {
    @Override
    public Object exec(List arguments) throws TemplateModelException {
        if(arguments.size()!=1)
            throw new TemplateModelException("arguments takes at least 1 parameter");
        String value  = arguments.get(0).toString();
        if(value.length() > 7){
            return  value.substring(0,7)+"...";
        }else{
            return  value;
        }

    }


}
