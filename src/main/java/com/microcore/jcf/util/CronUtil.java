package com.microcore.jcf.util;


import com.microcore.util.time.DateFormatUtil;

import java.util.Date;

/**
 * Quartz的表达式生成工具库
 *
 * @author LiuChunfu
 * @date 2017-07-05
 */
public class CronUtil
{
    public static String createCronExpression(Date date)
    {
        String s = DateFormatUtil.formatDate("yyyy MM dd HH mm ss", date);
        String[] split = s.split(" ");
        StringBuilder builder = new StringBuilder(split.length + 1);
        for (int i = split.length - 1; i >= 0; i--)
        {
            if (i == 0)
            {
                builder.append("? ");
            }
            builder.append(split[i]).append(" ");
        }
        return builder.toString();
    }
}
