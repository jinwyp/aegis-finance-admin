package com.yimei.finance.entity.admin.finance;

import com.yimei.finance.entity.common.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "t_finance_order_riskmanager_info")
@Entity
@Data
@NoArgsConstructor
public class FinanceOrderRiskManagerInfo extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Long id;                                                 //主键

    @Column(name = "finance_id")
    private Long financeId;                                          //金融单id

    @Column(name = "distribution_ability_eval")
    private String distributionAbilityEval;                          //分销能力评估

    @Column(name = "payment_situation_eval")
    private String paymentSituationEval;                             //预计回款情况

    @Column(name = "business_risk_point")
    private String businessRiskPoint;                                //业务风险点

    @Column(name = "risk_control_scheme")
    private String riskControlScheme;                                //风险控制方案

    @Column(name = "final_conclusion")
    private String finalConclusion;                                  //风控结论/最终结论/综合意见

    @Column(name = "need_supply_material")
    private int needSupplyMaterial;                                  //需要补充材料 1: 需要, 0: 不需要

    @Column(name = "supply_material_introduce")
    private String supplyMaterialIntroduce;                          //补充材料说明

    @Column(name = "approve_state")
    private String approveState;                                     //审批状态

    @Column(name = "approve_state_id")
    private Integer approveStateId;                                  //审批状态Id

}

