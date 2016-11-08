package com.yimei.finance.representation.admin.activiti;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yimei.finance.representation.common.enums.EnumCommonString;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(description = "任务对象")
@Data
@NoArgsConstructor
public class TaskObject implements Serializable {
    private String id;
    private String processInstanceId;                               //流程id
    private String taskDefinitionKey;                               //任务id(流程图中定义的id)
    private String owner;
    private String assignee;                                        //处理人id
    private String assigneeName;                                    //处理人姓名
    private String assigneeDepartment;                              //处理人部门
    private String name;                                            //任务名称
    private String description;                                     //任务描述
    @JsonFormat(pattern = EnumCommonString.LocalDateTime_Pattern, timezone = EnumCommonString.GMT_8)
    private Date createTime;
    @JsonFormat(pattern = EnumCommonString.LocalDateTime_Pattern, timezone = EnumCommonString.GMT_8)
    protected Date claimTime;
    private Long financeId;                                         //金融单id
    private String applyCompanyName;                                //申请客户公司名称
    private String applyType;                                       //融资类型
    private String applyTypeName;                                   //融资类型Name
    private BigDecimal financingAmount;                             //融资金额
    private String sourceId;                                        //金融单业务编号
    private Long riskCompanyId;                                     //业务公司id
}
