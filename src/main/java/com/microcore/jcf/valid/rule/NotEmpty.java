package com.microcore.jcf.valid.rule;


import com.microcore.jcf.valid.rule.base.AbstractRule;
import com.microcore.jcf.valid.validate.util.ValidUtil;

/**
 * 空字符串校验
 *
 * @author leizhenyang
 */
public class NotEmpty extends AbstractRule<String>
{
    @Override
    public boolean valid(String value)
    {
        if (ValidUtil.isEmpty(value))
        {
            message = "不可为空";
            return false;
        }
        return true;

    }

    @Override
    public int getOrderNumber()
    {
        return OrderNumber.FIRST;
    }
}