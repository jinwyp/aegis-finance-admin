package com.yimei.finance.entity.admin.finance;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentObject implements Serializable {
    private String id;
    private String name;
    private String type;
    private String url;

    public AttachmentObject(String name, String type, String url) {
        this.name = name;
        this.url = url;
        this.type = type;
    }

    private String description;
    private String taskId;
    private String processInstanceId;
    private String userId;
    private Date time;
}
