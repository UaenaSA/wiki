package com.microcore.jcf.valid.rule;


import com.microcore.jcf.valid.rule.base.AbstractRule;
import com.microcore.jcf.valid.validate.util.ValidUtil;

/**
 * 正则表达式校验
 *
 * @author leizhenyang
 */
public class Regex extends AbstractRule<String>
{

    String regex;

    public Regex(String regex)
    {
        this.regex = regex;
    }

    @Override
    public boolean valid(String value)
    {
        if (ValidUtil.isBlank(value))
        {
            return true;
        }
        if (!value.matches(regex))
        {
            message = "格式不合法";
            return false;
        }
        return true;
    }

}
