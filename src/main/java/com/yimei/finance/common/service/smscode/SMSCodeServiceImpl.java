package com.yimei.finance.common.service.smscode;

import com.yimei.finance.common.entity.smscode.SmsCode;
import com.yimei.finance.common.repository.smscode.SmsCodeRepository;
import com.yimei.finance.common.representation.result.Result;
import com.yimei.finance.common.representation.smscode.EnumSmsCodeStatus;
import com.yimei.finance.common.representation.smscode.EnumSmsCodeType;
import com.yimei.finance.common.service.message.MessageServiceImpl;
import com.yimei.finance.utils.CodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@Slf4j
public class SMSCodeServiceImpl {
    @Autowired
    private SmsCodeRepository smsCodeRepository;
    @Autowired
    private MessageServiceImpl messageService;

    private final String HELLOWORDS_STR = "您的验证码是：";
    private final String CODE_SEND_ERROR = "验证码发送失败";
    private final String YOUR_CODE_ERROR = "验证码不正确";

    /**
     * 发送验证码
     * @param userId           用户id
     * @param phone            手机号
     * @param type             验证码的类型
     * @param IP               请求人的ip地址
     * @param complex          true: 复杂验证码(数字+字母), false: 简单验证码(数字)
     * @param expireMinute     多少分钟后失效
     */
    @Transactional(readOnly = false)
    public Result generateSMSCode(String userId, String phone, EnumSmsCodeType type, String IP, boolean complex, int expireMinute)  {
        SmsCode smsCode = smsCodeRepository.findByUserIdAndPhoneAndTypeAndStatusAndExpirationTimeGreaterThan(userId, phone, type.id, EnumSmsCodeStatus.UNVerified.id, LocalDateTime.now());
        if (smsCode == null) {
            String code = complex ? CodeUtils.CreateNumLetterCode() : CodeUtils.CreateCode();
            smsCode = new SmsCode(userId, phone, code, type.id, EnumSmsCodeStatus.UNVerified.id, LocalDateTime.now().plusMinutes(expireMinute), IP);
        } else {
            smsCode.setExpirationTime(LocalDateTime.now().plusMinutes(expireMinute));
        }
        smsCodeRepository.save(smsCode);
        try {
            messageService.sendSMS(phone, HELLOWORDS_STR + smsCode.getCode());
        } catch (Exception e) {
            log.error(phone, CODE_SEND_ERROR);
            return Result.error(CODE_SEND_ERROR);
        }
        return Result.success();
    }

    /**
     * 验证验证码是否正确
     */
    @Transactional(readOnly = false)
    public Result validateSMSCode(String userId, String phone, String code, EnumSmsCodeType type) {
        SmsCode smsCode = smsCodeRepository.findByUserIdAndPhoneAndCodeAndTypeAndStatusAndExpirationTimeGreaterThan(userId, phone, code, type.id, EnumSmsCodeStatus.UNVerified.id, LocalDateTime.now());
        if (smsCode == null) {
            return Result.error(YOUR_CODE_ERROR);
        } else {
            smsCode.setStatus(EnumSmsCodeStatus.Verified.id);
            smsCodeRepository.save(smsCode);
            return Result.success();
        }
    }

}
