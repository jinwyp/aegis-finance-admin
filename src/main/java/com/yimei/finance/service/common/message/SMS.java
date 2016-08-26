package com.yimei.finance.service.common.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by lxj on 14/11/6.
 */
@Service
public class SMS  {
	Logger logger  = LoggerFactory.getLogger(SMS.class) ;

	@Value("${sms.sn}")
	private String sn;
	@Value("${sms.password}")
	private String password;
	@Value("${sms.server}")
	private String server;



	/**
	 * 发送短信
	 */
	public void sendSMS(String phone, String content) {
		SMSClient smsClient = new SMSClient(server, sn, password);
		String hellowords = "";
		try {
			smsClient.sendSMS(phone, content, hellowords, "【易煤网】");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("- SMS_EXCEPTION -------- 短信发送失败, phone=" + phone);
		}
		logger.info("CODE---------" + phone + "-------" + content);
	}

	/**
	 * 发送测试短信
	 */
	public void sendTestSMS(String phone, String content) {
		logger.info("测试版, 并不真正发送. ********** CODE---------" + phone + "-------" + content);
	}

}
