package com.yimei.finance.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

/**
 * Created by liuxinjie on 16/8/18.
 */
public class HibernateAwareObjectMapper extends ObjectMapper {
    private static final long serialVersionUID = 1L;

    public HibernateAwareObjectMapper() {
        registerModule(new Hibernate4Module());
    }
}
