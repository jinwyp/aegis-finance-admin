package com.yimei.boot.freemarker;

/**
 * Created by hary on 16/3/14.
 */

import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateDateModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by joe on 1/13/15.
 */
public class Java8ObjectWrapper extends DefaultObjectWrapper {
    public static class SimpleLocalDate implements TemplateDateModel {
        protected LocalDate localDate;

        public SimpleLocalDate(LocalDate localDate) {
            this.localDate = localDate;
        }
        public LocalDate getLocalDate() {
            return localDate;
        }

        @Override
        public Date getAsDate() throws TemplateModelException {
            return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }

        @Override
        public int getDateType() {
            return TemplateDateModel.DATE;
        }
    }
    public static class SimpleLocalDateTime implements TemplateDateModel{
        protected LocalDateTime localDateTime;

        public SimpleLocalDateTime(LocalDateTime localDateTime) {
            this.localDateTime = localDateTime;
        }

        public LocalDateTime getLocalDateTime() {
            return localDateTime;
        }

        @Override
        public Date getAsDate() throws TemplateModelException {
            return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        }

        @Override
        public int getDateType() {
            return TemplateDateModel.DATETIME;
        }
    }
    @Override
    public TemplateModel wrap(Object obj) throws TemplateModelException {
        if(obj!=null){
            if(obj instanceof LocalDate){
                return new SimpleLocalDate((LocalDate)obj);
            }
            if(obj instanceof LocalDateTime){
                return new SimpleLocalDateTime((LocalDateTime)obj);
            }
        }
        return super.wrap(obj);
    }
}

