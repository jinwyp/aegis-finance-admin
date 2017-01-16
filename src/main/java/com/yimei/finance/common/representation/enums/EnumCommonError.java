package com.yimei.finance.common.representation.enums;

public enum EnumCommonError {
    请您登录,
    此文件不存在,
    上传文件大小不能超过30M,
    传入参数错误,
    ;

    public static final String Admin_System_Error = "系统出错,请联系技术人员";

    public static final String NotSupported_File_Type(String suffix) {
        return suffix + " 类型文件不支持";
    }
}
