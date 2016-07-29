package com.yimei.boot.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yimei.boot.ext.mvc.interceptors.ACLInterceptor;
import com.yimei.boot.ext.mvc.interceptors.SecureTokenInterceptor;
import com.yimei.boot.ext.mvc.support.ClientInfoMethodArgumentHandler;
import com.yimei.boot.ext.mvc.support.CurrentUserMethodArgumentHandler;
import com.yimei.boot.ext.mvc.support.JsonResultMethodArgumentResolver;
import com.yimei.boot.ext.mvc.support.KittHandlerExceptionResolver;
import com.yimei.boot.jackson.Java8TimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.PostConstruct;
import javax.validation.Validator;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by hary on 16/3/14.
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/bower_components/**").addResourceLocations("file:./frontend/bower_components/");
        registry.addResourceHandler("/styles/**").addResourceLocations("file:./frontend/app/styles/");
        registry.addResourceHandler("/images/**").addResourceLocations("file:./frontend/app/images/");
        registry.addResourceHandler("/scripts/**").addResourceLocations("file:./frontend/app/scripts/");
        registry.addResourceHandler("/files/**").addResourceLocations("file:../files/");
    }

    @Autowired
    protected ACLInterceptor aclInterceptor;
    @Autowired
    protected CurrentUserMethodArgumentHandler currentUserMethodArgumentHandler;
    @Autowired
    protected JsonResultMethodArgumentResolver jsonResultMethodArgumentResolver;

    @Autowired
    protected SecureTokenInterceptor secureTokenInterceptor;
    @Autowired
    protected ClientInfoMethodArgumentHandler clientInfoMethodArgumentHandler;

    @Autowired
    protected KittHandlerExceptionResolver kittHandlerExceptionResolver;
    @Autowired
    protected ObjectMapper objectMapper;



    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(aclInterceptor).addPathPatterns("/**");
        registry.addInterceptor(secureTokenInterceptor).addPathPatterns("/**");
    }

    //添加自定义方法参数解析器
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(currentUserMethodArgumentHandler);                                        //方法参数直接拿当前用户对象
        argumentResolvers.add(jsonResultMethodArgumentResolver);
        argumentResolvers.add(clientInfoMethodArgumentHandler);                                         //获取客户端信息
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(kittHandlerExceptionResolver);
    }

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
                container.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST, "/400"));
                container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404"));
                container.addErrorPages(new ErrorPage(HttpStatus.UNAUTHORIZED, "/401"));
                container.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, "/403"));
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
    }
}
