package com.yimei.boot.freemarker.methods;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.util.List;

/**
 * Created by fanjun on 16/1/18.
 */
public class RandNumMethod implements TemplateMethodModelEx {
    //随机生成一个范围内整数,param1 最小值,param2最大值
    @Override
    public Object exec(List arguments) throws TemplateModelException {
        if(arguments.size()!=2)
            throw new TemplateModelException("arguments takes at least 2 parameter");
        int param1  = Integer.parseInt(arguments.get(0).toString());
        int param2  = Integer.parseInt(arguments.get(1).toString());

//        范围:(数据类型)(最小值+Math.random()*(最大值-最小值+1))
        return (int)(param1+Math.random()*(param2-param1+1));
    }
}
