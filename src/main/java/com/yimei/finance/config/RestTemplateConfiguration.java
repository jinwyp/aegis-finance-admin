package com.yimei.finance.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yimei.finance.ext.jackson.Java8TimeModule;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangqi on 16/8/23.
 */
@Configuration
public class RestTemplateConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(RestTemplateConfiguration.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${pg.organizationId}")
    private String organizationId;

    @Value("${pg.secretKey}")
    private String secretKey;

    @Bean
    public RestTemplate createRestTemplate() {
        objectMapper.registerModule(new Java8TimeModule());
        RestTemplate restTemplate = new RestTemplate();

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter jsonMessageConverter = new MappingJackson2HttpMessageConverter();
        jsonMessageConverter.setObjectMapper(objectMapper);
        messageConverters.add(jsonMessageConverter);

        restTemplate.setMessageConverters(messageConverters);

        List<ClientHttpRequestInterceptor> clientHttpRequestInterceptors = new ArrayList<>();
        //添加和pgj交互的拦截器
        clientHttpRequestInterceptors.add(new RestPayInterceptor());
        restTemplate.setInterceptors(clientHttpRequestInterceptors);
        return restTemplate;
    }

    /**
     * 添加指定的organizationId, 请求body进行Hmac加密
     */
    class RestPayInterceptor implements ClientHttpRequestInterceptor {

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
            HttpHeaders headers = request.getHeaders();
            headers.add("X-Org", organizationId);
            headers.add("X-Sig", pgEncodeHexString(body));
            return execution.execute(request, body);
        }
    }

    //pg 加密
    private String pgEncodeHexString(byte[] body) {
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), "UTF-8");
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(keySpec);
            byte[] signature = mac.doFinal(body);
            logger.info("signature:{}", signature);
            return Hex.encodeHexString(signature);
        } catch (Exception e) {
            throw new RuntimeException("aegis-pg restService error", e);
        }
    }

}
