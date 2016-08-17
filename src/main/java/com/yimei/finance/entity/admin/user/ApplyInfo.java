package com.yimei.finance.entity.admin.user;

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
@Entity(name = "T_finance_apply_info")
@Table(name = "T_finance_apply_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplyInfo implements Serializable {
    @Id
    @GeneratedValue
    private Integer id ; //主键
    @Column(name="userId")
    private int userId ; //用户id
    @Column(name="applyType")
    private String applyType; //申请类型(煤易融：MYR 煤易贷: MYD 煤易购: MYG)
    @Column(name="financingAmount")
    private BigDecimal financingAmount; // 拟融资金额（单位：万元）
    @Column(name="expectedDate")
    private int expectedDate; //拟使用资金时间（单位：天）
    @Column(name="businessAmount")
    private BigDecimal businessAmount; //预期此笔业务量（单位：万吨）
    @Column(name="transportMode")
    private String transportMode; //运输方式：海运\汽运\火运\其他
    @Column(name="procurementPrice")
    private BigDecimal procurementPrice; //单吨采购价 (元/吨)
    @Column(name="upstreamResource")
    private String upstreamResource; //上游资源方全称
    @Column(name="transferPort")
    private String transferPort; //中转港口/地全称
    @Column(name="comments")
    private String comments; //备注说明
    @Column(name="contractors")
    private String contractors; //签约单位全称
    @Column(name="downstreamContractors")
    private String downstreamContractors; //下游签约单位全称
    @Column(name="terminalServer")
    private String terminalServer; //用煤终端
    @Column(name="sellingPrice")
    private BigDecimal sellingPrice; //预计单吨销售价 (元/吨)
    @Column(name="storageLocation")
    private String storageLocation; //煤炭仓储地
    @Column(name="coalSources")
    private String coalSources; //煤炭来源
    @Column(name="marketPrice")
    private BigDecimal marketPrice; //单吨市场报价（元／吨）
    @Column(name="approveState")
    private String approveState; //审批状态
    @Column(name="sourceId")
    private String sourceId; //流水号，编号
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name="applyDateTime")
    private LocalDateTime applyDateTime; //申请时间
    @Column(name="applyUserName")
    private String applyUserName; //申请人姓名
    @Column(name="applyCompanyName")
    private String applyCompanyName; //申请公司名称
    @Column(name="workFlowInstanceId")
    private String workFlowInstanceId; //工作流实例id
}
