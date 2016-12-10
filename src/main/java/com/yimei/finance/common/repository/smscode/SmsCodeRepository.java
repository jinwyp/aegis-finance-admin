package com.yimei.finance.common.repository.smscode;

import com.yimei.finance.common.entity.smscode.SmsCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface SmsCodeRepository extends JpaRepository<SmsCode, Long> {
    SmsCode findByUserIdAndPhoneAndTypeAndStatusAndExpirationTimeGreaterThan(@Param("userId") String userId,
                                                                             @Param("phone") String phone,
                                                                             @Param("type") int type,
                                                                             @Param("status") int status,
                                                                             @Param("expirationTime") LocalDateTime expirationTime);


    SmsCode findByUserIdAndPhoneAndCodeAndTypeAndStatusAndExpirationTimeGreaterThan(@Param("userId") String userId,
                                                                                    @Param("phone") String phone,
                                                                                    @Param("code") String code,
                                                                                    @Param("type") int type,
                                                                                    @Param("status") int status,
                                                                                    @Param("expirationTime") LocalDateTime expirationTime);
}
