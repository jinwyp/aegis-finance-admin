package com.yimei.finance.representation.site.finance;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@ApiModel(description = "合同搜索参数")
@Data
@NoArgsConstructor
public class FinanceContractSearch implements Serializable {
    private String contractNo;                                       //合同编号  
    private String financeType;                                      //金融类型(煤易融：MYR 煤易贷: MYD 煤易购: MYG)

}
