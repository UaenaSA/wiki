package com.microcore.common.utils;

import com.microcore.common.exception.RRException;
import com.microcore.jcf.config.Global;
import com.microcore.modules.sys.entity.SysUserEntity;
import com.microcore.modules.sys.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * Shiro工具类
 *
 * @author chenshun
 * @date 2016年11月12日 上午9:49:19
 */
public class ShiroUtils {

    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static SysUserEntity getUserEntity() {
        if (Global.isJunitTest && StringUtils.isNotBlank(Global.junitMockLoginUserName)) {
            // 单元测试时用的代码
            return SpringContextUtils.getBean("sysUserService", SysUserService.class).queryByUserName(Global.junitMockLoginUserName);
        } else {
            // FIXME 按道理应该返回SecurityUtils.getSubject().getPrincipal()
            SysUserEntity user = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
            return SpringContextUtils.getBean("sysUserService", SysUserService.class).queryObject(user.getUserId());
        }
    }

    public static Long getUserId() {
        return getUserEntity().getUserId();
    }

    public static void setSessionAttribute(Object key, Object value) {
        getSession().setAttribute(key, value);
    }

    public static Object getSessionAttribute(Object key) {
        return getSession().getAttribute(key);
    }

    public static boolean isLogin() {
        return SecurityUtils.getSubject().getPrincipal() != null;
    }

    public static String getKaptcha(String key) {
        Object kaptcha = getSessionAttribute(key);
        if (kaptcha == null) {
            throw new RRException("验证码已失效");
        }
        getSession().removeAttribute(key);
        return kaptcha.toString();
    }

}
