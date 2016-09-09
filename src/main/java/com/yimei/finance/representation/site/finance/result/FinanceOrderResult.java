package com.yimei.finance.representation.site.finance.result;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(description = "金融申请单列表元素")
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
