package com.yimei.boot.ext.redis;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.core.serializer.support.SerializingConverter;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * Created by hongpf on 15/4/14.
 */
public class AegisRedisSerializer implements RedisSerializer<Object> {
    private Converter<Object, byte[]> serializer = new SerializingConverter();
    private Converter<byte[], Object> deserializer = new DeserializingConverter();


    public Object deserialize(byte[] bytes) {
        if(bytes ==null || bytes.length==0){
            return null ;
        }
        try {
            return deserializer.convert(bytes);
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
            return serializer.convert(object);
        } catch (Exception ex) {
            throw new SerializationException("Cannot serialize", ex);
        }
    }
}