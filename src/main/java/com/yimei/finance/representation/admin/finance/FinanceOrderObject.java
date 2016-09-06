package com.yimei.finance.representation.admin.finance;

import com.yimei.finance.entity.common.BaseEntity;
import com.yimei.finance.representation.admin.finance.validated.CreateFinanceOrder;
import com.yimei.finance.representation.admin.finance.validated.EditFinanceOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.util.StringUtils;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinanceOrderObject extends BaseEntity implements Serializable {
    private Long id;                                                 //主键
    private int userId;                                              //申请人用户id
    @Size(min = 3, max = 10, message = "申请类型字段应在3-10个字符之间", groups = {CreateFinanceOrder.class})
    @NotBlank(message = "申请类型字段不能为空", groups = {CreateFinanceOrder.class})
    private String applyType;                                        //申请类型(煤易融：MYR 煤易贷: MYD 煤易购: MYG)
    private String applyTypeName;

    @Digits(integer = 6, fraction = 2, message = "融资金额最大支持 {integer}位整数, {fraction}位小数", groups = {EditFinanceOrder.class})
    @DecimalMin(value = "1", inclusive = true, message = "融资金额不能低于 {value} 万元", groups = {EditFinanceOrder.class})
    @DecimalMax(value = "100000", inclusive = true, message = "融资金额不能超过 {value} 万元", groups = {EditFinanceOrder.class})
    @NotBlank(message = "融资金额不能为空", groups = {EditFinanceOrder.class})
    private BigDecimal financingAmount;                              //拟融资金额（单位：万元）

    @Range(min = 1, max = 3650, message = "资金使用时间应在 {min}-{max} 天之间", groups = {EditFinanceOrder.class})
    @NotBlank(message = "资金使用时间不能为空", groups = {EditFinanceOrder.class})
    private int expectDate;                                          //拟使用资金时间（单位：天）

    @Digits(integer = 6, fraction = 2, message = "预期业务量最大支持 {integer}位整数, {fraction}位小数", groups = {EditFinanceOrder.class})
    @DecimalMin(value = "0.1", inclusive = true, message = "预期业务量不能低于 {value} 万吨", groups = {EditFinanceOrder.class})
    @DecimalMax(value = "100000", inclusive = true, message = "预期业务量不能超过 {value} 万吨", groups = {EditFinanceOrder.class})
    @NotBlank(message = "预期业务量不能为空", groups = {EditFinanceOrder.class})
    private BigDecimal businessAmount;                               //预期此笔业务量（单位：万吨）

    @Size(min = 2, max = 10, message = "运输方式应在 {min}-{max} 个字符之间", groups = {EditFinanceOrder.class})
    @NotBlank(message = "运输方式不能为空", groups = {EditFinanceOrder.class})
    private String transportMode;                                    //运输方式：海运\汽运\火运\其他

    @Digits(integer = 4, fraction = 2, message = "单吨采购价最大支持 {integer}位整数, {fraction}位小数", groups = {EditFinanceOrder.class})
    @NotBlank(message = "单吨采购价不能为空", groups = {EditFinanceOrder.class})
    private BigDecimal procurementPrice;                             //单吨采购价 (元/吨)

    @Size(min = 1, max = 100, message = "上游资源方全称应在 {min}-{max} 个字符之间")
    private String upstreamResource;                                 //上游资源方全称
    private String transferPort;                                     //中转港口/地全称
    private String comments;                                         //备注说明
    private String ourContractCompany;                               //签约单位全称/我方签约公司
    private String downstreamContractCompany;                        //下游签约单位
    private String terminalServer;                                   //用煤终端
    private BigDecimal sellingPrice;                                 //预计单吨销售价 (元/吨)
    private String storageLocation;                                  //煤炭仓储地
    private String coalSource;                                       //煤炭来源
    private BigDecimal marketPrice;                                  //单吨市场报价（元／吨）
    private String approveState;                                     //审批状态
    private int approveStateId;                                      //审批状态Id
    private String sourceId;                                         //流水号，编号
    private String applyUserName;                                    //申请人姓名
    private String applyUserPhone;                                   //申请人手机号
    private String applyCompanyName;                                 //申请公司名称
    private String coalQuantityIndex;                                //主要煤质指标
    private Date endTime;                                            //结束时间
    private List<AttachmentObject> attachmentList;                   //附件列表

    public String getApplyTypeName() {
        if (StringUtils.isEmpty(applyType)) {
            return applyType;
        } else if (applyType.equals("MYR")) {
            return "煤易融";
        } else if (applyType.equals("MYD")) {
            return "煤易贷";
        } else if (applyType.equals("MYG")) {
            return "煤易购";
        } else {
            return applyType;
        }
    }

}
