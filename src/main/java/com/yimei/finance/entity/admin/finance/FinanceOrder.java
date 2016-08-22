package com.yimei.finance.entity.admin.finance;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by zhangbolun on 16/8/16.
 */
@Entity
@Table(name = "t_finance_order")
@ApiModel(value = "financeOrder", description = "金融申请单对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinanceOrder implements Serializable {
    @Id
    @Column(name = "id", length = 11)
    @GeneratedValue
    private Long id;                                                 //主键
    @Column(name = "user_id", length = 11)
    private int userId;                                              //申请人用户id
    @Column(name = "apply_type", length = 20)
    private String applyType;                                        //申请类型(煤易融：MYR 煤易贷: MYD 煤易购: MYG)
    @Column(name = "financing_amount", length = 20)
    private BigDecimal financingAmount;                              //拟融资金额（单位：万元）
    @Column(name = "expect_date", length = 10)
    private int expectDate;                                          //拟使用资金时间（单位：天）
    @Column(name = "business_amount", length = 10)
    private BigDecimal businessAmount;                               //预期此笔业务量（单位：万吨）
    @Column(name = "transport_mode", length = 30)
    private String transportMode;                                    //运输方式：海运\汽运\火运\其他
    @Column(name = "procurement_price", length = 10)
    private BigDecimal procurementPrice;                             //单吨采购价 (元/吨)
    @Column(name = "upstream_resource", length = 100)
    private String upstreamResource;                                 //上游资源方全称
    @Column(name = "transfer_port", length = 100)
    private String transferPort;                                     //中转港口/地全称
    @Column(name = "comments", length = 1000)
    private String comments;                                         //备注说明
    @Column(name = "contractor", length = 100)
    private String contractor;                                       //签约单位全称
    @Column(name = "downstream_contractor", length = 100)
    private String downstreamContractor;                             //下游签约单位全称
    @Column(name = "terminal_server", length = 100)
    private String terminalServer;                                   //用煤终端
    @Column(name = "selling_price", length = 10)
    private BigDecimal sellingPrice;                                 //预计单吨销售价 (元/吨)
    @Column(name = "storage_location", length = 100)
    private String storageLocation;                                  //煤炭仓储地
    @Column(name = "coal_source", length = 100)
    private String coalSource;                                       //煤炭来源
    @Column(name = "market_price", length = 10)
    private BigDecimal marketPrice;                                  //单吨市场报价（元／吨）
    @Column(name = "approve_state", length = 30)
    private String approveState;                                     //审批状态
    @Column(name = "source_id", length = 100)
    private String sourceId;                                         //流水号，编号
//    @Column(name = "apply_date_time")
//    private LocalDateTime applyDateTime;                             //申请时间
    @Column(name = "apply_user_name", length = 50)
    private String applyUserName;                                    //申请人姓名
    @Column(name = "apply_company_name", length = 50)
    private String applyCompanyName;                                 //申请公司名称

//    @Temporal(TemporalType.TIMESTAMP)
//    public LocalDateTime getApplyDateTime() {
//        return applyDateTime;
//    }
}