package com.yimei.finance.representation.site.finance;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yimei.finance.representation.common.enums.EnumCommonString;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@ApiModel(description = "金融合同列表元素")
@Data
@NoArgsConstructor
public class FinanceContractResult implements Serializable {
    private String contractNo;                                       //合同编号  
    private String financeSourceId;                                  //金融业务编号
    private String financeType;                                      //金融类型(煤易融：MYR 煤易贷: MYD 煤易购: MYG)
    @JsonFormat(pattern = EnumCommonString.LocalDateTime_Pattern, timezone = EnumCommonString.GMT_8)
    private Date createTime;                                         //创建时间
}
