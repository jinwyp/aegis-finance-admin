package com.yimei.finance.pay.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferDTO {

    /*0 同行转账   1 非同行转账*/
    private int sameBank;

    /* 交易流水id */
    private String clientId;

    /*易煤账户id*/
    private String srcAccId;

    /*入金银行账户号*/
    private String targetAccNo;

    /*入金银行账户名*/
    private String targetAccNm;

    /*入金银行名称*/
    private String targetBankName;

    /*入金支行名称*/
    private String targetSubBranchName;

    /*出金资金账户号*/
    private String srcAccNo;

    /*出金资金账户名*/
    private String srcAccNm;

    /*金额*/
    private BigDecimal amount;

    /*操作者*/
    private String operator;

    /*支付联行号*/
    private String targetZFLHH;

    /*银行id*/
    private String bankId;

    /*支付联行号*/
    private String memo;

}
