package com.microcore.jcf.base;

import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.util.*;

/**
 * Created by LiuChunfu on 2017/3/21.
 * 针对Oracle数属性null时无法获取jdbcType导致错误的问题
 */
@Intercepts(
        {@Signature(
                type = ParameterHandler.class,
                method = "setParameters",
                args = {PreparedStatement.class}
        )
        })
public class JdbcTypeInterceptor implements Interceptor {
    private static Set<String> methodSet = new HashSet<>();
    private static Map<Class<?>, JdbcType> typeMap = new HashMap<Class<?>, JdbcType>();
    private static final Field mappedStatementField;
    private static final Field boundSqlField;

    static {
        try {
            mappedStatementField = DefaultParameterHandler.class.getDeclaredField("mappedStatement");
            mappedStatementField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("获取DefaultParameterHandler字段mappedStatement时出错", e);
        }
        try {
            boundSqlField = DefaultParameterHandler.class.getDeclaredField("boundSql");
            boundSqlField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("获取DefaultParameterHandler字段boundSql时出错", e);
        }
        //设置默认的方法,是用Mapper所有方法
        Method[] methods = tk.mybatis.mapper.common.Mapper.class.getMethods();
        for (Method method : methods) {
            methodSet.add(method.getName());
        }
        //设置默认的类型转换,参考TypeHandlerRegistry
        register(Boolean.class, JdbcType.BOOLEAN);
        register(boolean.class, JdbcType.BOOLEAN);

        register(Byte.class, JdbcType.TINYINT);
        register(byte.class, JdbcType.TINYINT);

        register(Short.class, JdbcType.SMALLINT);
        register(short.class, JdbcType.SMALLINT);

        register(Integer.class, JdbcType.INTEGER);
        register(int.class, JdbcType.INTEGER);

        register(Long.class, JdbcType.BIGINT);
        register(long.class, JdbcType.BIGINT);

        register(float.class, JdbcType.FLOAT);
        register(Float.class, JdbcType.FLOAT);

        register(Double.class, JdbcType.DOUBLE);
        register(double.class, JdbcType.DOUBLE);

        register(String.class, JdbcType.VARCHAR);

        register(BigDecimal.class, JdbcType.DECIMAL);
        register(BigInteger.class, JdbcType.DECIMAL);

        register(Byte[].class, JdbcType.BLOB);
        register(byte[].class, JdbcType.BLOB);

        register(Date.class, JdbcType.DATE);
        register(java.sql.Date.class, JdbcType.DATE);
        register(java.sql.Time.class, JdbcType.DATE);
        register(java.sql.Timestamp.class, JdbcType.DATE);

        register(Character.class, JdbcType.CHAR);
        register(char.class, JdbcType.CHAR);

    }

    /**
     * 拦截器
     *
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //这里不考虑有多个基于该方法插件时的情况
        Object handler = invocation.getTarget();
        MappedStatement mappedStatement = (MappedStatement) mappedStatementField.get(handler);
        //一旦哦诶之了methods,就只对配置的方法进行处理
        if (handler instanceof DefaultParameterHandler) {
            if (methodSet.size() > 0) {
                String msID = mappedStatement.getId();
                String methodName = msID.substring(msID.lastIndexOf(".") + 1, msID.length());
                if (!methodSet.contains(methodName)) {
                    return invocation.proceed();
                }
            }
            //获取基本变量类型
            BoundSql boundSql = (BoundSql) boundSqlField.get(handler);
            Object parameterObject = ((DefaultParameterHandler) handler).getParameterObject();
            Configuration configuration = mappedStatement.getConfiguration();
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            PreparedStatement ps = (PreparedStatement) invocation.getArgs()[0];
            //原defaultParameterHandler逻辑
            ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
            List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
            if (parameterMappings != null) {
                for (int i = 0; i < parameterMappings.size(); i++) {
                    ParameterMapping parameterMapping = parameterMappings.get(i);
                    if (parameterMapping.getMode() != ParameterMode.OUT) {
                        Object value;
                        String propertyName = parameterMapping.getProperty();
                        if (boundSql.hasAdditionalParameter(propertyName)) {
                            value = boundSql.getAdditionalParameter((propertyName));
                        } else if (parameterObject == null) {
                            value = null;
                        } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                            value = parameterObject;
                        } else {
                            MetaObject metaObject = configuration.newMetaObject(parameterObject);
                            value = metaObject.getValue(propertyName);
                        }
                        TypeHandler typeHandler = parameterMapping.getTypeHandler();
                        JdbcType jdbcType = parameterMapping.getJdbcType();
                        if (value == null && jdbcType == null) {
                            if (parameterMapping.getJavaType() != null && typeMap.containsKey(parameterMapping.getJavaType())) {
                                jdbcType = typeMap.get(parameterMapping.getJavaType());
                            } else {
                                jdbcType = configuration.getJdbcTypeForNull();
                            }
                        }
                        typeHandler.setParameter(ps, i + 1, value, jdbcType);
                    }
                }
            }
            return null;
        }
        System.out.println(123);
        return invocation.proceed();
    }

    /**
     * 插件
     *
     * @param target
     * @return
     */
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    /**
     * 是否不为空
     *
     * @param str
     * @return
     */
    private boolean isNotEmpty(String str) {
        return str != null && str.length() > 0;
    }

    /**
     * 设置属性
     *
     * @param properties
     */
    @Override
    public void setProperties(Properties properties) {
        String methodStr = properties.getProperty("methods");
        if (isNotEmpty(methodStr)) {
            //处理所有方法
            if (methodStr.equalsIgnoreCase("ALL")) {
                methodSet.clear();
            } else {
                String[] methods = methodStr.split(",");
                for (String method : methods) {
                    methodSet.add(method);
                }
            }
        }
        //手动配置
        String typeMapStr = properties.getProperty("typeMaps");
        if (isNotEmpty(typeMapStr)) {
            String[] typeMaps = typeMapStr.split(",");
            for (String typeMap : typeMaps) {
                String[] kvs = typeMap.split(":");
                if (kvs.length == 2) {
                    register(kvs[0], kvs[1]);
                }
            }
        }
    }

    /**
     * 注册
     *
     * @param type
     * @param jdbcType
     */
    public static void register(String type, String jdbcType) {
        try {
            typeMap.put(Class.forName(type), JdbcType.valueOf(jdbcType));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("配置typeMaps时出错", e);
        }
    }

    /**
     * 注册
     *
     * @param type
     * @param jdbcType
     */
    public static void register(Class<?> type, JdbcType jdbcType) {
        typeMap.put(type, jdbcType);
    }
}
