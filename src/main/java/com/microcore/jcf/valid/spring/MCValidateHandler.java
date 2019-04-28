package com.microcore.jcf.valid.spring;


import com.microcore.jcf.exception.ServiceExceptionUtil;
import com.microcore.jcf.valid.rule.base.IRuleBuilder;
import com.microcore.jcf.valid.validate.MCValidator;
import com.microcore.jcf.valid.validate.annotate.RuleBuilder;
import com.microcore.jcf.valid.validate.base.IValidateAcceptor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author leizhenyang
 */
@Aspect  //切面
@Component
public class MCValidateHandler
{

    @Autowired
    @Qualifier("mcValidator")
    private MCValidator mcValidator;

    @Pointcut("execution(public * com.mc.core.service..*.*(..))") //切入点
    private void method()
    {
    }

    @SuppressWarnings("unchecked")
    @Around("method()") //通知
    public Object handle(final ProceedingJoinPoint pjp) throws Throwable
    {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod(); // 获取当前方法
        Object[] args = pjp.getArgs(); // 参数的值
        Parameter[] parameters = method.getParameters(); // 参数
        if (parameters.length == args.length)
        {
            MCValidateMessage<String, IValidateAcceptor> messages = new MCValidateMessage<>();
            int length = args.length;
            for (int i = 0; i < length; i++)
            {
                RuleBuilder beanRuleBuilder = parameters[i].getAnnotation(RuleBuilder.class);
                if (beanRuleBuilder != null)
                {
                    Class<? extends IRuleBuilder> handler = beanRuleBuilder.builder();
                    IRuleBuilder builder = handler.newInstance();
                    mcValidator.setBuilder(builder);
                    //此处最重要
                    IValidateAcceptor acceptor = mcValidator.validate(args[i]);
                    if (!acceptor.isEmpty())
                    {
                        // 当前参数校验错误，注入到参数错误集中
                        messages.put(parameters[i].getName(), acceptor);
                    }
                }
            }
            // 验证错误抛出异常或者将异常信息注入到参数方法中
            if (!messages.isEmpty())
            {
                MCValidateMessage outMap = new MCValidateMessage();
                int i = findValidFailMapArgIndexByArgs(args, outMap);
                if (i == -1)
                {
                    // 如果没有找到ValidFailMap入参，那么抛出异常
                    throw ServiceExceptionUtil.getValidFailServiceException(messages);
                }
                // 将错误信息追加到方法参数列表中的ValidFailMap参数中
                outMap.putAll(messages);
                ((MCValidateMessage) args[i]).putAll(outMap);
            }
        }
        Object proceedReturn = pjp.proceed(args);
        return proceedReturn;
    }

    /**
     * 查找ValidateMessage对象
     *
     * @param args
     * @param messages
     * @return
     */
    public int findValidFailMapArgIndexByArgs(Object[] args, MCValidateMessage messages)
    {
        for (int i = 0; i < args.length; i++)
        {
            if (args[i] instanceof MCValidateMessage && args[i] == null)
            {
                messages.clear();
                return i;
            }
            if (args[i] instanceof MCValidateMessage)
            {
                messages.putAll((MCValidateMessage) args[i]);
                return i;
            }
        }
        return -1;
    }
}
