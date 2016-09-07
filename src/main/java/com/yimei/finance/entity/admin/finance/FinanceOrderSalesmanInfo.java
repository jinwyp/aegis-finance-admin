package com.yimei.finance.entity.admin.finance;

import com.yimei.finance.entity.common.BaseEntity;
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
    @Column(name = "id", unique = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;                                                 //主键
    @Column(name = "finance_id", nullable = false, updatable = false, unique = true)
    private Long financeId;                                          //金融单id
    @Column(name = "contract_companies_info_supply", length = 1020)
    private String contractCompaniesInfoSupply;                      //上下游签约单位信息补充
    @Column(name = "business_model_introduce", length = 1020)
    private String businessModelIntroduce;                           //业务操作模式介绍
    @Column(name = "logistics_storage_info_supply", length = 1020)
    private String logisticsStorageInfoSupply;                       //物流仓储信息补充
    @Column(name = "other_info_supply", length = 1020)
    private String otherInfoSupply;                                  //其它补充说明
    @Column(name = "need_supply_material", length = 1, nullable = false)
    private int needSupplyMaterial;                                  //需要补充材料 1: 需要, 0: 不需要
    @Column(name = "supply_material_introduce", length = 520)
    private String supplyMaterialIntroduce;                          //补充材料说明

}
