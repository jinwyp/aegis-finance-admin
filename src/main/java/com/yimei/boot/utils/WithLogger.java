package com.yimei.boot.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by joe on 11/6/14.
 */
public interface WithLogger {
    public default Logger logger(){
        return LoggerFactory.getLogger(this.getClass());
    }
}
