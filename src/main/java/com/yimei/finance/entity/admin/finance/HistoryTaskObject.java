package com.yimei.finance.entity.admin.finance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryTaskObject {
    protected String id;
    private String processInstanceId;
    private String taskDefinitionKey;
    protected String assignee;
    private String assigneeName;
    private String assigneeDepartment;
    protected String name;
    protected String description;
    private Date startTime;
    private Date endTime;
    protected Date dueDate;
    private String applyCompanyName;
    private String applyType;
    private BigDecimal financingAmount;
    private List<CommentObject> commentList;
}
