package com.microcore.jcf.valid.rule.bean;


import com.microcore.jcf.valid.rule.base.IValid;

/**
 * 校验规则实体
 *
 * @author leizhenyang
 */
public class RuleEntry
{
    private IValid rule;

    public IValid getRule()
    {
        return rule;
    }

    public void setRule(IValid rule)
    {
        this.rule = rule;
    }

    private RuleEntry()
    {

    }

    public static RuleEntry newInstance(IValid rule)
    {
        RuleEntry entry = new RuleEntry();
        entry.setRule(rule);
        return entry;
    }

}
