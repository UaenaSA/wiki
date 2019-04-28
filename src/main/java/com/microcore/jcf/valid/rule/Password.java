package com.microcore.jcf.valid.rule;


import com.microcore.jcf.valid.rule.base.AbstractRule;
import org.apache.commons.lang3.StringUtils;

/**
 * 密码校验、校验密码强度等？
 *
 * @author leizhenyang
 */
public class Password extends AbstractRule<String>
{

    /**
     * 密码的正则表达式:暂时只支持数字和字符 长度为4到16位
     */
    private static final String PASSWORD_REGEX = "[\\d|a_zA-Z]{4,16}";

    @Override
    public boolean valid(String value)
    {
        if (StringUtils.isBlank(value))
        {
            return true;
        }
        if (!value.matches(PASSWORD_REGEX))
        {
            message = "密码格式不合法";
            return false;
        }
        return true;
    }
}
