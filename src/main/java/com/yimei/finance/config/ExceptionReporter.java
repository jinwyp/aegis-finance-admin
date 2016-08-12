package com.yimei.finance.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by joe on 2/26/15.
 */
@Service
@Slf4j
public class ExceptionReporter {
    @Value("${mail.to}")
    private String to;

    public void handle(Exception ex, String requestUrl, String formData, String requestHeader, Object user) {
        try {
            sendQueueMessage(ex, requestUrl, formData, requestHeader, user);
        } catch (Exception e) {
            log.error("error happened in send message");
        }

    }

    private void sendQueueMessage(Exception ex, String requestUrl, String formData, String requestHeader, Object user) {

    }

    public static String getStackTrace(final Throwable throwable) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }
}
