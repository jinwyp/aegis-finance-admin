package com.yimei.boot.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


/**
 * Created by zhangbolun on 14/12/18.
 */
public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {
    protected  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @Override
    public LocalDate deserialize(JsonParser jp, DeserializationContext ctxt)throws IOException, JsonProcessingException {
        String string = jp.getText().trim();
        if(string.length() == 0)
            return null;
        return LocalDate.parse(string, formatter);
    }
}





