package com.yimei.finance.kitt.representation.account;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class KittUserFundAccount implements Serializable {
    private int id;
    private int userId;                //客户公司 超级管理员 userId
    private String account;            //账户名
    private String accountBankName;    //账户开户行名称
    private String accountChildBankName;//账户开户行支行名称
    private String password;           //资金支付密码
    private int status;                //状态
    private String statusName;         //状态
    private String payPhone;           //支付手机号
    private BigDecimal balanceMoney;   //账户余额
    private String companyName;        //账户公司名称
    private Long cashBankOpenCode;     //绑定的取现银行开户支行Code
    private String cashChildBankName;  //绑定的取现银行开户支行
    private String cashBankAccount;    //绑定的取现银行账号
    private boolean deleted;           //是否删除
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdateTime;
}
