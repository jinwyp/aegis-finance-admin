package com.yimei.finance.representation.admin.finance.object;

import com.yimei.finance.entity.common.BaseEntity;
import com.yimei.finance.representation.admin.finance.object.validated.SaveFinanceSalesmanInfo;
import com.yimei.finance.representation.admin.finance.object.validated.SubmitFinanceSalesmanInfo;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@ApiModel(description = "金融-业务员填写信息对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinanceOrderSalesmanInfoObject extends BaseEntity implements Serializable {
    private Long id;                                                 //主键
    private Long financeId;                                          //金融单id
    @Size(max = 1000, message = "上下游签约单位补充信息不能超过 {max} 个字符", groups = {SaveFinanceSalesmanInfo.class, SubmitFinanceSalesmanInfo.class})
    private String contractCompaniesInfoSupply;                      //上下游签约单位信息补充

    @Size(max = 1000, message = "业务操作模式介绍不能超过 {max} 个字符", groups = {SaveFinanceSalesmanInfo.class, SubmitFinanceSalesmanInfo.class})
    private String businessModelIntroduce;                           //业务操作模式介绍

    @Size(max = 1000, message = "物流仓储补充信息不能超过 {max} 个字符", groups = {SaveFinanceSalesmanInfo.class, SubmitFinanceSalesmanInfo.class})
    private String logisticsStorageInfoSupply;                       //物流仓储信息补充

    @Size(max = 1000, message = "其它补充说明不能超过 {max} 个字符", groups = {SaveFinanceSalesmanInfo.class, SubmitFinanceSalesmanInfo.class})
    private String otherInfoSupply;                                  //其它补充说明
    private int needSupplyMaterial;                                  //需要补充材料 1: 需要, 0: 不需要

    @Size(max = 500, message = "补充材料说明不能超过 {max} 个字符", groups = {SaveFinanceSalesmanInfo.class, SubmitFinanceSalesmanInfo.class})
    private String supplyMaterialIntroduce;                          //补充材料说明
    private String approveState;                                     //审批状态
    private Integer approveStateId;                                  //审批状态Id  0:审核不通过, 1:审核通过
    private List<AttachmentObject> attachmentList1;                  //附件列表

}
