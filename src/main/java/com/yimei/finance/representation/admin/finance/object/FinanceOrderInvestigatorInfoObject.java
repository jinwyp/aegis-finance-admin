package com.yimei.finance.representation.admin.finance.object;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yimei.finance.representation.admin.finance.object.validated.SaveFinanceInvestigatorInfo;
import com.yimei.finance.representation.admin.finance.object.validated.SubmitFinanceInvestigatorInfo;
import com.yimei.finance.representation.common.base.BaseObject;
import com.yimei.finance.representation.common.enums.EnumCommonString;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@ApiModel(description = "金融-尽调员填写表单信息对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinanceOrderInvestigatorInfoObject extends BaseObject implements Serializable {
    private Long id;                                                 //主键
    private Long financeId;                                          //金融单id
    private String applyCompanyName;                                 //申请公司/融资方
    private String ourContractCompany;                               //我方签约公司
    private String upstreamContractCompany;                          //上游签约单位
    private String downstreamContractCompany;                        //下游签约单位
    private String terminalServer;                                   //终端用户

    @Size(max = 100, message = "运输方不能超过 {max} 个字符", groups = {SaveFinanceInvestigatorInfo.class, SubmitFinanceInvestigatorInfo.class})
    private String transportParty;                                   //运输方
    private String transitPort;                                      //中转港口

    @Size(max = 100, message = "质量检验单位不能超过 {max} 个字符", groups = {SaveFinanceInvestigatorInfo.class, SubmitFinanceInvestigatorInfo.class})
    private String qualityInspectionUnit;                            //质量检验单位

    @Size(max = 100, message = "数量检验单位不能超过 {max} 个字符", groups = {SaveFinanceInvestigatorInfo.class, SubmitFinanceInvestigatorInfo.class})
    private String quantityInspectionUnit;                           //数量检验单位

    @Digits(integer = 6, fraction = 2, message = "融资金额最大支持 {integer}位整数, {fraction}位小数", groups = {SaveFinanceInvestigatorInfo.class, SubmitFinanceInvestigatorInfo.class})
    @DecimalMin(value = "1", inclusive = true, message = "融资金额不能低于 {value} 万元", groups = {SaveFinanceInvestigatorInfo.class, SubmitFinanceInvestigatorInfo.class})
    @DecimalMax(value = "100000", inclusive = true, message = "融资金额不能超过 {value} 万元", groups = {SaveFinanceInvestigatorInfo.class, SubmitFinanceInvestigatorInfo.class})
    @NotBlank(message = "融资金额不能为空", groups = {SubmitFinanceInvestigatorInfo.class})
    private BigDecimal financingAmount;                              //融资金额

    @Range(min = 1, max = 3650, message = "融资期限应在 {min}-{max} 天之间", groups = {SaveFinanceInvestigatorInfo.class, SubmitFinanceInvestigatorInfo.class})
    private int financingPeriod;                                     //融资期限

    @Digits(integer = 2, fraction = 4, message = "利率最大支持 {integer}位整数, {fraction}位小数", groups = {SaveFinanceInvestigatorInfo.class, SubmitFinanceInvestigatorInfo.class})
    @DecimalMin(value = "0.0001", inclusive = true, message = "利率不能低于 {value} 万元", groups = {SaveFinanceInvestigatorInfo.class, SubmitFinanceInvestigatorInfo.class})
    @DecimalMax(value = "100", inclusive = false, message = "利率不能超过 {value} 万元", groups = {SaveFinanceInvestigatorInfo.class, SubmitFinanceInvestigatorInfo.class})
    private BigDecimal interestRate;                                 //利率

    @DateTimeFormat(pattern = EnumCommonString.LocalDate_Pattern)
    @JsonFormat(pattern = EnumCommonString.LocalDate_Pattern, timezone = EnumCommonString.GMT_8)
    private Date businessStartTime;                                  //业务开始时间

    @Size(max = 1000, message = "历史合作情况不能超过 {max} 个字符", groups = {SaveFinanceInvestigatorInfo.class, SubmitFinanceInvestigatorInfo.class})
    private String historicalCooperationDetail;                      //历史合作情况

    @Size(max = 1000, message = "业务主要信息不能超过 {max} 个字符", groups = {SaveFinanceInvestigatorInfo.class, SubmitFinanceInvestigatorInfo.class})
    private String mainBusinessInfo;                                 //业务主要信息

    @Size(max = 1000, message = "业务流转信息不能超过 {max} 个字符", groups = {SaveFinanceInvestigatorInfo.class, SubmitFinanceInvestigatorInfo.class})
    private String businessTransferInfo;                             //业务流转信息

    @Size(max = 5000, message = "业务风险点不能超过 {max} 个字符", groups = {SaveFinanceInvestigatorInfo.class, SubmitFinanceInvestigatorInfo.class})
    private String businessRiskPoint;                                //业务风险点

    @Size(max = 5000, message = "履约信用及能力评估不能超过 {max} 个字符", groups = {SaveFinanceInvestigatorInfo.class, SubmitFinanceInvestigatorInfo.class})
    private String performanceCreditAbilityEval;                     //履约信用及能力评估

    @Size(max = 5000, message = "综合意见不能超过 {max} 个字符", groups = {SaveFinanceInvestigatorInfo.class, SubmitFinanceInvestigatorInfo.class})
    private String finalConclusion;                                  //综合意见/最终结论
    private int needSupplyMaterial;                                  //需要补充材料 1: 需要, 0: 不需要

    @Size(max = 5000, message = "补充材料说明不能超过 {max} 个字符", groups = {SaveFinanceInvestigatorInfo.class, SubmitFinanceInvestigatorInfo.class})
    private String supplyMaterialIntroduce;                          //补充材料说明
    private String approveState;                                     //审批状态
    private Integer approveStateId;                                  //审批状态Id  0:审核不通过, 1:审核通过
    List<AttachmentObject> attachmentList1;                          //附件列表
    List<AttachmentObject> attachmentList2;                          //补充材料/附件列表


}