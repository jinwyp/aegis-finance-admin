package com.yimei.boot.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

/**
 * Created by xiangyang on 16/5/26.
 */
public class HttpUtils {
    private static ClientHttpRequestFactory requestFactory;
    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    static {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(2000);
        factory.setReadTimeout(5000);
        requestFactory = factory;
    }

    private static String sendHttpRequest(String url,HttpMethod method) {
        try {
            URI uri = new URI(url);
            ClientHttpRequest request = requestFactory.createRequest(uri, method);
            request.getHeaders().set("Accept", "application/json");
            ClientHttpResponse response = request.execute();
            byte[] bytes = new byte[4096];
            int toRead = (int) response.getHeaders().getContentLength();
            int tRead = toRead;
            //int read = 0;
            if (toRead == -1) {
                logger.error("url={},response.headers={}\nresponse.status={}\nrequest.headers={}",
                        url,response.getHeaders(),response.getStatusText(),request.getHeaders());
            }

            int offset = 0;
            while(toRead > 0) {
                //System.out.println("hhehehe");
                int length = response.getBody().read(bytes, offset, toRead);
                toRead -= length;
                offset = length;
                //read += length;
            }

           // System.out.println("tRead:"+ tRead+"  Read:"+read);

            String jsonData = new String(bytes, 0, tRead, Charset.forName("UTF-8"));
            logger.info("recv:{}", jsonData);
            return jsonData;
        } catch (IOException e) {
            logger.error("send request error ", e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static String  sendGetRequest(String url){
       return sendHttpRequest(url,HttpMethod.GET);
    }

    public static String  sendPostRequest(String url){
        return sendHttpRequest(url,HttpMethod.POST);
    }
}
