package com.yimei.finance.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by wangqi on 16/8/10.
 */
@Configuration
@EnableSwagger2
public class Swagger {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("restful-api")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yimei.finance.controllers"))
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .apiInfo(apiInfo())
                .tags(
                    new Tag("site-page", "网站页面"),
                    new Tag("site-page-error", "网站错误页面"),
                    new Tag("site-api", "网站 RESTful API"),
                    new Tag("admin-page", "管理后台页面"),
                    new Tag("admin-page-error", "管理后台错误页面"),
                    new Tag("admin-api", "管理后台 RESTful API"),
                    new Tag("admin-api-user", "管理后台 RESTful API 用户登录与信息"),
                    new Tag("admin-api-permission", "管理后台 RESTful API 权限"),
                    new Tag("admin-api-group", "管理后台 RESTful API 用户组"),
                    new Tag("admin-api-flow", "管理后台 RESTful API 融资流程"),
                    new Tag("admin-api-flow-myr", "管理后台 RESTful API 煤易融相关接口"),
                    new Tag("admin-api-flow-myd", "管理后台 RESTful API 煤易贷相关接口"),
                    new Tag("admin-api-flow-myg", "管理后台 RESTful API 煤易购相关接口"),
                    new Tag("example", "范例方法")
                );
    }

    @Bean
    public UiConfiguration uiConfig() {
        return UiConfiguration.DEFAULT;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot 中使用Swagger2 构建RESTful APIs")
                .description("更多Spring Boot相关文章请关注：http://blog.didispace.com/")
                .termsOfServiceUrl("http://blog.didispace.com/")
                .contact("程序猿DD")
                .version("1.0")
                .build();
    }
}
