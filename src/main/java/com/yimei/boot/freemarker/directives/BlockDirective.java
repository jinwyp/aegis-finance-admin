package com.yimei.boot.freemarker.directives;

import freemarker.core.Environment;
import freemarker.template.*;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * Created by joe on 10/26/14.
 */
public class BlockDirective implements TemplateDirectiveModel {

    public void execute(Environment env,
                        Map params, TemplateModel[] loopVars,
                        TemplateDirectiveBody body)
            throws TemplateException, IOException {
        if (loopVars.length != 0) {
            throw new TemplateModelException(
                    "This directive doesn't allow loop variables.");
        }
        Map<String, String> container= com.yimei.boot.freemarker.directives.ExtendDirective.container(env);
        if(container==null)
            throw new TemplateModelException("block must work with extend");
        String name=ExtendDirective.getName(params);
        if(container.containsKey(name)){
            String content=container.get(name);
            if(content!=null)
                env.getOut().write(content);
        }else{
            String bodyResult =null;
            if(body!=null) {
                StringWriter out = new StringWriter();
                body.render(out);
                bodyResult = out.toString();
                env.getOut().write(bodyResult);
            }
            container.put(name, bodyResult);
        }
    }

}
