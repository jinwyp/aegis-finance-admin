package com.yimei.finance.representation.site.finance.result;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

@ApiModel(value = "financeSearch", description = "金融申请搜索参数")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinanceOrderSearch implements Serializable {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private int approveStateId;
    private String applyType;
    private String sourceId;

}
