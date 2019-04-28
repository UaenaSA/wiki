package com.microcore.jcf.valid.validate.base;

/**
 * 验证器接口
 *
 * @author leizhenyang
 */
public interface IValidator
{

    /**
     * 验证泛型实体
     *
     * @param t
     * @param <T>
     * @return 错误信息
     */
    <T> IValidateAcceptor validate(T t) throws Exception;
}