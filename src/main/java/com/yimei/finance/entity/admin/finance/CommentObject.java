package com.yimei.finance.entity.admin.finance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentObject implements Serializable {
    private int id;
    private String taskId;
    private String processInstanceId;
    private Date time;
    private String message;
    private String fullMessage;
    private String type;

}
