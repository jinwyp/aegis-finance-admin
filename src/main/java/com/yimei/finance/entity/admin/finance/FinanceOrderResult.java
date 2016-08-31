package com.yimei.finance.entity.admin.finance;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class FinanceOrderResult implements Serializable {
    private Long id;
    private String userId;
    private String sourceId;
    private String applyType;
    private Date createTime;
    private BigDecimal financingAmount;
    private int expectDate;
}
