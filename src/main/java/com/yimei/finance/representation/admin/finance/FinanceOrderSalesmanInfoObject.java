package com.yimei.finance.representation.admin.finance;

import com.yimei.finance.entity.admin.finance.AttachmentObject;
import com.yimei.finance.entity.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinanceOrderSalesmanInfoObject extends BaseEntity implements Serializable {
    private Long id;                                                 //主键
    private Long financeId;                                          //金融单id
    private String contractCompaniesInfoSupply;                      //上下游签约单位信息补充
    private String businessModelIntroduce;                           //业务操作模式介绍
    private String logisticsStorageInfoSupply;                       //物流仓储信息补充
    private String otherInfoSupply;                                  //其它补充说明
    private int needSupplyMaterial;                                  //需要补充材料 1: 需要, 0: 不需要
    private String supplyMaterialIntroduce;                          //补充材料说明
    private int noticeApplyUser;                                     //通知申请用户 1: 通知, 0: 不通知
    private List<AttachmentObject> attachmentList;                   //附件列表

}
