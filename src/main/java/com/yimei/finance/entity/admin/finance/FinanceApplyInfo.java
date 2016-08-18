package com.yimei.finance.entity.admin.finance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by zhangbolun on 16/8/16.
 */
@Entity
@Table(name = "T_finance_apply_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinanceApplyInfo implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id ; //主键
    @Column(name="user_id")
    private int userId ; //用户id
    @Column(name="apply_type")
    private String applyType; //申请类型(煤易融：MYR 煤易贷: MYD 煤易购: MYG)
    @Column(name="financing_amount")
    private BigDecimal financingAmount; // 拟融资金额（单位：万元）
    @Column(name="expected_date")
    private int expected_date; //拟使用资金时间（单位：天）
    @Column(name="business_amount")
    private BigDecimal businessAmount; //预期此笔业务量（单位：万吨）
    @Column(name="transport_mode")
    private String transportMode; //运输方式：海运\汽运\火运\其他
    @Column(name="procurement_price")
    private BigDecimal procurement_price; //单吨采购价 (元/吨)
    @Column(name="upstream_resource")
    private String upstreamResource; //上游资源方全称
    @Column(name="transfer_port")
    private String transferPort; //中转港口/地全称
    @Column(name="comments")
    private String comments; //备注说明
    @Column(name="contractors")
    private String contractors; //签约单位全称
    @Column(name="downstream_contractors")
    private String downstreamContractors; //下游签约单位全称
    @Column(name="terminal_server")
    private String terminalServer; //用煤终端
    @Column(name="selling_price")
    private BigDecimal sellingPrice; //预计单吨销售价 (元/吨)
    @Column(name="storage_location")
    private String storageLocation; //煤炭仓储地
    @Column(name="coal_sources")
    private String coalSources; //煤炭来源
    @Column(name="market_price")
    private BigDecimal marketPrice; //单吨市场报价（元／吨）
    @Column(name="approve_state")
    private String approveState; //审批状态
    @Column(name="source_id")
    private String sourceId; //流水号，编号
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name="apply_date_ime")
    private LocalDateTime applyDateTime; //申请时间
    @Column(name="apply_user_name")
    private String applyUserName; //申请人姓名
    @Column(name="apply_company_name")
    private String applyCompanyName; //申请公司名称
    @Column(name="work_flow_instance_id")
    private String workFlowInstanceId; //工作流实例id
}
