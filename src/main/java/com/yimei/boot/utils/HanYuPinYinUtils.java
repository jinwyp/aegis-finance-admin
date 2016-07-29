package com.yimei.boot.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * Created by liuxinjie on 15/9/2.
 */
public class HanYuPinYinUtils {

    /**
     * 根据汉字获取对应拼音字符串
     * 如果name中包含汉字，汉字转换成英文，如果是英文，数字等其它，保持不变
     * @param name        汉语字符串
     * @param upper       true  大写拼音字符串  false 小写拼音字符串
     * @return            拼音字符串
     */
    public static String HanYuToPinYinMethod(String name, boolean upper) throws BadHanyuPinyinOutputFormatCombination {
        String pinyinName = "";
        char[] nameChar = name.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        if(upper){
            defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        } else {
            defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        }
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i=0; i<nameChar.length; i++) {
            if (Character.toString(nameChar[i]).matches(
                    "[\\u4E00-\\u9FA5]+")) {
                pinyinName += PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0];
            } else {
                pinyinName += Character.toString(nameChar[i]);
            }
        }
        return pinyinName;
    }


    /**
     * 汉语转换英文，如果是汉语，取拼音首字母， 是其它，不变
     * @param name               汉语字符串
     * @param upper              是否大写
     * @return
     * @throws BadHanyuPinyinOutputFormatCombination
     */
    public static String HanYuToPinYinFirstLetter(String name, boolean upper) throws BadHanyuPinyinOutputFormatCombination {
        String pinyinName = "";
        char[] nameChar = name.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        if(upper){
            defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        } else {
            defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        }
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i=0; i<nameChar.length; i++) {
            if (Character.toString(nameChar[i]).matches(
                    "[\\u4E00-\\u9FA5]+")) {
                pinyinName += PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0].charAt(0);
            } else {
                pinyinName += Character.toString(nameChar[i]);
            }
        }
        return pinyinName;
    }

}


