package com.microcore.jcf.valid.rule;


import com.microcore.jcf.valid.rule.base.AbstractRule;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * IP V4校验
 *
 * @author leizhenyang
 */
public class IPV4 extends AbstractRule<String>
{
    /**
     * IP地址的正确性验证  必须是0-255的数字
     */
    private static final String IP_REGEX = "((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)";
    private static final Pattern PATTERN_REGEX_EMAIL = Pattern.compile(IP_REGEX);

    @Override
    public boolean valid(String value)
    {
        if (!StringUtils.isNotBlank(value))
        {
            return true;
        }
        if (!PATTERN_REGEX_EMAIL.matcher(value).matches())
        {
            message = "IP地址不合法";
            return false;
        }
        return true;
    }
}