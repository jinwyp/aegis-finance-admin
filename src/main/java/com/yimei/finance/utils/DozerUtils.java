package com.yimei.finance.utils;

import org.dozer.DozerBeanMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangqi on 16/4/7.
 */
public class DozerUtils {

    private static DozerBeanMapper mapper = new DozerBeanMapper();

    static {

        List myMappingFiles = new ArrayList();
        myMappingFiles.add("dozer/conf/localtimeMapping.xml");
       // myMappingFiles.add("dozerMappers/tpl/mapMapping.xml");
        mapper.setMappingFiles(myMappingFiles);
    }

    public static <T, U> ArrayList<U> copy(final List<T> source, final Class<U> destType) {

        if (null == source) {
            return null;
        }

        final ArrayList<U> dest = new ArrayList<U>();

        for (T element : source) {
            if (element == null) {
                continue;
            }
            dest.add(mapper.map(element, destType));
        }

        // finally remove all null values if any
        List s1 = new ArrayList();
        s1.add(null);
        dest.removeAll(s1);

        return dest;
    }

    public static <T, U> U copy(final T source, final Class<U> destType) {
        if (null == source) {
            return null;
        }
        return mapper.map(source, destType);
    }


    public static <T, U> U copy(final T source, final U dest) {
        if (null == source) {
            return null;
        }
        mapper.map(source, dest);
        return dest;
    }


}
