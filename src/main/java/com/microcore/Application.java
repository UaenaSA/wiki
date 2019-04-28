package com.microcore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.spring.annotation.MapperScan;


/**
 * spring boot启动器
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.microcore.modules.wiki.mapper"}, markerInterface = Mapper.class)
public class Application extends SpringBootServletInitializer {

    /**
     * main函数
     * @param args
     */
    public static void main(String[] args) {
        // spring boot 启动
        SpringApplication.run(Application.class, args);
    }

    /**
     * 配置
     * @param application
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
}
