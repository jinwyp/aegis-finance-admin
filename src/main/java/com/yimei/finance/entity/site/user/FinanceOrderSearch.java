package com.yimei.finance.entity.site.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinanceOrderSearch implements Serializable {
    private String startDate;
    private String endDate;
    private int approveStateId;
    private String applyType;
    private String sourceId;
    private int page;

}
