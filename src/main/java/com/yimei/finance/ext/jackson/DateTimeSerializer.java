package com.yimei.finance.ext.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class DateTimeSerializer extends JsonSerializer<Date> {
    protected SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    protected SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        String dateString = formatter.format(value);
        if (dateString.endsWith("00:00:00")) {
            try {
                jgen.writeString(formatter2.format(value));
            } catch (DateTimeParseException e) {
                jgen.writeString(formatter.format(value));
            }
        } else {
            jgen.writeString(formatter.format(value));
        }
    }
}
