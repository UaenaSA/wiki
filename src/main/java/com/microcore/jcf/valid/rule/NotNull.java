package com.microcore.jcf.valid.rule;


import com.microcore.jcf.valid.rule.base.AbstractRule;
import com.microcore.jcf.valid.validate.util.ValidUtil;

/**
 * 空指针校验
 *
 * @author leizhenyang
 */
public class NotNull extends AbstractRule<Object>
{
    @Override
    public boolean valid(Object value)
    {
        if (ValidUtil.isNull(value))
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