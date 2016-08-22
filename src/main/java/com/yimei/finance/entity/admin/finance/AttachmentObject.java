package com.yimei.finance.entity.admin.finance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by liuxinjie on 16/8/20.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentObject implements Serializable {
    private String name;
    private String url;
    private String description;
    private String type;
    private String taskId;
    private String processInstanceId;
    private String userId;
    private Date time;
}
