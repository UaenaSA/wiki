package com.microcore.jcf.valid.rule;


import com.microcore.jcf.exception.ServiceExceptionUtil;
import com.microcore.jcf.valid.rule.base.AbstractRule;

import java.util.Set;

/**
 * 字符串枚举，主要用于char类型的字段检查
 *
 *
 * @author leizhenyang
 */
public class Contains<T> extends AbstractRule<T>
{

    private Set<T> values;

    public Contains(Set values)
    {
        if (values == null || values.size() == 0)
        {
            ServiceExceptionUtil.getErrorServiceException("校验集合不可为空");
        }

        this.values = values;
    }

    @Override
    public boolean valid(T value) throws Exception
    {
        if (value == null)
        {
            return true;
        }
        if (values.contains(value))
        {
            return true;
        }
        message = "值不合法";
        return false;
    }
}
