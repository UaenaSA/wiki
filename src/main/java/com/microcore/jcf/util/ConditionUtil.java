package com.microcore.jcf.util;

/**
 * @author leizhenyang
 */
public class ConditionUtil
{
    /**
     * %value% 模糊查询条件
     *
     * @param value
     * @return
     */
    public static String likeWrap(String value)
    {
        return "%" + value + "%";
    }

    /**
     * %value 模糊查询条件
     *
     * @param value
     * @return
     */
    public static String likePrefix(String value)
    {
        return "%" + value;
    }

    /**
     * value% 模糊查询条件
     *
     * @param value
     * @return
     */
    public static String likeSuffix(String value)
    {
        return value + "%";
    }
}
