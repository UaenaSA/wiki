package com.microcore.jcf.valid.validate.util;


import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xuyuan
 * @date 2018/1/31
 */
public class DataValidateUtils {

    /**
     * 验证数字输入
     * @param str
     * @return
     */
    public static boolean isNumber(String str)
    {
        if(StringUtils.isBlank(str))
        {
            return false;
        }
        String regex = "^[0-9]*$";
        return match(regex,str);
    }

    /**
     * 验证数字输入(0.00-1.00)
     *
     * @param str
     * @return
     */
    public static boolean isNumberBetween0And1(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        }
        String regex = "^0|1|(0.\\d{1,2}$)|1.0|1.00$";
        return match(regex, str);
    }

    /**
     * 验证字母输入
     */
    public static boolean isLetter(String str)
    {
        if (StringUtils.isBlank(str)) {
            return false;
        }
        String regex = "^[A-Za-z]+$";
        return match(regex,str);
    }

    /**
     *
     * @param regex
     *          正则表达式字符串
     * @param str
     *          要匹配的字符串
     * @return
     */
    private static boolean match(String regex,String str)
    {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
