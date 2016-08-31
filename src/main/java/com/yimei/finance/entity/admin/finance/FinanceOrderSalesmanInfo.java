package com.yimei.finance.entity.admin.finance;

import com.yimei.finance.representation.common.basic.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "t_finance_order_salesman_info")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinanceOrderSalesmanInfo extends BaseEntity implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;                                                 //主键
    @Column(name = "finance_id", nullable = false)
    private Long financeId;                                          //金融单id
    @Column(name = "contract_companies_info_supply", length = 1000)
    private String contractCompaniesInfoSupply;                      //上下游签约单位信息补充
    @Column(name = "business_model_introduce", length = 1000)
    private String businessModelIntroduce;                           //业务操作模式介绍
    @Column(name = "logistics_storage_info_supply", length = 1000)
    private String logisticsStorageInfoSupply;                       //物流仓储信息补充
    @Column(name = "other_info_supply", length = 1000)
    private String otherInfoSupply;                                  //其它补充说明
    @Column(name = "need_supply_material", nullable = false)
    private boolean needSupplyMaterial;                              //需要补充材料 true: 需要, false: 不需要
    @Column(name = "supply_material_introduce", length = 500)
    private String supplyMaterialIntroduce;                          //补充材料说明
    @Column(name = "notice_apply_user")
    private boolean noticeApplyUser;                                 //通知申请用户 true: 通知, false: 不通知
    @Column(name = "audit_status_id", length = 3)
    private int auditStatusId;                                       //审核状态id
    @Column(name = "audit_status", length = 50)
    private String auditStatus;                                      //审核状态

}
