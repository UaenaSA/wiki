package com.microcore.jcf.valid.rule.base;


/**
 * 校验规则抽象类，IValid接口
 *
 * @author leizhenyang
 */
public abstract class AbstractRule<T> extends BaseRule implements IValid<T>
{
    /**
     * 默认OrderNumber.OtherWise
     *
     * @return
     */
    @Override
    public int getOrderNumber()
    {
        // 默认排序为1
        return OrderNumber.OTHER_WISE;
    }

    protected class OrderNumber
    {
        public static final int FIRST = 0;
        public static final int SECOND = 1;
        public static final int OTHER_WISE = 10;
    }
}