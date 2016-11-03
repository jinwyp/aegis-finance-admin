package com.yimei.finance.entity.admin.finance;

import com.yimei.finance.entity.common.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "t_finance_order")
@ApiModel(value = "financeOrder", description = "金融申请单对象")
@Data
@NoArgsConstructor
public class FinanceOrder extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Long id;                                                 //主键

    @Column(name = "source_id")
    private String sourceId;                                         //流水号，编号

    @Column(name = "user_id")
    private int userId;                                              //申请人用户id

    @Column(name = "apply_user_name")
    private String applyUserName;                                    //申请人姓名

    @Column(name = "apply_user_phone")
    private String applyUserPhone;                                   //申请人手机号

    @Column(name = "apply_company_id")
    private Long applyCompanyId;                                     //申请人公司id

    @Column(name = "apply_company_name")
    private String applyCompanyName;                                 //申请公司名称

    @Column(name = "apply_type")
    private String applyType;                                        //申请类型(煤易融：MYR 煤易贷: MYD 煤易购: MYG)

    @Column(name = "financing_amount")
    private BigDecimal financingAmount;                              //拟融资金额（单位：万元）

    @Column(name = "expect_date")
    private int expectDate;                                          //拟使用资金时间（单位：天）

    @Column(name = "business_amount")
    private BigDecimal businessAmount;                               //预期此笔业务量（单位：万吨）

    @Column(name = "transport_mode")
    private String transportMode;                                    //运输方式：海运\汽运\火运\其他

    @Column(name = "procurement_price")
    private BigDecimal procurementPrice;                             //单吨采购价 (元/吨)

    @Column(name = "upstream_resource")
    private String upstreamResource;                                 //上游资源方全称

    @Column(name = "transfer_port")
    private String transferPort;                                     //中转港口/地全称

    @Column(name = "comments")
    private String comments;                                         //备注说明

    @Column(name = "our_contract_company")
    private String ourContractCompany;                               //签约单位全称/我方签约公司

    @Column(name = "downstream_contract_company")
    private String downstreamContractCompany;                        //下游签约单位

    @Column(name = "terminal_server")
    private String terminalServer;                                   //用煤终端

    @Column(name = "selling_price")
    private BigDecimal sellingPrice;                                 //预计单吨销售价 (元/吨)

    @Column(name = "storage_location")
    private String storageLocation;                                  //煤炭仓储地

    @Column(name = "coal_source")
    private String coalSource;                                       //煤炭来源

    @Column(name = "market_price")
    private BigDecimal marketPrice;                                  //单吨市场报价（元／吨）

    @Column(name = "approve_state")
    private String approveState;                                     //审批状态

    @Column(name = "approve_state_id")
    private Integer approveStateId;                                  //审批状态Id

    @Column(name = "coal_quantity_index")
    private String coalQuantityIndex;                                //主要煤质指标

    @Column(name = "end_time")
    private Date endTime;                                            //结束时间

    @Column(name = "business_company_id")
    private Long businessCompanyId;                                  //业务线(公司)id

}
