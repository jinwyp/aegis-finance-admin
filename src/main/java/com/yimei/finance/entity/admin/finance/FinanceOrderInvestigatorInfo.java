package com.yimei.finance.entity.admin.finance;

import com.yimei.finance.entity.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "t_finance_order_investigator_info")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinanceOrderInvestigatorInfo extends BaseEntity implements Serializable {
    @Id
    @Column(name = "id", unique = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;                                                 //主键

    @Column(name = "finance_id", nullable = false, updatable = false, unique = true)
    private Long financeId;                                          //金融单id

    @Column(name = "apply_company_name", length = 120)
    private String applyCompanyName;                                 //申请公司/融资方

    @Column(name = "our_contract_company", length = 120)
    private String ourContractCompany;                               //我方签约公司

    @Column(name = "upstream_contract_company", length = 120)
    private String upstreamContractCompany;                          //上游签约单位

    @Column(name = "downstream_contract_company", length = 120)
    private String downstreamContractCompany;                        //下游签约单位

    @Column(name = "terminal_server", length = 120)
    private String terminalServer;                                   //用煤终端

    @Column(name = "transport_party", length = 120)
    private String transportParty;                                   //运输方

    @Column(name = "transit_port", length = 120)
    private String transitPort;                                      //中转港口

    @Column(name = "quality_inspection_unit", length = 120)
    private String qualityInspectionUnit;                            //质量检验单位

    @Column(name = "quantity_inspection_unit", length = 120)
    private String quantityInspectionUnit;                           //数量检验单位

    @Column(name = "financing_amount", precision = 20, scale = 2)
    private BigDecimal financingAmount;                              //融资金额

    @Column(name = "financing_period", length = 5)
    private int financingPeriod;                                     //融资期限

    @Column(name = "interest_rate", precision = 5, scale = 2)
    private BigDecimal interestRate;                                 //利率

    @Column(name = "business_start_time", columnDefinition = "DATE")
    private Date businessStartTime;                                  //业务开始时间

    @Column(name = "historical_cooperation_detail", length = 5020, columnDefinition = "TEXT")
    private String historicalCooperationDetail;                      //历史合作情况

    @Column(name = "main_business_information", length = 5020, columnDefinition = "TEXT")
    private String mainBusinessInfo;                                 //业务主要信息

    @Column(name = "business_transfer_info", length = 5020, columnDefinition = "TEXT")
    private String businessTransferInfo;                             //业务流转信息

    @Column(name = "business_risk_point", length = 5020, columnDefinition = "TEXT")
    private String businessRiskPoint;                                //业务风险点

    @Column(name = "performance_credit_ability_eval", length = 5020, columnDefinition = "TEXT")
    private String performanceCreditAbilityEval;                     //履约信用及能力评估

    @Column(name = "final_conclusion", length = 5020, columnDefinition = "TEXT")
    private String finalConclusion;                                  //综合意见/最终结论

    @Column(name = "need_supply_material", length = 1, nullable = false)
    private int needSupplyMaterial;                                  //需要补充材料 1: 需要, 0: 不需要

    @Column(name = "supply_material_introduce", length = 5020, columnDefinition = "TEXT")
    private String supplyMaterialIntroduce;                          //补充材料说明

    @Column(name = "approve_state", length = 30)
    private String approveState;                                     //审批状态

    @Column(name = "approve_state_id", length = 3)
    private Integer approveStateId;                                  //审批状态Id

}
