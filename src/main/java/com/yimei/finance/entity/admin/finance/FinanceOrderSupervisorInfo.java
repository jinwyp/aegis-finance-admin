package com.yimei.finance.entity.admin.finance;

import com.yimei.finance.entity.common.BaseEntity;
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
    @Column(name = "finance_id", nullable = false, updatable = false, unique = true)
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
    @Column(name = "supervision_scheme", length = 1000)
    private String supervisionScheme;                                //监管方案
    @Column(name = "final_conclusion", length = 1000)
    private String finalConclusion;                                  //最终结论/综合意见
    @Column(name = "need_supply_material", length = 1, nullable = false)
    private int needSupplyMaterial;                                  //需要补充材料 1: 需要, 0: 不需要
    @Column(name = "supply_material_introduce", length = 500)
    private String supplyMaterialIntroduce;                          //补充材料说明
    @Column(name = "notice_apply_user", length = 1, nullable = false)
    private int noticeApplyUser;                                     //通知申请用户 1: 通知, 0: 不通知
    @Column(name = "notice_salesman", length = 1, nullable = false)
    private int noticeSalesman;                                      //通知业务员   1: 通知, 0: 不通知
    @Transient
    List<AttachmentObject> attachmentList;                           //附件列表



}
