package com.yimei.finance.entity.site.user;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "financeSearch", description = "金融申请搜索参数")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinanceOrderSearch implements Serializable {
    private Date startDate;
    private Date endDate;
    private int approveStateId;
    private Date applyType;
    private Date sourceId;

}
