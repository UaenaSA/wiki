package com.microcore.jcf.valid.rule.base;

/**
 * 规则构建器
 *
 * @param <T>
 */
public interface IRuleBuilder<T>
{
    /**
     * 构建验证器
     *
     * @return
     */
    void build(T entity);

    /**
     * 获取所有验证规则
     *
     * @return
     */
    Object getRules();

    /**
     * 多验证规则时，验证失败后是否继续验证
     *
     * @return true 继续验证 false 不再继续验证
     */
    boolean hasErrorContinueValid();
}
