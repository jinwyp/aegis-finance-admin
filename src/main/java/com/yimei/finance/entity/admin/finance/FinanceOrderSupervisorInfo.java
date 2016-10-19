package com.yimei.finance.entity.admin.finance;

import com.yimei.finance.entity.common.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "t_finance_order_supervisor_info")
@Entity
@Data
@NoArgsConstructor
public class FinanceOrderSupervisorInfo extends BaseEntity implements Serializable {
    @Id
    @Column(name = "id")
    private Long id;                                                 //主键

    @Column(name = "finance_id")
    private Long financeId;                                          //金融单id

    @Column(name = "storage_location")
    private String storageLocation;                                  //仓储地名称

    @Column(name = "storage_property")
    private String storageProperty;                                  //仓储性质

    @Column(name = "storage_address")
    private String storageAddress;                                   //仓储地地址

    @Column(name = "historical_cooperation_detail")
    private String historicalCooperationDetail;                      //历史合作情况

    @Column(name = "operating_storage_detail")
    private String operatingStorageDetail;                           //经营及堆存情况

    @Column(name = "port_standard_degree")
    private String portStandardDegree;                               //保管及进出口流程规范程度

    @Column(name = "supervision_cooperate_detail")
    private String supervisionCooperateDetail;                       //监管配合情况

    @Column(name = "supervision_scheme")
    private String supervisionScheme;                                //监管方案

    @Column(name = "final_conclusion")
    private String finalConclusion;                                  //最终结论/综合意见

    @Column(name = "need_supply_material")
    private int needSupplyMaterial;                                  //需要补充材料 1: 需要, 0: 不需要

    @Column(name = "supply_material_introduce")
    private String supplyMaterialIntroduce;                          //补充材料说明

    @Column(name = "approve_state")
    private String approveState;                                     //审批状态

    @Column(name = "approve_state_id")
    private Integer approveStateId;                                  //审批状态Id

}
