package com.example.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger配置
 * http://localhost:8001/swagger-ui.html#/
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {

    /**
     * 一个Docket实例，代表一个组，配置区分
     * @return
     */
    @Bean
    public Docket docket() {
        // 设置环境范围
//        Profiles profiles = Profiles.of("dev","test");
        // 如果在该环境返回内则返回：true，反之返回 false  environment通过参数传递Environment用于获取权限
//        boolean flag = environment.acceptsProfiles(profiles);
        return new Docket(DocumentationType.SWAGGER_2)
//                修改组名
                .groupName("")
//                配置接口信息
                .select()
//                设置扫描接口、如何扫描接口
                .apis(RequestHandlerSelectors
                        // 扫描全部的接口，默认
                        //.any()
                        // 全部不扫描
                        //.none()
                        // 扫描带有指定注解的类下所有接口
                        //.withClassAnnotation(RestController.class)
                        // 扫描带有只当注解的方法接口
                        //.withMethodAnnotation(PostMapping.class)
                        //扫描指定包下的接口，最为常用
                        .basePackage("com.example.business")
                )
                .paths(PathSelectors
                        .any() // 满足条件的路径，该断言总为true
                        //.none() // 不满足条件的路径，该断言总为false（可用于生成环境屏蔽 swagger）
                        //.ant("/user/**") // 满足字符串表达式路径
                        //.regex("") // 符合正则的路径
                ).build()
                // 配置基本信息
                .apiInfo(apiInfo());
    }

    // 基本信息设置
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Random-接口文档")
                .description("嗨嗨害！来了嗷")
                .termsOfServiceUrl("127.0.0.1:8003")
                .version("1.0")
                .build();
    }
}
