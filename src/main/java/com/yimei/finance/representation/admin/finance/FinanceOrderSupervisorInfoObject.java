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
public class FinanceOrderSupervisorInfoObject extends BaseEntity implements Serializable {
    private Long id;                                                 //主键
    private Long financeId;                                          //金融单id

    @Size(max = 100, message = "煤炭仓储地不能超过 {max} 个字符")
    private String storageLocation;                                  //煤炭仓储地

    @Size(max = 100, message = "仓储地性质不能超过 {max} 个字符")
    private String storageProperty;                                  //仓储性质

    @Size(max = 200, message = "仓储地地址不能超过 {max} 个字符")
    private String storageAddress;                                   //仓储地地址

    @Size(max = 1000, message = "历史合作情况不能超过 {max} 个字符")
    private String historicalCooperationDetail;                      //历史合作情况

    @Size(max = 1000, message = "经营及堆存情况不能超过 {max} 个字符")
    private String operatingStorageDetail;                           //经营及堆存情况

    @Size(max = 1000, message = "保管及进出口流程规范不能超过 {max} 个字符")
    private String portStandardDegree;                               //保管及进出口流程规范程度

    @Size(max = 1000, message = "监管配合情况不能超过 {max} 个字符")
    private String supervisionCooperateDetail;                       //监管配合情况

    @Size(max = 1000, message = "监管方案不能超过 {max} 个字符")
    private String supervisionScheme;                                //监管方案

    @Size(max = 1000, message = "综合意见不能超过 {max} 个字符")
    private String finalConclusion;                                  //最终结论/综合意见
    private int needSupplyMaterial;                                  //需要补充材料 1: 需要, 0: 不需要

    @Size(max = 500, message = "补充材料说明不能超过 {max} 个字符")
    private String supplyMaterialIntroduce;                          //补充材料说明
    private int noticeApplyUser;                                     //通知申请用户 1: 通知, 0: 不通知
    private int noticeSalesman;                                      //通知业务员   1: 通知, 0: 不通知
    List<AttachmentObject> attachmentList;                           //附件列表



}
