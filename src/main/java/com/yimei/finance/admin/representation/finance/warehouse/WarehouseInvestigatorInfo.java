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
public class WarehouseInvestigatorInfo implements Serializable {
    private String applyCompanyName;                                 //申请公司/融资方
    private String ourContractCompany;                               //我方签约公司
    private String upstreamContractCompany;                          //上游签约单位
    private String downstreamContractCompany;                        //下游签约单位
    private String terminalServer;                                   //终端用户

    private String transportParty;                                   //运输方
    private String transitPort;                                      //中转港口

    private String qualityInspectionUnit;                            //质量检验单位
    private String quantityInspectionUnit;                           //数量检验单位

    private BigDecimal financingAmount;                              //融资金额
    private int financingPeriod;                                     //融资期限

    private BigDecimal interestRate;                                 //利率

    private Date businessStartTime;                                  //业务开始时间
    private String historicalCooperationDetail;                      //历史合作情况
    private String mainBusinessInfo;                                 //业务主要信息
    private String businessTransferInfo;                             //业务流转信息
    private String businessRiskPoint;                                //业务风险点
    private String performanceCreditAbilityEval;                     //履约信用及能力评估
    private String finalConclusion;                                  //综合意见/最终结论

    public String getBusinessStartTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (businessStartTime != null) {
            return sdf.format(businessStartTime);
        }
        return null;
    }
}
