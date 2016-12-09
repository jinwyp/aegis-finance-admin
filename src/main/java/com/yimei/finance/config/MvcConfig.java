package com.yimei.finance.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yimei.finance.ext.intereceptors.AdminACLInterceptor;
import com.yimei.finance.ext.intereceptors.SiteACLInterceptor;
import com.yimei.finance.ext.intereceptors.WarehouseAdminACLInterceptor;
import com.yimei.finance.ext.jackson.Java8TimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import javax.validation.Validator;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by hary on 16/3/14.
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/swagger/v2/api-docs", "/v2/api-docs?group=restful-api");
        registry.addRedirectViewController("/swagger/swagger-resources/configuration/ui", "/swagger-resources/configuration/ui");
        registry.addRedirectViewController("/swagger/swagger-resources/configuration/security", "/swagger-resources/configuration/security");
        registry.addRedirectViewController("/swagger/swagger-resources", "/swagger-resources");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/admin/**").addResourceLocations("file:./frontend-admin/src/");
        registry.addResourceHandler("/static/site/**").addResourceLocations("file:./frontend-site/src/");
        registry.addResourceHandler("/files/**").addResourceLocations("file:../files/");
        registry.addResourceHandler("/swagger/swagger-ui.html**").addResourceLocations("classpath:/META-INF/resources/swagger-ui.html");
        registry.addResourceHandler("/swagger/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }




//    @Autowired
//    protected KittHandlerExceptionResolver kittHandlerExceptionResolver;
    @Autowired
    protected SiteACLInterceptor siteACLInterceptor;
    @Autowired
    protected AdminACLInterceptor adminACLInterceptor;
    @Autowired
    protected WarehouseAdminACLInterceptor warehouseAdminACLInterceptor;
    @Autowired
    protected ObjectMapper objectMapper;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(siteACLInterceptor).addPathPatterns("/finance/**", "/api/financing/**").excludePathPatterns("/finance/admin/**", "/api/financing/admin/**", "/finance/admin/404", "/finance/admin/500");
        registry.addInterceptor(adminACLInterceptor).addPathPatterns("/finance/admin/**", "/api/financing/admin/**").excludePathPatterns("/finance/admin/login", "/api/financing/admin/login", "/finance/admin/404", "/finance/admin/500");
        registry.addInterceptor(warehouseAdminACLInterceptor).addPathPatterns("/warehouse/admin/**", "/api/warehouse/admin/**").excludePathPatterns("/warehouse/admin/login", "/api/warehouse/admin/login", "/warehouse/admin/404", "/warehouse/admin/500");
    }

//    @Override
//    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
//        exceptionResolvers.add(kittHandlerExceptionResolver);
//    }

    //添加信息转换器
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
    }

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                container.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST, "/finance/admin/404"));
                container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/finance/admin/404"));
                container.addErrorPages(new ErrorPage(HttpStatus.UNAUTHORIZED, "/finance/admin/404"));
                container.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, "/finance/admin/404"));
                container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/finance/admin/500"));
            }
        };
    }

    //JSR-303
    @Bean(name = "validator")
    public Validator createBeanValidator() {
        return new LocalValidatorFactoryBean();
    }

    @PostConstruct
    private void jacksonConfig() {
        objectMapper.registerModule(new Java8TimeModule());
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeClientInfo(true);
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setIncludeHeaders(true);
        filter.setMaxPayloadLength(5120);
        return filter;
    }
}
