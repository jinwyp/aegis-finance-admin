package com.yimei.finance.site.representation.finance;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yimei.finance.common.representation.enums.EnumCommonString;
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
    @JsonFormat(pattern = EnumCommonString.LocalDateTime_Pattern, timezone = EnumCommonString.GMT_8)
    private Date createTime;
    private BigDecimal financingAmount;
    private int expectDate;
    private String approveState;
    private Integer approveStateId;
}
