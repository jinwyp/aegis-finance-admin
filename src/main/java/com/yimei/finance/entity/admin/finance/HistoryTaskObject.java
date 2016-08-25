package com.yimei.finance.entity.admin.finance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryTaskObject {
    protected String id;
    private String processInstanceId;
    protected String assignee;
    private String assigneeName;
    private String assigneeDepartment;
    protected String name;
    protected String description;
    private Date startTime;
    private Date endTime;
    protected Date dueDate;
    private String applyCompanyName;
}
