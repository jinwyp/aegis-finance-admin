package com.yimei.finance.entity.admin.finance;

import com.yimei.finance.entity.common.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class FinanceOrder extends BaseEntity implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;                                                 //主键
    @Column(name = "user_id", length = 11, nullable = false, updatable = false)
    private int userId;                                              //申请人用户id
    @Column(name = "apply_type", length = 20, nullable = false, updatable = false)
    private String applyType;                                        //申请类型(煤易融：MYR 煤易贷: MYD 煤易购: MYG)
    @Column(name = "financing_amount", precision = 20, scale = 2)
    private BigDecimal financingAmount;                              //拟融资金额（单位：万元）
    @Column(name = "expect_date", length = 10, nullable = false)
    private int expectDate;                                          //拟使用资金时间（单位：天）
    @Column(name = "business_amount", precision = 20, scale = 2)
    private BigDecimal businessAmount;                               //预期此笔业务量（单位：万吨）
    @Column(name = "transport_mode", length = 30)
    private String transportMode;                                    //运输方式：海运\汽运\火运\其他
    @Column(name = "procurement_price", precision = 10, scale = 2)
    private BigDecimal procurementPrice;                             //单吨采购价 (元/吨)
    @Column(name = "upstream_resource", length = 120)
    private String upstreamResource;                                 //上游资源方全称
    @Column(name = "transfer_port", length = 120)
    private String transferPort;                                     //中转港口/地全称
    @Column(name = "comments", length = 1020)
    private String comments;                                         //备注说明
    @Column(name = "our_contract_company", length = 120)
    private String ourContractCompany;                               //签约单位全称/我方签约公司
    @Column(name = "downstream_contract_company", length = 120)
    private String downstreamContractCompany;                        //下游签约单位
    @Column(name = "terminal_server", length = 120)
    private String terminalServer;                                   //用煤终端
    @Column(name = "selling_price", precision = 10, scale = 2)
    private BigDecimal sellingPrice;                                 //预计单吨销售价 (元/吨)
    @Column(name = "storage_location", length = 120)
    private String storageLocation;                                  //煤炭仓储地
    @Column(name = "coal_source", length = 120)
    private String coalSource;                                       //煤炭来源
    @Column(name = "market_price", precision = 10, scale = 2)
    private BigDecimal marketPrice;                                  //单吨市场报价（元／吨）
    @Column(name = "approve_state", length = 30, nullable = false)
    private String approveState;                                     //审批状态
    @Column(name = "approve_state_id", length = 3, nullable = false)
    private int approveStateId;                                      //审批状态Id
    @Column(name = "source_id", length = 100, nullable = false, updatable = false, unique = true)
    private String sourceId;                                         //流水号，编号
    @Column(name = "apply_user_name", length = 50)
    private String applyUserName;                                    //申请人姓名
    @Column(name = "apply_user_phone", length = 50)
    private String applyUserPhone;                                   //申请人手机号
    @Column(name = "apply_company_name", length = 50, nullable = false, updatable = false)
    private String applyCompanyName;                                 //申请公司名称
    @Column(name = "coal_quantity_index", length = 520)
    private String coalQuantityIndex;                                //主要煤质指标
    @Column(name = "end_time", updatable = false)
    private Date endTime;                                            //结束时间


}
