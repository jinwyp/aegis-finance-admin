package com.yimei.finance.utils;

import com.yimei.finance.exception.BusinessException;
import com.yimei.finance.common.representation.enums.EnumCommonError;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

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

    /**
     * 根据名称,生成拼音首字母
     */
    public static String GeneratePinYinCode(String str, int length, boolean upper) {
        String pinyinName = "";
        char[] nameChar = str.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        if(upper){
            defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        } else {
            defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        }
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        length = nameChar.length < length ? nameChar.length : length;
        for (int i=0; i<length; i++) {
            if (Character.toString(nameChar[i]).matches(
                    "[\\u4E00-\\u9FA5]+")) {
                try {
                    pinyinName += PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0].substring(0, 1);
                } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                    badHanyuPinyinOutputFormatCombination.printStackTrace();
                    throw new BusinessException(EnumCommonError.Admin_System_Error);
                }
            } else {
                if (upper) {
                    pinyinName += Character.toString(nameChar[i]).toUpperCase();
                } else {
                    pinyinName += Character.toString(nameChar[i]).toLowerCase();
                }
            }
        }
        return pinyinName;
    }

}