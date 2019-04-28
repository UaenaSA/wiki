package com.microcore.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        ParameterBuilder params = new ParameterBuilder();
        List<Parameter> list = new ArrayList<>();
        // 在swagger文档中构建token参数
        params.name("token").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        list.add(params.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)) //加了ApiOperation注解的方法，生成接口文档
                //.apis(RequestHandlerSelectors.any()) // 所有被扫描到的Controller，生成接口文档
                //.paths(PathSelectors.regex("/sys/.*"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(list)
                .apiInfo(apiInfo());
        //return new Docket(DocumentationType.SWAGGER_2)
        //    .apiInfo(apiInfo())
        //    .select()
        //    .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))           //加了ApiOperation注解的方法，生成接口文档
        //    .apis(RequestHandlerSelectors.basePackage("com.microcore"))  //包下的类，生成接口文档
        //    .paths(PathSelectors.any())
        //    .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("微核科技微")
                .description("MicroCore Mic API文档")
                .termsOfServiceUrl("")
                .version("1.0")
                .build();
    }

}
