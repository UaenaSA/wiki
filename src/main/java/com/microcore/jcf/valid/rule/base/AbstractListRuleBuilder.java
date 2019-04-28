package com.microcore.jcf.valid.rule.base;


import com.microcore.jcf.valid.rule.VList;
import com.microcore.jcf.valid.rule.bean.RuleEntry;
import com.microcore.jcf.valid.validate.util.ValidUtil;

import java.util.List;

/**
 * 抽象的List校验规则构建器
 *
 * @author leizhenyang
 */
public abstract class AbstractListRuleBuilder<T extends List> extends AbstractRuleBuilder<T>
{

    /**
     * 校验规则集合
     */
    private RuleEntry rule;

    @Override
    public RuleEntry getRules()
    {
        return rule;
    }

    public void setRule(RuleEntry rule)
    {
        this.rule = rule;
    }

    /**
     * 创建泛型集合的校验规则
     *
     * @param rule
     */
    public void makeGenericListRule(VList rule)
    {
        if (!ValidUtil.isNull(rule))
        {
            setRule(RuleEntry.newInstance(rule));
        }
    }

}