package com.yimei.finance.config;

import freemarker.template.TemplateModelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Map;

/**
 * Created by JinWYP on 8/15/16.
 */

@Configuration
@AutoConfigureAfter({FreeMarkerAutoConfiguration.class})
public class FreemarkerConfiguration extends FreeMarkerAutoConfiguration.FreeMarkerWebConfiguration {

    @Autowired
    protected freemarker.template.Configuration configuration;

    @PostConstruct
    void FreeMarkerConfigurer() throws TemplateModelException, IOException {

        configuration.setSharedVariable("staticPath","/static/site");
        configuration.setSharedVariable("title","易煤网");

    }
}



