package com.microcore.jcf.util;


import com.microcore.util.time.DateFormatUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Date;

/**
 * 专门用户处于Core模块中对于时间的工具类，核心方法调用Util模块中的方法。
 *
 * @author LiuChunfu
 * @date 2017-05-12
 */
public class CoreDateUtil
{

    private static final Logger log = LoggerFactory.getLogger(CoreDateUtil.class);

    /**
     * 格式化形如：2017-05-10T17:53:40的代码，如果转换失败返回null；
     *
     * @param dateStr 时间字符串
     * @return null or 转换后的Date
     */
    public static Date checkDateStr(String dateStr)
    {
        if (StringUtils.isBlank(dateStr))
        {
                return null;
        }
        try
        {
            return DateFormatUtil.pareDate(DateFormatUtil.PATTERN_ISO_ON_SECOND, dateStr);
        }
        catch (ParseException e)
        {
            log.error("时间字符串{}转换失败，原因是:{}", dateStr, e.getMessage());
            return null;
        }
    }
}
