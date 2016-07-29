package com.yimei.boot.utils;

import java.util.Random;

/**
 * Created by liuxinjie on 16/3/30.
 */
public class CodeUtils {
    /**
     * 生成6位随机数字符串
     */
    public static String CreateCode(){
        Random random = new Random();
        String code = "";
        for (int i = 0; i < 6; i++) {
            code += String.valueOf(random.nextInt(10));
        }
        return code;
    }

}
