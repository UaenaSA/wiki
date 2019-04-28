package com.microcore.common.utils;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * HttpServlet相关的工具类
 *
 * @author LiuChunfu
 * @date 2017-06-15
 */
public class HttpServletUtil
{
    /**
     * 判定某一次Http请求是否是ajax请求
     *
     * @param request http请求
     * @return true:是 false:不是
     */
    public static boolean isAjaxRequest(HttpServletRequest request)
    {
        String header = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equals(header);
    }

    /**
     * 从ThreadLocal中获取 HttpServletRequest 对象
     *
     * @return 本次请求的 HttpServletRequest 对象
     */
    public static HttpServletRequest getHttpServletRequest()
    {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    /**
     * 获取 URL-Method 的Map集合
     *
     * @param request http集合
     * @return
     */
    public static Map<RequestMappingInfo, HandlerMethod> getHandlerMethods(HttpServletRequest request)
    {
        WebApplicationContext context = (WebApplicationContext) request.getAttribute(
            DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        RequestMappingHandlerMapping bean = context.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = bean.getHandlerMethods();
        return handlerMethods;
    }

}
