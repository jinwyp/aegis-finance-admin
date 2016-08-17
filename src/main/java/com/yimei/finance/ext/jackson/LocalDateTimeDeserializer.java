package com.yimei.finance.ext.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Created by joe on 1/14/15.
 */
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    protected static final DateTimeFormatter dateTimeformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    protected static final DateTimeFormatter dateTimeWithHmformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public LocalDateTime deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String str = jp.getText().trim();
        if (str.length() == 0)
            return null;
        try{
            return LocalDateTime.parse(str, dateTimeformatter);
        }catch (DateTimeParseException e){
            return LocalDateTime.parse(str,dateTimeWithHmformatter);
        }
    }

}
