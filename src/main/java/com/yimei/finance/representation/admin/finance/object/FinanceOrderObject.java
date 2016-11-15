package com.yimei.finance.representation.admin.finance.object;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yimei.finance.representation.admin.finance.enums.EnumFinanceOrderType;
import com.yimei.finance.representation.admin.finance.object.validated.CreateFinanceOrder;
import com.yimei.finance.representation.admin.finance.object.validated.SaveFinanceOrder;
import com.yimei.finance.representation.admin.finance.object.validated.SubmitFinanceOrder;
import com.yimei.finance.representation.common.base.BaseObject;
import com.yimei.finance.representation.common.enums.EnumCommonString;
import com.yimei.finance.representation.common.file.AttachmentObject;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@ApiModel(description = "金融单对象")
@Data
@NoArgsConstructor
public class FinanceOrderObject extends BaseObject implements Serializable {
    private Long id;                                                 //主键
    private int userId;                                              //申请人用户id
    @Size(min = 3, max = 10, message = "申请类型字段应在 {min}-{max} 个字符之间", groups = {CreateFinanceOrder.class})
    @NotBlank(message = "申请类型字段不能为空", groups = {CreateFinanceOrder.class})
    private String applyType;                                        //申请类型(煤易融：MYR 煤易贷: MYD 煤易购: MYG)
    private String applyTypeName;

    @Digits(integer = 6, fraction = 2, message = "融资金额最大支持 {integer}位整数, {fraction}位小数", groups = {SaveFinanceOrder.class, SubmitFinanceOrder.class})
    @DecimalMin(value = "1", inclusive = true, message = "融资金额不能低于 {value} 万元", groups = {SaveFinanceOrder.class, SubmitFinanceOrder.class})
    @DecimalMax(value = "100000", inclusive = true, message = "融资金额不能超过 {value} 万元", groups = {SaveFinanceOrder.class, SubmitFinanceOrder.class})
    @NotBlank(message = "融资金额不能为空", groups = {SubmitFinanceOrder.class})
    private BigDecimal financingAmount;                              //拟融资金额（单位：万元）

    @Range(min = 1, max = 3650, message = "资金使用时间应在 {min}-{max} 天之间", groups = {SaveFinanceOrder.class, SubmitFinanceOrder.class})
    @NotBlank(message = "资金使用时间不能为空", groups = {SubmitFinanceOrder.class})
    private int expectDate;                                          //拟使用资金时间（单位：天）

    @Digits(integer = 6, fraction = 2, message = "预期业务量最多支持 {integer}位整数, {fraction}位小数", groups = {SaveFinanceOrder.class, SubmitFinanceOrder.class})
    @DecimalMin(value = "0.1", inclusive = true, message = "预期业务量不能低于 {value} 万吨", groups = {SaveFinanceOrder.class, SubmitFinanceOrder.class})
    @DecimalMax(value = "100000", inclusive = true, message = "预期业务量不能超过 {value} 万吨", groups = {SaveFinanceOrder.class, SubmitFinanceOrder.class})
    private BigDecimal businessAmount;                               //预期此笔业务量（单位：万吨）

    @Size(min = 2, max = 10, message = "运输方式应在 {min}-{max} 个字符之间", groups = {SaveFinanceOrder.class, SubmitFinanceOrder.class})
    @NotBlank(message = "运输方式不能为空", groups = {SubmitFinanceOrder.class})
    private String transportMode;                                    //运输方式：海运\汽运\火运\其他

    @Digits(integer = 4, fraction = 2, message = "单吨采购价最大支持 {integer}位整数, {fraction}位小数", groups = {SaveFinanceOrder.class, SubmitFinanceOrder.class})
    @DecimalMin(value = "1", inclusive = true, message = "单吨采购价不能低于 {value} 元", groups = {SaveFinanceOrder.class, SubmitFinanceOrder.class})
    @DecimalMax(value = "100000", inclusive = true, message = "单吨采购价不能高于 {value} 元", groups = {SaveFinanceOrder.class, SubmitFinanceOrder.class})
    private BigDecimal procurementPrice;                             //单吨采购价 (元/吨)

    @Size(max = 100, message = "上游资源方全称不能超过 {max} 个字符", groups = {SaveFinanceOrder.class, SubmitFinanceOrder.class})
    private String upstreamResource;                                 //上游资源方全称

    @Size(max = 100, message = "中转港口不能超过 {max} 个字符", groups = {SaveFinanceOrder.class, SubmitFinanceOrder.class})
    private String transferPort;                                     //中转港口/地全称

    @Size(max = 5000, message = "备注说明不能超过 {max} 个字符", groups = {SaveFinanceOrder.class, SubmitFinanceOrder.class})
    private String comments;                                         //备注说明

    @Size(max = 100, message = "我方签约单位不能超过 {max} 个字符", groups = {SaveFinanceOrder.class, SubmitFinanceOrder.class})
    private String ourContractCompany;                               //签约单位全称/我方签约公司

    @Size(max = 100, message = "下游签约单位不能超过 {max} 个字符", groups = {SaveFinanceOrder.class, SubmitFinanceOrder.class})
    private String downstreamContractCompany;                        //下游签约单位

    @Size(max = 100, message = "用煤终端不能超过 {max} 个字符", groups = {SaveFinanceOrder.class, SubmitFinanceOrder.class})
    private String terminalServer;                                   //用煤终端

    @Digits(integer = 4, fraction = 2, message = "预计单吨采购价最多支持 {integer}位整数, {fraction}位小数", groups = {SaveFinanceOrder.class, SubmitFinanceOrder.class})
    @DecimalMin(value = "1", inclusive = true, message = "预计单吨采购价不能低于 {value} 元", groups = {SaveFinanceOrder.class, SubmitFinanceOrder.class})
    @DecimalMax(value = "100000", inclusive = true, message = "预计单吨采购价不能高于 {value} 元", groups = {SaveFinanceOrder.class, SubmitFinanceOrder.class})
    private BigDecimal sellingPrice;                                 //预计单吨销售价 (元/吨)

    @Size(max = 100, message = "煤炭仓储地不能超过 {max} 个字符", groups = {SaveFinanceOrder.class, SubmitFinanceOrder.class})
    private String storageLocation;                                  //煤炭仓储地

    @Size(max = 100, message = "煤炭来源不能超过 {max} 个字符", groups = {SaveFinanceOrder.class, SubmitFinanceOrder.class})
    private String coalSource;                                       //煤炭来源

    @Digits(integer = 4, fraction = 2, message = "单吨市场报价最多支持 {integer}位整数, {fraction}位小数", groups = {SaveFinanceOrder.class, SubmitFinanceOrder.class})
    @DecimalMin(value = "1", inclusive = true, message = "单吨市场报价不能低于 {value} 元", groups = {SaveFinanceOrder.class, SubmitFinanceOrder.class})
    @DecimalMax(value = "100000", inclusive = true, message = "单吨市场报价不能高于 {value} 元", groups = {SaveFinanceOrder.class, SubmitFinanceOrder.class})
    private BigDecimal marketPrice;                                  //单吨市场报价（元／吨）

    @Size(max = 500, message = "主要煤炭指标不能超过 {max} 个字符", groups = {SaveFinanceOrder.class, SubmitFinanceOrder.class})
    private String coalQuantityIndex;                                //主要煤质指标
    private String approveState;                                     //审批状态
    private Integer approveStateId;                                  //审批状态Id
    private String sourceId;                                         //流水号，编号
    private String applyUserName;                                    //申请人姓名
    private String applyUserPhone;                                   //申请人手机号
    private Long applyCompanyId;                                     //申请人公司id
    private String applyCompanyName;                                 //申请公司名称
    @JsonFormat(pattern = EnumCommonString.LocalDateTime_Pattern, timezone = EnumCommonString.GMT_8)
    private Date endTime;                                            //结束时间
    @NotBlank(message = "风控线不能为空", groups = {SubmitFinanceOrder.class})
    private Long riskCompanyId;                                      //风控线id
    private String riskCompanyName;                                  //风控线名称
    private List<AttachmentObject> attachmentList1;                  //附件列表

    public String getApplyTypeName() {
        return EnumFinanceOrderType.getName(EnumFinanceOrderType.valueOf(applyType));
    }


}
