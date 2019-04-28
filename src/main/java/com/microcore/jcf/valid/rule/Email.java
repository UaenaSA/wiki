package com.microcore.jcf.valid.rule;


import com.microcore.jcf.valid.rule.base.AbstractRule;
import com.microcore.jcf.valid.validate.util.ValidUtil;

import java.util.regex.Pattern;

/**
 * 邮箱校验
 *
 * @author leizhenyang
 */
public class Email extends AbstractRule<String>
{
    /**
     * 正则：邮箱, 有效字符(不支持中文), 且中间必须有@，后半部分必须有.
     */
    private static final String REGEX_EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    private static final Pattern PATTERN_REGEX_EMAIL = Pattern.compile(REGEX_EMAIL);

    @Override
    public boolean valid(String value)
    {
        if (ValidUtil.isBlank(value))
        {
            return true;
        }
        if (!PATTERN_REGEX_EMAIL.matcher(value).matches())
        {
            message = "邮箱格式不合法";
            return false;
        }
        return true;
    }
}
