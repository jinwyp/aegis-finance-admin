package com.yimei.finance.site.representation.finance;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

@ApiModel(value = "financeSearch", description = "金融申请搜索参数")
@Data
@NoArgsConstructor
public class FinanceOrderSearch implements Serializable {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private int approveStateId;
    private String applyType;
    private String sourceId;

}
