package com.microcore.jcf.valid.rule.base;


/**
 * 抽象的校验规则构建器
 *
 * @author leizhenyang
 */
public abstract class AbstractRuleBuilder<T> implements IRuleBuilder<T>
{

    private boolean hasErrorContinueValid = false;

    @Override
    public boolean hasErrorContinueValid()
    {
        return hasErrorContinueValid;
    }

    public void setHasErrorContinueValid(boolean hasErrorContinueValid)
    {
        this.hasErrorContinueValid = hasErrorContinueValid;
    }
}