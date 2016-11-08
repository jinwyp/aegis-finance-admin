package com.yimei.finance.representation.admin.finance.object;

import com.yimei.finance.representation.common.file.AttachmentObject;
import com.yimei.finance.representation.admin.finance.object.validated.SaveFinanceRiskManagerInfo;
import com.yimei.finance.representation.admin.finance.object.validated.SubmitFinanceRiskManagerInfo;
import com.yimei.finance.representation.common.base.BaseObject;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@ApiModel(description = "金融-风控人员填写信息对象")
@Data
@NoArgsConstructor
public class FinanceOrderRiskManagerInfoObject extends BaseObject implements Serializable {
    private Long id;                                                 //主键
    private Long financeId;                                          //金融单id

    @Size(max = 5000, message = "分销能力评估不能超过 {max} 个字符", groups = {SaveFinanceRiskManagerInfo.class, SubmitFinanceRiskManagerInfo.class})
    private String distributionAbilityEval;                          //分销能力评估

    @Size(max = 1000, message = "预计回款情况不能超过 {max} 个字符", groups = {SaveFinanceRiskManagerInfo.class, SubmitFinanceRiskManagerInfo.class})
    private String paymentSituationEval;                             //预计回款情况

    @Size(max = 5000, message = "业务风险点不能超过 {max} 个字符", groups = {SaveFinanceRiskManagerInfo.class, SubmitFinanceRiskManagerInfo.class})
    private String businessRiskPoint;                                //业务风险点

    @Size(max = 5000, message = "风险控制方案不能超过 {max} 个字符", groups = {SaveFinanceRiskManagerInfo.class, SubmitFinanceRiskManagerInfo.class})
    private String riskControlScheme;                                //风险控制方案

    @Size(max = 5000, message = "风控结论不能超过 {max} 个字符", groups = {SaveFinanceRiskManagerInfo.class, SubmitFinanceRiskManagerInfo.class})
    private String finalConclusion;                                  //风控结论/最终结论/综合意见
    private int needSupplyMaterial;                                  //需要补充材料 1: 需要, 0: 不需要

    @Size(max = 5000, message = "补充材料说明不能超过 {max} 个字符", groups = {SaveFinanceRiskManagerInfo.class, SubmitFinanceRiskManagerInfo.class})
    private String supplyMaterialIntroduce;                          //补充材料说明
    private Integer approveStateId;                                  //审批状态Id  0:审核不通过, 1:审核通过
    private String approveState;                                     //审批状态
    List<AttachmentObject> attachmentList1;                          //附件列表
    List<AttachmentObject> attachmentList2;                          //补充材料/附件列表


}
