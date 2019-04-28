package com.microcore.jcf.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.HashSet;
import java.util.Set;

/**
 * 全局
 *
 * @author leizhenyang
 */
public class Global {

    /**
     * 单元测试模式
     */
    public static boolean isJunitTest = false;

    /**
     * 单元测试模式的测试账号，模拟登陆
     */
    public static String junitMockLoginUserName;

    /**
     * 系统用户集合，该集合用户不在用户表中
     */
    public static Set<String> SYSTEM_USER_SET = new HashSet<>();

    /**
     * 日志
     */
    public static Logger log = LoggerFactory.getLogger(MethodHandles.lookup().getClass());

    /**
     * LCF ADD FROM ZKHX FOR ENCRYPT
     */
    public static final String KEY = "0123456789";

    /**
     * LCF ADD FOR CODE GENERATOR
     */
    public static class SystemFlag {

        /**
         * 应用系统
         */
        public static final String APPLICATION = "A";

        /**
         * 默认系统
         */
        public static final String DEFAULT = "A";
    }

    static {
        SYSTEM_USER_SET.add(SystemUser.SYSTEM);
    }

    public static class SystemUser {
        /**
         * 系统
         */
        public static final String SYSTEM = "System";
    }

    public static class Doc {

        /**
         * 导出路径
         */
        public static final String DEFAULT_EXPORT_PATH = "E:/test/export";

        /**
         * 导入路径
         */
        public static final String DEFAULT_IMPORT_PATH = "E:/test/import";

    }

}
