package com.yimei.finance.representation.admin.finance;

import com.yimei.finance.entity.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinanceOrderInvestigatorInfoObject extends BaseEntity implements Serializable {
    private Long id;                                                 //主键
    private Long financeId;                                          //金融单id
    private String applyCompanyName;                                 //申请公司/融资方
    private String ourContractCompany;                               //我方签约公司
    private String upstreamContractCompany;                          //上游签约单位
    private String downstreamContractCompany;                        //下游签约单位
    private String terminalServer;                                   //终端用户

    @Size(max = 100, message = "运输方不能超过 {max} 个字符")
    private String transportParty;                                   //运输方
    private String transitPort;                                      //中转港口

    @Size(max = 100, message = "质量检验单位不能超过 {max} 个字符")
    private String qualityInspectionUnit;                            //质量检验单位

    @Size(max = 100, message = "数量检验单位不能超过 {max} 个字符")
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
    private int needSupplyMaterial;                                  //需要补充材料 1: 需要, 0: 不需要
    private String supplyMaterialIntroduce;                          //补充材料说明
    private int noticeApplyUser;                                     //通知申请用户 1: 通知, 0: 不通知
    private int noticeSalesman;                                      //通知业务员   1: 通知, 0: 不通知
    List<AttachmentObject> attachmentList;                           //附件列表

}