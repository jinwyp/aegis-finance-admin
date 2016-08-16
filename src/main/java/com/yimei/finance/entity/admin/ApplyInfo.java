package com.yimei.finance.entity.admin;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by zhangbolun on 16/8/16.
 */
@Entity
@Table(name = "T_finance_apply_info")
@Data
public class ApplyInfo implements Serializable {
    @Id
    @GeneratedValue
    private int id ; //主键
    private int userId ; //用户id
    private String applyType; //申请类型(煤易融：MYR 煤易贷: MYD 煤易购: MYG)
    private BigDecimal financingAmount; // 拟融资金额（单位：万元）
    private int expectedDate; //拟使用资金时间（单位：天）
    private BigDecimal businessAmount; //预期此笔业务量（单位：万吨）
    private String transportMode; //运输方式：海运\汽运\火运\其他
    private BigDecimal procurementPrice; //单吨采购价 (元/吨)
    private String upstreamResource; //上游资源方全称
    private String transferPort; //中转港口/地全称
    private String comments; //备注说明
    private String contractors; //签约单位全称
    private String downstreamContractors; //下游签约单位全称
    private String terminalServer; //用煤终端
    private BigDecimal sellingPrice; //预计单吨销售价 (元/吨)
    private String storageLocation; //煤炭仓储地
    private String coalSources; //煤炭来源
    private BigDecimal marketPrice; //单吨市场报价（元／吨）
    private String approveState; //审批状态
    private String approveComments; //审批说明
    private String souceId; //流水号，编号
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime applyDateTime; //申请时间
    private String applyUserName; //申请人姓名
    private String applyCompanyName; //申请公司名称
    private String workFlowInstanceId; //工作流实例id
}
