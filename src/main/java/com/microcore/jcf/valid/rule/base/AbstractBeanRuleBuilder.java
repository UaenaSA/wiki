package com.microcore.jcf.valid.rule.base;


import com.microcore.jcf.valid.rule.bean.RuleEntry;

import java.util.*;

/**
 * 抽象的Bean校验规则构建器
 *
 * @author leizhenyang
 */
public abstract class AbstractBeanRuleBuilder<T> extends AbstractRuleBuilder<T>
{

    /**
     * 校验规则集合
     */
    private Map<String, List<RuleEntry>> rules;


    @Override
    public Map<String, List<RuleEntry>> getRules()
    {
        return rules;
    }

    public void setRules(Map<String, List<RuleEntry>> rules)
    {
        this.rules = rules;
    }

    /**
     * 新增字段校验规则
     *
     * @param field
     * @param validArray
     */
    public void addFieldRule(String field, IValid... validArray)
    {
        if (rules == null)
        {
            rules = new TreeMap<>();
        }
        if (validArray != null && validArray.length > 0)
        {
            List<RuleEntry> ruleEntryList = new ArrayList<>(validArray.length);
            for (IValid rule : validArray)
            {
                ruleEntryList.add(RuleEntry.newInstance(rule));
            }
            if (ruleEntryList.size() > 0)
            {
                // 验证规则排序
                ruleEntryList.sort(Comparator.comparingInt(o -> o.getRule().getOrderNumber()));
            }
            rules.put(field, ruleEntryList);
        }
    }

    /**
     * 删除字段校验规则
     *
     * @param field
     */
    public void removeFieldRule(String field)
    {
        rules.remove(field);
    }

}