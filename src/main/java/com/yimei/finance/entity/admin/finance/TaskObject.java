package com.yimei.finance.entity.admin.finance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskObject implements Serializable {
    private String id;
    private String processInstanceId;
    private String assignee;
    private String assigneeName;
    private String assigneeDepartment;
    private String name;
    private String description;
    private Date createTime;
    private Date dueDate;
    private String applyCompanyName;


}
