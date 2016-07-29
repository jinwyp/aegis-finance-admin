package com.yimei.boot.freemarker.methods;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.util.List;


/**
 * Created by joe on 11/2/14.
 */
public class StaticMethod implements TemplateMethodModelEx {

    @Override
    public Object exec(List arguments) throws TemplateModelException {
        if(arguments.size()!=1)
            throw new TemplateModelException("arguments only take 1 ");
        return ((SimpleScalar) arguments.get(0)).toString();

    }
}
