package com.microcore.datasources;

import com.microcore.jcf.base.JdbcTypeInterceptor;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 配置多数据源
 *
 * @date 2017/8/19 0:41
 */
@Configuration
public class DynamicDataSourceConfig {


    /**
     * 第一个数据源
     *
     * @return
     */
    @Bean
    @ConfigurationProperties("spring.datasource.hikari.first")
    public DataSource firstDataSource() {
        return new HikariDataSource();
    }

    /**
     * 第二个数据源
     *
     * @return
     */
    @Bean
    @ConfigurationProperties("spring.datasource.hikari.second")
    public DataSource secondDataSource() {
        return new HikariDataSource();
    }

    /**
     * 数据源
     *
     * @param firstDataSource
     * @param secondDataSource
     * @return
     */
    @Bean
    @Primary
    public DynamicDataSource dataSource(DataSource firstDataSource, DataSource secondDataSource) {
        Map<String, DataSource> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceNames.FIRST, firstDataSource);
        targetDataSources.put(DataSourceNames.SECOND, secondDataSource);
        return new DynamicDataSource(firstDataSource, targetDataSources);
    }

    /**
     * 设置jdbc拦截器
     *
     * @return
     */
    @Bean
    public Interceptor setInterceptor() {
        return new JdbcTypeInterceptor();
    }

}
