package com.yimei.finance.site.representation.warehouse;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yimei.finance.common.representation.enums.EnumCommonString;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
public class WarehouseList implements Serializable {
    private Long financeId;                          //审批id
    private String userId;
    private String applyType;
    @JsonFormat(pattern = EnumCommonString.LocalDateTime_Pattern, timezone = EnumCommonString.GMT_8)
    private Date createTime;
    private BigDecimal financingAmount;
    private String approveState = "审核通过";
    private String businessCode;                     //业务编号
    private String downstreamCompanyName;            //下游签约公司-公司名称
    private LocalDateTime financeCreateTime;         //审批开始时间
    private String stockPort;                        //库存港口
    private BigDecimal coalAmount;                   //总质押吨数
    private BigDecimal waitRedeemAmount;             //待赎回数量
    private BigDecimal confirmFinancingAmount;       //融资金额
    private BigDecimal paidDeposit;                  //已经缴纳保证金
    private BigDecimal alreadyPayPrinciple;          //已回款本金
    private BigDecimal waitPayPrinciple;             //待回款本金
    private BigDecimal capitalCost;                  //资金成本
    private int financingDays;                       //融资天数
    private String coalType;                         //煤炭种类
    private int coalIndex_NCV;                       //煤炭 热值
    private BigDecimal coalIndex_RS;                 //煤炭 硫分
    private BigDecimal coalIndex_ADV;                //煤炭 空干基挥发分
}
