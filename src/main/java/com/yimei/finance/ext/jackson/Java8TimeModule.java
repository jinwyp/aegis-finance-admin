package com.yimei.finance.ext.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by hary on 16/3/14.
 */
public class Java8TimeModule extends SimpleModule {
    public Java8TimeModule() {
        this.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        this.addSerializer(LocalDate.class, new LocalDateSerializer());
        this.addSerializer(Date.class, new DateTimeSerializer());

        this.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        this.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        this.addDeserializer(Date.class, new DateTimeDeserializer());


    }

}
