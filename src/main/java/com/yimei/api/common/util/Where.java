package com.yimei.api.common.util;


import org.apache.commons.lang3.StringUtils;

/**
 * Created by joe on 2/9/15.
 */
public class Where {
    public static final String script = "<script>";
    public static final String $script = "</script>";
    public static final String where = "<where>";
    public static final String $where = "</where>";
    public static final String and = " and ";
    public static final String or = " or ";
    public static final String limitAndOffset = " limit #{limit} offset #{offset} ";
    public static final String limit = " limit #{limit} ";
    public static final String selectAllFrom = " select * from ";
    public static final String selectCountAllFrom = " select count(*) from ";

    public static String $like(String content) {
        if (content != null && !"".equals(content))
            return "%" + content;
        return null;
    }

    public static String like$(String content) {
        if (content != null && !"".equals(content)) {
            return content + "%";
        }
        return null;
    }

    public static String $like$(String content) {
        if (!StringUtils.isEmpty(content))
            return "%" + content + "%";
        return null;
    }


}
