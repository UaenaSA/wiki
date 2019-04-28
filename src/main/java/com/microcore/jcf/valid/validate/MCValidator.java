package com.microcore.jcf.valid.validate;


import com.microcore.jcf.exception.ServiceExceptionUtil;
import com.microcore.jcf.util.SpringHelper;
import com.microcore.jcf.valid.rule.base.IRuleBuilder;
import com.microcore.jcf.valid.rule.base.IValid;
import com.microcore.jcf.valid.rule.bean.RuleEntry;
import com.microcore.jcf.valid.validate.base.IValidateAcceptor;
import com.microcore.jcf.valid.validate.base.IValidator;
import com.microcore.jcf.valid.validate.util.ValidUtil;
import com.microcore.util.reflect.ReflectionUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 验证器
 *
 * @author leizhenyang
 */
@Component("mcValidator")
@Scope("prototype")
public class MCValidator implements IValidator
{
    /**
     * 静态获取MCValidator,若从spring context中获取不到，则实例化MCValidator并返回
     *
     * @return
     */
    public static MCValidator getValidator()
    {
        MCValidator mcValidator;
        try
        {
            mcValidator = SpringHelper.getBean("mcValidator", MCValidator.class);
        }
        catch (Exception e)
        {
            mcValidator = new MCValidator();
        }
        return mcValidator;
    }

    protected IRuleBuilder builder;


    public void setBuilder(IRuleBuilder builder)
    {
        this.builder = builder;
    }


    /**
     * 校验接口实现
     *
     * @param t
     * @param <T>
     * @return
     */
    @Override
    public final <T> IValidateAcceptor validate(T t) throws Exception
    {
        if (ValidUtil.isNull(t))
        {
            throw ServiceExceptionUtil.getErrorServiceException("校验参数不可为空");
        }
        return validateForBean(t);
    }

    /**
     * VBean调用的校验方法
     *
     * @param t
     * @param <T>
     * @return
     */
    public final <T> IValidateAcceptor validateForBean(T t) throws Exception
    {
        builder.build(t);
        if (t instanceof List)
        {
            return validList(t);
        }
        else
        {
            return validBean(t);
        }
    }

    /**
     * 验证List
     *
     * @param t
     * @return
     */
    private <T> IValidateAcceptor validList(T t) throws Exception
    {
        ListValidateAcceptor acceptor = new ListValidateAcceptor<>();
        RuleEntry rule = (RuleEntry) builder.getRules();
        if (!ValidUtil.isNull(rule))
        {
            IValid valid = rule.getRule();
            boolean flag = valid.valid(t); // 校验
            if (!flag)
            {
                Object msg = valid.getMessage();
                acceptor.add(msg);
            }
        }
        return acceptor;
    }

    /**
     * 验证Bean
     *
     * @param t
      * @param <T>
     * @return
     */
    private <T> BeanValidateAcceptor validBean(T t)
    {
        BeanValidateAcceptor acceptor = new BeanValidateAcceptor<>();
        Map<String, List<RuleEntry>> rules = (Map<String, List<RuleEntry>>) builder.getRules();
        if (!ValidUtil.isNull(rules))
        {
            for (Map.Entry<String, List<RuleEntry>> entry : rules.entrySet())
            {
                String fieldName = entry.getKey();
                List<RuleEntry> ruleList = entry.getValue();
                for (RuleEntry ruleEntry : ruleList)
                {
                    IValid valid = ruleEntry.getRule();
                    Object value = ReflectionUtil.getFieldValue(t, fieldName);
                    boolean flag = true;
                    Object msg = null;
                    try
                    {
                        flag = valid.valid(value); // 校验
                        msg = valid.getMessage();
                    }
                    catch (Exception e)
                    {
                        flag = false; // 校验
                        msg = e.getMessage();
                    }
                    finally
                    {
                        if (!flag) // 校验失败
                        {
                            List<Object> messageList = (List<Object>) acceptor.get(fieldName);
                            if (messageList == null)
                            {
                                messageList = new ArrayList<>();
                                messageList.add(msg);
                                acceptor.put(fieldName, messageList);
                            }
                            else
                            {
                                messageList.add(msg);
                            }
                            if (!builder.hasErrorContinueValid())
                            {
                                // 不再继续校验
                                break;
                            }
                        }
                    }
                }
            }
        }
        return acceptor;
    }
}
