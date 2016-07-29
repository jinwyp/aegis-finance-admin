package com.yimei.boot.freemarker.methods;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.util.List;

/**
 * Created by fanjun on 14-11-17.
 */
public class FileStorageMethod implements TemplateMethodModelEx {
    @Override
    public Object exec(List arguments) throws TemplateModelException {
        if(arguments.size()!=1)
            throw new TemplateModelException("arguments only take 1 ");
        return ((SimpleScalar)arguments.get(0)).toString();
    }
}
