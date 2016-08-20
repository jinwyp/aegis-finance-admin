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
    protected String name;
    protected String url;
    protected String description;
    protected String type;
    protected String taskId;
    protected String processInstanceId;
    protected String userId;
    protected Date time;
}
