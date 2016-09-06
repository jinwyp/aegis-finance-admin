package com.yimei.finance.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yimei.cloudservice.mailer.api.MailMessage;
import com.yimei.cloudservice.queue.api.Queue;
import com.yimei.cloudservice.queue.api.util.ObjectMapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.ZonedDateTime;

@Service
@Slf4j
public class ExceptionReporter {
    @Value("${mail.to}")
    private String to;

    @Autowired
    private Queue queue;

    public void handle(Exception ex, String requestUrl, String formData, String requestHeader, Object user) {
        try {
            sendQueueMessage(ex, requestUrl, formData, requestHeader, user);
        } catch (Exception e) {
            log.error("error happened in send message");
        }

    }

    private void sendQueueMessage(Exception ex, String requestUrl, String formData, String requestHeader, Object user) {
        MailMessage mailMessage = new MailMessage();
        ObjectMapper mapper = ObjectMapperUtil.getMapper();

        String[] mail_tos = to.split(";");
        for (int i = 0; i < mail_tos.length; i++) {
            mailMessage.setTo(mail_tos[i]);
            mailMessage.setFrom("auto_server@yimei180.com");
            mailMessage.setSubject(ZonedDateTime.now().toString() + " " + ex.getClass().getName());
            mailMessage.setBody("requestHeader:\t" + requestHeader + "\nrequestUrl:\t" + requestUrl + "\nformData:\t" + formData + "\ncurrentUser:\t" + user + "\n" + ex.getMessage() + " " + getStackTrace(ex));

            try {
                String mailJson = mapper.writeValueAsString(mailMessage);
                System.out.println("message to send : " + mailJson);

                queue.sendMessage(mailJson, 0);

            } catch (Exception e) {
                System.out.println("error happened in transform mail to json");
            }
        }
    }

    public static String getStackTrace(final Throwable throwable) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }
}
