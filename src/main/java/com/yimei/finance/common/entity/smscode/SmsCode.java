package com.yimei.finance.common.entity.smscode;

import com.yimei.finance.common.entity.base.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "warehouse_sms_code")
@Data
@NoArgsConstructor
public class SmsCode extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "phone")
    private String phone;
    @Column(name = "code")
    private String code;
    @Column(name = "type")
    private int type;
    @Column(name = "status")
    private int status;
    @Column(name = "expiration_time")
    private LocalDateTime expirationTime;
    @Column(name = "ip")
    private String ip;

    public SmsCode (String userId, String phone, String code, int type, int status, LocalDateTime expirationTime, String ip) {
        this.userId = userId;
        this.phone = phone;
        this.code = code;
        this.type = type;
        this.status = status;
        this.expirationTime = expirationTime;
        this.ip = ip;
    }
}
