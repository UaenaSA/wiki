package com.microcore.jcf.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 从全局上下文中获取某个对象
 *
 * @author LiuChunfu
 * @date 2017-07-03
 */
public class ContextHolderUtil {

    private static final String USER_KEY = "user";

    private static final String ERROR_USER = "unknown";

    /**
     * 直接获取用户Id
     *
     * @return 用户Id
     */
    public static String getUserId() {
        Object obj = getRequestAttributes(USER_KEY);
        if (obj == null) {
            return ERROR_USER;
        }
        return obj.toString();
    }

    /**
     * 从RequestContextHolder中获取HttpServlet对象
     *
     * @return 用户id
     */
    public static Object getRequestAttributes(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        if (request == null) {
            return null;
        }
        //先简单处理IP问题，后续处理
        if ("IP".equalsIgnoreCase(key)) {
            String ip = request.getHeader("X-Real-IP");
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("X-Forwarded-For");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
            if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip) && ip != null) {
                if (ip.indexOf(",") > 0) {
                    return ip.substring(0, ip.indexOf(","));
                } else {
                    return ip;
                }
            }
        }
        HttpSession session = request.getSession();
        if (session == null) {
            return null;
        }
        Object obj = session.getAttribute(key);
        if (obj == null) {
            return null;
        }
        return obj;
    }
}
