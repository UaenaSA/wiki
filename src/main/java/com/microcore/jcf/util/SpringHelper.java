package com.microcore.jcf.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 只能获取到Spring管理的Bean，无法获取到SpringMVC的？
 *
 * @author Liu.Junjie
 */
public class SpringHelper implements ApplicationContextAware {

    private ApplicationContext applicationContext;
    private static SpringHelper springHelper;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        springHelper = this;
    }

    /**
     * 获取spring helper
     *
     * @return
     */
    private static SpringHelper getInstance() {
        if (springHelper == null) {
            throw new IllegalArgumentException("SpringHelper resetCache failed");
        }
        return springHelper;
    }

    /**
     * 获取上下文
     *
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return getInstance().applicationContext;
    }

    /**
     * 获取bean
     *
     * @param name
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name, Class<T> type) {
        return getApplicationContext().getBean(name, type);
    }

    /**
     * 获取bean
     *
     * @param name
     * @return
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * 获取bean
     *
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> type) {
        return getApplicationContext().getBean(type);
    }

    /**
     * 获取bean
     *
     * @param type
     * @param <T>
     * @return
     * @throws BeansException
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getApplicationContext().getBeansOfType(type);
    }

    /**
     * 发布事件
     *
     * @param event
     */
    public static void publishEvent(ApplicationEvent event) {
        getApplicationContext().publishEvent(event);
    }

    /**
     * 获取bean集合
     *
     * @param ctx
     * @return
     */
    public static List<Object> listAllBean(ApplicationContext ctx) {
        String[] beanNames = ctx.getBeanDefinitionNames();
        List<Object> beans = new ArrayList<Object>(beanNames.length);
        for (int i = 0; i < beanNames.length; i++) {
            String name = beanNames[i];
            beans.add(ctx.getBean(name));
        }
        return beans;
    }

}