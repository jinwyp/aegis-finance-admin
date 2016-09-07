package com.yimei.finance.representation.admin.finance;

import com.yimei.finance.entity.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinanceOrderRiskManagerInfoObject extends BaseEntity implements Serializable {
    private Long id;                                                 //主键
    private Long financeId;                                          //金融单id

    @Size(max = 1000, message = "分销能力评估不能超过 {max} 个字符")
    private String distributionAbilityEval;                          //分销能力评估

    @Size(max = 1000, message = "预计回款情况不能超过 {max} 个字符")
    private String paymentSituationEval;                             //预计回款情况

    @Size(max = 1000, message = "业务风险点不能超过 {max} 个字符")
    private String businessRiskPoint;                                //业务风险点

    @Size(max = 1000, message = "风险控制方案不能超过 {max} 个字符")
    private String riskControlScheme;                                //风险控制方案

    @Size(max = 1000, message = "风控结论不能超过 {max} 个字符")
    private String finalConclusion;                                  //风控结论/最终结论/综合意见
    private int needSupplyMaterial;                                  //需要补充材料 1: 需要, 0: 不需要

    @Size(max = 500, message = "补充材料说明不能超过 {max} 个字符")
    private String supplyMaterialIntroduce;                          //补充材料说明
    private int noticeApplyUser;                                     //通知申请用户 1: 通知, 0: 不通知
    private int noticeSalesman;                                      //通知业务员   1: 通知, 0: 不通知
    private int editContract;                                        //编辑合同     1: 需要编辑, 0: 不需要编辑
    List<AttachmentObject> attachmentList;                           //附件列表

}
