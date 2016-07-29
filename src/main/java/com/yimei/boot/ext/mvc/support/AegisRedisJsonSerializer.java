package com.yimei.boot.ext.mvc.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * Created by hary on 16/4/5.
 */
public class AegisRedisJsonSerializer {

    private ObjectMapper objectMapper = new ObjectMapper();

    public Object deserialize(byte[] bytes) {
        if(bytes ==null || bytes.length==0){
            return null ;
        }
        try {
            return objectMapper.readValue(bytes, Session.class);
        } catch (Exception ex) {
            //ex.printStackTrace();
            return null ;
            // throw new SerializationException("Cannot deserialize", ex);
        }
    }

    public byte[] serialize(Object object) {
        if (object == null) {
            return  new byte[0] ;
        }
        try {
            return objectMapper.writeValueAsString(object).getBytes();
        } catch (Exception ex) {
            throw new SerializationException("Cannot serialize", ex);
        }
    }
}
