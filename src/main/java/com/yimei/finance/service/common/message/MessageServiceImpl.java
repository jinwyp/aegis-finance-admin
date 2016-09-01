package com.yimei.finance.service.common.message;

import com.yimei.finance.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by hary on 16/3/30.
 */
@Service
public class MessageServiceImpl {
    @Autowired
    private MailServiceImpl mailService;
    @Autowired
    private SMS sms;

    /**
     *发送 Simple 邮件
     * @param to                  收件人
     * @param subject
     * @param content             内容
     */
    public void sendSimpleMail(String to, String subject, String content) {
        //mailService.sendSimpleMail(to, subject, content);
    }

//    /**
//     * 发送 Html 邮件
//     * @param to                  收件人
//     * @param uuid
//     */
//    @Override
//    public void sendHtmlMail(String to, String uuid) {
//        mailService.sendHtmlMail(to, uuid);
//    }

    /**
     * 发送短信
     */
    public void sendSMS(String phone, String content) {
        if (content.length() < 1000) {
            sms.sendSMS(phone, content);
        } else {
            throw new BusinessException("短信内容太长,发送失败");
        }
    }

    /**
     * 发送测试短信
     */
    public void sendTestSMS(String phone, String content) {
        sms.sendTestSMS(phone, content);
    }


}
