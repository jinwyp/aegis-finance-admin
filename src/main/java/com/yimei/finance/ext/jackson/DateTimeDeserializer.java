package com.yimei.finance.ext.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by joe on 1/14/15.
 */
public class DateTimeDeserializer extends JsonDeserializer<Date> {
    protected static final SimpleDateFormat dateTimeformatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String str = jp.getText().trim();
        if (str.length() == 0)
            return null;
        try {
            return dateTimeformatter.parse(str);
        } catch (ParseException e) {
            return null;
        }
    }

}
