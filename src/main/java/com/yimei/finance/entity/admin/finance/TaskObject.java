package com.yimei.finance.entity.admin.finance;

import com.yimei.finance.entity.admin.user.UserObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskObject implements Serializable {
    private String id;
    private String processInstanceId;                               //流程id
    private String taskDefinitionKey;                               //任务id(流程图中定义的id)
    private String assignee;                                        //处理人
    private String assigneeName;                                    //处理人姓名
    private String assigneeDepartment;                              //处理人部门
    private String name;                                            //任务名称
    private String description;                                     //任务描述
    private Date createTime;
    private Date dueDate;
    private String applyCompanyName;                                //申请客户公司名称
    private String applyType;                                       //融资类型
    private String applyTypeName;                                   //融资类型Name
    private BigDecimal financingAmount;                             //融资金额
    private String sourceId;                                        //金融单业务编号
    private UserObject currentAssignee;                             //当前处理人
    private String currentName;                                     //当前流程节点

}
