package com.yimei.boot.freemarker.directives;

import freemarker.core.Environment;
import freemarker.template.*;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by joe on 10/26/14.
 */
public class ExtendDirective implements TemplateDirectiveModel {

    public void execute(Environment env,
                        Map params, TemplateModel[] loopVars,
                        TemplateDirectiveBody body)
            throws TemplateException, IOException {
        // Check if no parameters were given:
        container(env,true);
        if (params.isEmpty()) {
            throw new TemplateModelException(
                    "This directive doesn't allow parameters.");
        }
        if (loopVars.length != 0) {
            throw new TemplateModelException(
                    "This directive doesn't allow loop variables.");
        }
        if (body != null) {
            body.render(new NullWriter());
        }
        String layoutName=getName(params)+".ftl";
        env.include(layoutName, null, true);
    }
    public static class NullWriter extends Writer{

        @Override
        public void write(char[] cbuf, int off, int len) throws IOException {}

        @Override
        public void flush() throws IOException {}

        @Override
        public void close() throws IOException {}
    }
    public static String getName(Map params) throws TemplateModelException {
        Object ret=params.get("name");
        if(ret==null)
            throw new TemplateModelException("param name doesn't exist");
        return ((SimpleScalar) ret).getAsString();
    }
    public static class Container implements TemplateModel{
        protected Map<String, String> value;

        public Container(Map<String, String> value) {
            this.value = value;
        }

        public Map<String, String> getValue() {
            return value;
        }
    }
    public static final String LAYOUT_NAME="RUI_LAYOUT";

    public static Map<String, String> container(Environment env){
        try {
            Object ret = env.getVariable(LAYOUT_NAME);
            if(ret instanceof Container){
                return ((Container) ret).getValue();
            }
        }catch (Exception e){}
        return null;
    }
    public static Map<String, String> container(Environment env, boolean autoCreate){
        Map<String,String> ret=container(env);
        if(autoCreate && ret==null){
            ret=new HashMap<String, String>();
            env.setVariable(LAYOUT_NAME, new Container(ret));
        }
        return ret;
    }
}
