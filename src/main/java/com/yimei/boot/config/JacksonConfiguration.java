package com.yimei.boot.config;


import com.fasterxml.jackson.databind.module.SimpleModule;
import com.yimei.boot.jackson.LocalDateDeserializer;
import com.yimei.boot.jackson.LocalDateSerializer;
import com.yimei.boot.jackson.LocalDateTimeDeserializer;
import com.yimei.boot.jackson.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
public class JacksonConfiguration {

    
    @Bean
    public SimpleModule jacksonDateModule() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        simpleModule.addSerializer(LocalDate.class, new LocalDateSerializer());
        simpleModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        simpleModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        return simpleModule;
    }
}
