package com.yimei.finance.config;

import freemarker.template.TemplateModelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${ssourl.env}")
    private String SSOURL;
    @Value("${sso.protocol}")
    private String SSOPROTOCOL;
    @Value("${sitepage}")
    private String SITEPAGE;
    @Value("${logisticspage}")
    private String LOGISTICSPAGE;
    @Value("${pay.env}")
    private String PAYURL;

    @Value("${info.page}")
    private String INFOPAGE;

    @PostConstruct
    void FreeMarkerConfigurer() throws TemplateModelException, IOException {

        configuration.setSharedVariable("staticPath","/static/site");
        configuration.setSharedVariable("title","易煤网");
        configuration.setSharedVariable("ssoMemberUrl",SSOPROTOCOL+"://"+SSOURL);
        configuration.setSharedVariable("payUrl",SSOPROTOCOL+"://"+PAYURL);
        configuration.setSharedVariable("sitepage",SITEPAGE);
        configuration.setSharedVariable("logisticspage",LOGISTICSPAGE);
        configuration.setSharedVariable("infopage",INFOPAGE);



    }
}



