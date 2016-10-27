package com.yimei.finance.utils;

import java.util.Random;

public class CodeUtils {
    public static char[] codeNums = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};

    public static char[] codeChars = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
            'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
            'W', 'X', 'Y', 'Z'
    };

    /**
     * 生成6位随机数字符串
     */
    public static String CreateCode(){
        Random random = new Random();
        String code = "";
        for (int i = 0; i < 6; i++) {
            code += codeNums[random.nextInt(10)];
        }
        return code.toString();
    }

    /**
     * 生成6位随机数字+字母组合字符串
     */
    public static String CreateNumLetterCode() {
        Random random = new Random();
        String code = "";
        for (int i=0; i< 6; i++) {
            code += codeChars[random.nextInt(62)];
        }
        return code;
    }

}