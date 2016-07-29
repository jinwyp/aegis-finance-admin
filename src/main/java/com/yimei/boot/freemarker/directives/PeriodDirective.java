package com.yimei.boot.freemarker.directives;

import com.yimei.boot.freemarker.Java8ObjectWrapper;
import com.yimei.boot.freemarker.SuperPeriod;
import freemarker.core.Environment;
import freemarker.template.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.Map;

/**
 * Created by joe on 10/26/14.
 */
public class PeriodDirective implements TemplateDirectiveModel {
    public void execute(Environment env,
                        Map params, TemplateModel[] loopVars,
                        TemplateDirectiveBody body)
            throws TemplateException, IOException {
        Object ret=params.get("value");
        if(ret==null)
            throw new TemplateModelException("param name doesn't exist");

        if(ret instanceof Java8ObjectWrapper.SimpleLocalDateTime){
            LocalDateTime localDateTime=((Java8ObjectWrapper.SimpleLocalDateTime) ret).getLocalDateTime();
            env.getOut().write(formatPeriod(localDateTime, LocalDateTime.now()));
        }
    }
    protected String formatPeriod(Temporal begin, Temporal end){
        SuperPeriod period=SuperPeriod.between(begin,end);
        if(period.getYears()>0)
            return period.getYears()+"年前";
        if(period.getMonths()>0)
            return period.getMonths()+"个月前";
        if(period.getDays()>0)
            return period.getDays()+"天前";
        if(period.getHours()>0)
            return period.getHours()+"小时前";
        if(period.getMinutes()>0)
            return period.getMinutes()+"分钟前";
        return "1分钟内";
    }
}
