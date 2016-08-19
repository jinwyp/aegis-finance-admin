package com.yimei.finance.entity.admin.finance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by liuxinjie on 16/8/19.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskObject implements Serializable {
    private String id;
    private String processInstanceId;
    private String assignee;
    private String name;
    private String description;
    private Date createTime;
    private Date dueDate;

}