package com.yimei.finance.entity.site.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class FinanceSearch implements Serializable {
    private String startDate;
    private String endDate;
    private String approveState;
    private String applyType;
    private String sourceId;
}
