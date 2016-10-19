package com.yimei.finance.entity.admin.finance;

import com.yimei.finance.entity.common.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "t_finance_order_investigator_info")
@Entity
@Data
@NoArgsConstructor
public class FinanceOrderInvestigatorInfo extends BaseEntity implements Serializable {
    @Id
    @Column(name = "id")
    private Long id;                                                 //主键

    @Column(name = "finance_id")
    private Long financeId;                                          //金融单id

    @Column(name = "apply_company_name")
    private String applyCompanyName;                                 //申请公司/融资方

    @Column(name = "our_contract_company")
    private String ourContractCompany;                               //我方签约公司

    @Column(name = "upstream_contract_company")
    private String upstreamContractCompany;                          //上游签约单位

    @Column(name = "downstream_contract_company")
    private String downstreamContractCompany;                        //下游签约单位

    @Column(name = "terminal_server")
    private String terminalServer;                                   //用煤终端

    @Column(name = "transport_party")
    private String transportParty;                                   //运输方

    @Column(name = "transit_port")
    private String transitPort;                                      //中转港口

    @Column(name = "quality_inspection_unit")
    private String qualityInspectionUnit;                            //质量检验单位

    @Column(name = "quantity_inspection_unit")
    private String quantityInspectionUnit;                           //数量检验单位

    @Column(name = "financing_amount")
    private BigDecimal financingAmount;                              //融资金额

    @Column(name = "financing_period")
    private int financingPeriod;                                     //融资期限

    @Column(name = "interest_rate")
    private BigDecimal interestRate;                                 //利率

    @Column(name = "business_start_time")
    private Date businessStartTime;                                  //业务开始时间

    @Column(name = "historical_cooperation_detail")
    private String historicalCooperationDetail;                      //历史合作情况

    @Column(name = "main_business_information")
    private String mainBusinessInfo;                                 //业务主要信息

    @Column(name = "business_transfer_info")
    private String businessTransferInfo;                             //业务流转信息

    @Column(name = "business_risk_point")
    private String businessRiskPoint;                                //业务风险点

    @Column(name = "performance_credit_ability_eval")
    private String performanceCreditAbilityEval;                     //履约信用及能力评估

    @Column(name = "final_conclusion")
    private String finalConclusion;                                  //综合意见/最终结论

    @Column(name = "need_supply_material")
    private int needSupplyMaterial;                                  //需要补充材料 1: 需要, 0: 不需要

    @Column(name = "supply_material_introduce")
    private String supplyMaterialIntroduce;                          //补充材料说明

    @Column(name = "approve_state")
    private String approveState;                                     //审批状态

    @Column(name = "approve_state_id")
    private Integer approveStateId;                                  //审批状态Id

}
