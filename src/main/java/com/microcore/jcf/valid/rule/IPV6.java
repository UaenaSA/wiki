package com.microcore.jcf.valid.rule;


import com.microcore.jcf.valid.rule.base.AbstractRule;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * IP V6校验
 *
 * @author jiangjianjun
 */
public class IPV6 extends AbstractRule<String>
{
    /**
     * IP地址的正确性验证  必须是0-255的数字
     */
    private static final String IP_REGEX = "([\\da-fA-F]{1,4}:){7}[\\da-fA-F]{1,4}";
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