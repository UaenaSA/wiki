//package com.microcore.modules.config;
//
//import com.xiaoleilu.hutool.convert.Convert;
//import com.xiaoleilu.hutool.setting.Setting;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// * Web模块需要的配置常量在此统一配置
// *
// * @author weihanyu
// * @date 2017/12/28
// */
//public class WebConfigParam {
//    private static final Logger log = LoggerFactory.getLogger(WebConfigParam.class);
//
//    private static final String PRO_FILE_NAME = "webConfig.setting";
//
//    private static Setting setting;
//
//    static {
//        try {
//            setting = new Setting(PRO_FILE_NAME);
//        } catch (RuntimeException ex) {
//            log.error("加载系统配置文件 {} 时出错，原因为:{}", PRO_FILE_NAME, ex.getMessage());
//            setting = null;
//        }
//    }
//
//    //region 该类的常量
//    private static final String EMPTY_STR = "";
//    private static final String[] EMPTY_STR_ARRAY = new String[0];
//    private static final Integer[] EMPTY_INT_ARRAY = new Integer[0];
//    //endregion
//
//
//    //region 核心对外常量
//    public static final String NO_XSS_SUFFIX = getNoXSSSuffix();
//    /**
//     * 获取可以不经过登录直接访问的路径
//     */
//    public static final String[] BEYOND_LOGIN_FILTER_URI = getBeyondLoginFilterUri();
//
//    /**
//     * 权限Filter不拦截的路径
//     */
//    public static final String[] BEYOND_AUTHORITY_FILTER_URI = getBeyondAuthorityUri();
//
//    /**
//     * 默认的Request请求方式（Restful是4种）
//     */
//    public static final String[] DEFAULT_REQUEST_METHODS = getDefaultRequestMethods();
//
//    /**
//     * 静态资源的标志位
//     */
//    public static final String STATIC_RESOURCE_START_FLAG = getStaticResourceStartFlag();
//
//    /**
//     * 登录拦截器不拦截该数组的中元素开头的URI
//     */
//    public static final String[] BEYOND_LOGIN_FILTER_STATR_FLAGS = getBeyondLoginFilterStartFlags();
//
//    /**
//     * 授权拦截器不拦截该数组的中元素开头的URI
//     */
//    public static final String[] BEYOND_AUTHORITY_START_FLAGS = getBeyondAuthorityStartFlags();
//
//
//    /**
//     * 登录URI
//     */
//    public static final String LOGIN_URI = getLoginUri();
//
//    /**
//     * 获取session、cookie中保存用户信息的主键
//     */
//    public static final String USER_KEY = getUserKey();
//
//    /**
//     * 首页URI
//     */
//    public static final String HOME_PAGE_URI = getHomePageUri();
//
//    /**
//     * 无权限界面请求，通过ErrorController挑战
//     */
//    public static final String NO_AUTH_ERROR = getNoAuthErrorPage();
//
//    /**
//     * 获取是否是debug模式
//     */
//    public static final boolean DEBUG = getDebug();
//
//
//    //endregion
//
//
//    //region 私有方法
//    private static String getNoXSSSuffix() {
//        String key = "NoXSSSuffix";
//        if (setting == null) {
//            return EMPTY_STR;
//        }
//        return setting.get(key).toString();
//    }
//
//    private static String getStaticResourceStartFlag() {
//        String key = "StaticResourceStartFlag";
//        if (setting == null) {
//            return EMPTY_STR;
//        }
//        return setting.get(key).toString();
//    }
//
//    private static String[] getBeyondLoginFilterUri() {
//        String key = "BeyondLoginFilterUri";
//        if (setting == null) {
//            return EMPTY_STR_ARRAY;
//        }
//        return split(setting.getStr(key));
//    }
//
//    private static String[] getBeyondAuthorityUri() {
//        String key = "BeyondAuthorityUri";
//        if (setting == null) {
//            return EMPTY_STR_ARRAY;
//        }
//        return split(setting.getStr(key));
//    }
//
//    private static String[] getDefaultRequestMethods() {
//        String key = "DefaultRequestMethods";
//        if (setting == null) {
//            return EMPTY_STR_ARRAY;
//        }
//        return split(setting.getStr(key));
//    }
//
//    private static String getLoginUri() {
//        String key = "LoginUri";
//        if (setting == null) {
//            return EMPTY_STR;
//        }
//        return setting.getStr(key);
//    }
//
//    /**
//     * 此方法根据传入的系统名字来返回对应系统的URL地址. 不设为static
//     *
//     * @param sysName
//     * @return
//     */
//    public String getLoginOtherUrl(String sysName) {
//        String key = sysName;
//        if (setting == null) {
//            return EMPTY_STR;
//        }
//        return setting.getStr(key);
//
//    }
//
//    private static String getUserKey() {
//        String key = "UserKey";
//        if (setting == null) {
//            return EMPTY_STR;
//        }
//        return setting.getStr(key);
//    }
//
//    private static String getHomePageUri() {
//        String key = "HomePageUri";
//        if (setting == null) {
//            return EMPTY_STR;
//        }
//        return setting.getStr(key);
//    }
//
//    private static boolean getDebug() {
//        String key = "Debug";
//        if (setting == null) {
//            return false;
//        }
//        return Convert.toBool(setting.getStr(key), false);
//    }
//
//    private static String getNoAuthErrorPage() {
//        String key = "NoAuthErrorPage";
//        if (setting == null) {
//            return EMPTY_STR;
//        }
//        return setting.getStr(key);
//    }
//
//    private static String[] getBeyondLoginFilterStartFlags() {
//        String key = "BeyondLoginFilterStartFlag";
//        if (setting == null) {
//            return EMPTY_STR_ARRAY;
//        }
//        return split(setting.getStr(key));
//    }
//
//
//    private static String[] getBeyondAuthorityStartFlags() {
//        String key = "BeyondAuthorityStartFlag";
//        if (setting == null) {
//            return EMPTY_STR_ARRAY;
//        }
//        return split(setting.getStr(key));
//    }
//
//
//    private static String[] split(String value) {
//        if (StringUtils.isBlank(value)) {
//            return EMPTY_STR_ARRAY;
//        }
//        return value.split(",");
//    }
//
//
//}
