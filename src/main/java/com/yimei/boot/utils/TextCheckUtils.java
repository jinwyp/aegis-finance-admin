package com.yimei.boot.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * Created by yimei on 15/7/2.
 */

public class TextCheckUtils {

    /**
     * 只能包含字母，汉字，数字，下划线
     */
    public static boolean doTextCheck(String content) {
        if(StringUtils.isBlank(content)){
            return true;
        }
        String reg = "[a-zA-Z0-9_\\u4e00-\\u9fa5]+$";
        return Pattern.compile(reg).matcher(content).matches();
    }

    /**
     * 只能包含字母，汉字，数字，下划线 百分号 斜杠 反斜杠
     */
    public static boolean doTextCheckTwo(String content) {
        if(StringUtils.isBlank(content)){
            return true;
        }
        String reg = "[|/%\\\\a-zA-Z0-9_\\u4e00-\\u9fa5]+$";
        return Pattern.compile(reg).matcher(content).matches();
    }

    /**
     * 包括键盘上所有的字符
     */
    public static boolean doTextCheckThree(String content) {
        if(StringUtils.isBlank(content)){
            return true;
        }
        String reg = "[ΑαΒβΓγΔδΕεΖζΗηΘθΙι℩Κκ∧λΜμΝνΞξΟοΠπΡρΣσςΤτΥυΦφΧχΨψΩω①②③④⑤⑥⑦⑧⑨⑩μm³㎡℃–「」IVX〔〕＞\uF70Dｖ＂¬µ≈＇｀·＃¥￥＄\\\\≠．± 　\n^p≤≥～~·•——（）‘’`!！@＠#＄$%％^……＾&＆*×＊(（)）_＿\\-+＋－=＝\\[\\]\\{}｛［｝］|｜＼／、【】、;:\"'；：‘“”,，<＜.。．>?/，《。》、？ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ０１２３４５６７８９a-zA-Z0-9_\\u4e00-\\u9fa5]{1,100000}+$";
        return Pattern.compile(reg).matcher(content).matches();
    }

    /***
     * 去掉字符串前后的空间，中间的空格保留
     */
    public static String trimStartEndBackSpace(String str){
        if (StringUtils.isBlank(str) || str.equals("null")) return str;
        str = str.trim();
        while (str.startsWith(" ") || str.startsWith(" ") || str.startsWith("　")) {
            str = str.substring(1, str.length());
        }
        while (str.endsWith(" ") || str.endsWith(" ") || str.endsWith("　")) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    /**
     * 检验手机号是否是正确的手机号
     * @param securephone        手机号码
     * @return                   true: 符合要求, false: 不是正确的手机号
     */
    public static  boolean isValidMobilePhoneNum(String securephone) {
        return (null != securephone) && Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$").matcher(securephone).matches(); // 验证手机号
    }

    /**
     * 检查邮箱格式是否正确
     * @param email              邮箱
     * @return                   true: 符合要求, false: 不是正确的油箱
     */
    public static  boolean isValidEmail(String email) {
        return (null != email) && Pattern.compile("^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$").matcher(email).matches(); // 验证email
    }


    public static final String regEx = "[\\u4e00-\\u9fa5]";
    public static Pattern p = Pattern.compile(regEx);


    //判断字符中是否包含汉字
    public static boolean isChinese(String str) {
        if (null == str) {
            return false;
        }
        return p.matcher(str).find();
    }

}
