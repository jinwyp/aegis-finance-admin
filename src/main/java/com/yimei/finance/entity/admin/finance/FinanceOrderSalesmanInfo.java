package com.yimei.finance.entity.admin.finance;

import com.yimei.finance.entity.common.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "t_finance_order_salesman_info")
@Entity
@Data
@NoArgsConstructor
public class FinanceOrderSalesmanInfo extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Long id;                                                 //主键

    @Column(name = "finance_id")
    private Long financeId;                                          //金融单id

    @Column(name = "contract_companies_info_supply")
    private String contractCompaniesInfoSupply;                      //上下游签约单位信息补充

    @Column(name = "business_model_introduce")
    private String businessModelIntroduce;                           //业务操作模式介绍

    @Column(name = "logistics_storage_info_supply")
    private String logisticsStorageInfoSupply;                       //物流仓储信息补充

    @Column(name = "other_info_supply")
    private String otherInfoSupply;                                  //其它补充说明

    @Column(name = "need_supply_material")
    private int needSupplyMaterial;                                  //需要补充材料 1: 需要, 0: 不需要

    @Column(name = "supply_material_introduce")
    private String supplyMaterialIntroduce;                          //补充材料说明

    @Column(name = "approve_state")
    private String approveState;                                     //审批状态

    @Column(name = "approve_state_id")
    private Integer approveStateId;                                  //审批状态Id

}
