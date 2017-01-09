package com.yimei.finance.admin.representation.finance.warehouse;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;


@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WarehouseInitData implements Serializable {
    private String applyUserId;
    private String applyUserName;
    private String applyUserPhone;
    private String applyCompanyId;
    private String applyCompanyName;                    //申请人-融资方 信息
    private String businessCode;                        //业务编号
    private Date financeCreateTime;                     //审批开始时间
    private Date financeEndTime;                        //审批结束时间
    private String downstreamCompanyName;               //下游签约公司-公司名称
    private BigDecimal financingAmount;                 //拟融资金额
    private int financingDays;                          //融资天数
    private BigDecimal interestRate;                    //利率
    private String coalType;                            //煤炭种类,品种
    private int coalIndex_NCV;
    private BigDecimal coalIndex_RS;
    private BigDecimal coalIndex_ADV;                   //煤炭 热值,硫分,空干基挥发分
    private String stockPort;                           //库存港口
    private BigDecimal coalAmount;                      //总质押吨数
    private String upstreamContractNo;                  //上游合同编号
    private String downstreamContractNo;                //下游合同编号



    public String getFinanceCreateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(financeCreateTime);
    }

    public String getFinanceEndTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(financeEndTime);
    }
}
