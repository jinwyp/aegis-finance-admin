package com.yimei.finance.site.representation.warehouse;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class WarehouseDetail implements Serializable {
    private Long financeId;                          //审批id
    private String businessCode;                     //业务编号
    private BigDecimal paidDeposit;                  //已经缴纳保证金
    private BigDecimal confirmFinancingAmount;       //实际放款金额
    private int coalIndex_NCV;                       //煤炭 热值
    private BigDecimal coalIndex_RS;                 //煤炭 硫分
    private BigDecimal coalIndex_ADV;                //煤炭 空干基挥发分
    private int contractFileNumber;                  //合同文件份数
    private int financeFileNumber;                   //财务文件份数
    private int businessFileNumber;                  //业务文件份数
    private BigDecimal alreadyPayPrinciple;          //已回款本金
    private BigDecimal waitPayPrinciple;             //待回款本金
}
