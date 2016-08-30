package com.yimei.finance.entity.admin.finance;

import com.yimei.finance.entity.common.basic.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Table(name = "t_finance_order_supervisor_info")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinanceOrderSupervisorInfo extends BaseEntity implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;                                                 //主键
    @Column(name = "finance_id", nullable = false)
    private Long financeId;                                          //金融单id
    @Column(name = "storage_place_name", length = 100)
    private String storagePlaceName;                                 //仓储地名称
    @Column(name = "storage_property", length = 100)
    private String storageProperty;                                  //仓储性质
    @Column(name = "storage_address", length = 200)
    private String storageAddress;                                   //仓储地地址
    @Column(name = "historical_cooperation_detail", length = 1000)
    private String historicalCooperationDetail;                      //历史合作情况
    @Column(name = "operating_storage_detail", length = 1000)
    private String operatingStorageDetail;                           //经营及堆存情况
    @Column(name = "port_standard_degree", length = 1000)
    private String portStandardDegree;                               //保管及进出口流程规范程度
    @Column(name = "supervision_cooperate_detail", length = 1000)
    private String supervisionCooperateDetail;                       //监管配合情况
    @Column(name = "supervision_plan", length = 1000)
    private String supervisionPlan;                                  //监管方案
    @Column(name = "final_conclusion", length = 1000)
    private String finalConclusion;                                  //最终结论/综合意见
    @Column(name = "need_supply_material", nullable = false)
    private boolean needSupplyMaterial;                              //需要补充材料 true: 需要, false: 不需要
    @Column(name = "supply_material_introduce", length = 500)
    private String supplyMaterialIntroduce;                          //补充材料说明
    @Column(name = "notice_apply_user")
    private boolean noticeApplyUser;                                 //通知申请用户 true: 通知, false: 不通知
    @Column(name = "notice_salesman")
    private boolean noticeSalesman;                                  //通知业务员   true: 通知, false: 不通知
    @Column(name = "audit_status_id", length = 3)
    private int auditStatusId;                                       //审核状态id
    @Column(name = "audit_status", length = 50)
    private String auditStatus;                                      //审核状态
    @Transient
    List<AttachmentObject> attachmentList;                           //附件列表



}
