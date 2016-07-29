package com.yimei.boot.freemarker.directives;

import com.yimei.boot.freemarker.PDF;
import freemarker.core.Environment;
import freemarker.template.*;

import java.io.IOException;
import java.util.Map;

/**
 * Created by joe on 10/26/14.
 */
public class MarkdownDirective implements TemplateDirectiveModel {

    public void execute(Environment env,
                        Map params, TemplateModel[] loopVars,
                        TemplateDirectiveBody body)
            throws TemplateException, IOException {
        Object ret=params.get("value");
        if(ret==null)
            throw new TemplateModelException("param name doesn't exist");
        String markdown=((SimpleScalar) ret).getAsString();
        if(markdown!=null){
            env.getOut().write(PDF.markdownToHtml(markdown));
        }
    }
}
