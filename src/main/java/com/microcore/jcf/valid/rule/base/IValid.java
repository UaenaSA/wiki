package com.microcore.jcf.valid.rule.base;

/**
 * 验证接口
 *
 * @author leizhenyang
 */
public interface IValid<T>
{

    /**
     * 校验
     *
     * @param value
     * @return
     */
    boolean valid(T value) throws Exception;

    /**
     * 获取消息
     *
     * @return
     */
    Object getMessage();

    /**
     * 验证顺序的序号
     *
     * @return 0, 1, 2, ...
     */
    int getOrderNumber();

}
