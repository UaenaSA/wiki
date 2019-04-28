package com.microcore.jcf.valid.validate.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author leizhenyang
 */
public class ValidUtil
{
    /**
     * 对象为空
     *
     * @param value
     * @return
     */
    public static boolean isNull(Object value)
    {
        return value == null;
    }

    /**
     * 字符串为空
     *
     * @param value
     * @return
     */
    public static boolean isEmpty(String value)
    {
        return StringUtils.isEmpty(value);
    }

    /**
     * 字符串为空或空字符串
     *
     * @param value
     * @return
     */
    public static boolean isBlank(String value)
    {

        return StringUtils.isBlank(value);
    }

    /**
     * min <= value <= max
     *
     * @param value
     * @return
     */
    public static boolean between(long min, long max, Number value)
    {
        return min <= value.longValue() && value.longValue() <= max;
    }
}
