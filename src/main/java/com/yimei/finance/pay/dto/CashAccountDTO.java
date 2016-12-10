package com.yimei.finance.pay.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CashAccountDTO extends TransferStatusDTO {

    /*系统账户ID*/
    private String accId;

    /*银行账户No*/
    private String acctNo;

    private BigDecimal sjAmount;

    private BigDecimal kyAmount;

    private BigDecimal djAmount;
}
