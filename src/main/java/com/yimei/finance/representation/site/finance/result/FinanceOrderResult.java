package com.yimei.finance.representation.site.finance.result;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(description = "金融申请单列表元素")
@Data
@NoArgsConstructor
public class FinanceOrderResult implements Serializable {
    private Long id;
    private String userId;
    private String sourceId;
    private String applyType;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private BigDecimal financingAmount;
    private int expectDate;
    private String approveState;
    private Integer approveStateId;
}
