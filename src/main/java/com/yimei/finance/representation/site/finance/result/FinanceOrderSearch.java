package com.yimei.finance.representation.site.finance.result;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@ApiModel(value = "financeSearch", description = "金融申请搜索参数")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinanceOrderSearch implements Serializable {
    private String startDate;
    private String endDate;
    private int approveStateId;
    private String applyType;
    private String sourceId;

}
