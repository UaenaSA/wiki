package com.microcore.jcf.valid.rule;

import com.microcore.jcf.exception.ServiceExceptionUtil;
import com.microcore.jcf.valid.rule.base.AbstractRule;
import com.microcore.jcf.valid.rule.support.BoundaryValueSupport;
import com.microcore.jcf.valid.validate.util.ValidUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 数字范围校验
 *
 * @author leizhenyang
 */
public class Range extends AbstractRule<Number>
{
    private Map<Class, IRangeValidator> numberValidators = new HashMap<>();

    private BoundaryValueSupport boundaryValueSupport;

    {
        // 初始化校验类型
        numberValidators.put(Integer.class, new IntegerValidator());
        numberValidators.put(Double.class, new DoubleValidator());
        numberValidators.put(Float.class, new FloatValidator());
        numberValidators.put(Long.class, new LongValidator());
        numberValidators.put(Short.class, new ShortValidator());
    }

    public Range(Number max)
    {
        if (max == null)
        {
            throw ServiceExceptionUtil.getErrorServiceException("Range验证时max均不可为空");
        }
        boundaryValueSupport = new BoundaryValueSupport(max.longValue());
    }

    public Range(Number min, Number max)
    {
        if (ValidUtil.isNull(min) || ValidUtil.isNull(max))
        {
            throw ServiceExceptionUtil.getErrorServiceException("Range验证时min和max均不可为空");
        }
        boundaryValueSupport = new BoundaryValueSupport(min.longValue(), max.longValue());
    }

    /**
     * 根据参数类型会转换min 、max的值
     *
     * @param value
     * @return
     */
    @Override
    public boolean valid(Number value)
    {
        if (ValidUtil.isNull(value))
        {
            return true;
        }
        IRangeValidator numberValidator = numberValidators.get(value.getClass());
        if (numberValidator == null)
        {
            message = "不支持的校验类型，当前参数类型：" + value.getClass();
            return false;
        }
        boolean flag = numberValidator.valid(value);
        if (!flag)
        {
            message = "必须在[" + boundaryValueSupport.getMin() + "," + boundaryValueSupport.getMax() + "]之间";
        }
        return flag;
    }

    /**
     * 用于实现各类类型校验接口
     */
    interface IRangeValidator
    {
        boolean valid(Number value);
    }

    class IntegerValidator implements IRangeValidator
    {
        @Override
        public boolean valid(Number value)
        {
            return ValidUtil.between(boundaryValueSupport.getMin(), boundaryValueSupport.getMax(), value.intValue());
        }
    }

    class DoubleValidator implements IRangeValidator
    {
        @Override
        public boolean valid(Number value)
        {
            return ValidUtil.between(boundaryValueSupport.getMin(), boundaryValueSupport.getMax(), value.doubleValue());
        }
    }

    class FloatValidator implements IRangeValidator
    {
        @Override
        public boolean valid(Number value)
        {
            return ValidUtil.between(boundaryValueSupport.getMin(), boundaryValueSupport.getMax(), value.floatValue());
        }
    }

    class LongValidator implements IRangeValidator
    {
        @Override
        public boolean valid(Number value)
        {
            return ValidUtil.between(boundaryValueSupport.getMin(), boundaryValueSupport.getMax(), value.longValue());
        }
    }

    class ShortValidator implements IRangeValidator
    {
        @Override
        public boolean valid(Number value)
        {
            return ValidUtil.between(boundaryValueSupport.getMin(), boundaryValueSupport.getMax(), value.shortValue());
        }
    }
}
