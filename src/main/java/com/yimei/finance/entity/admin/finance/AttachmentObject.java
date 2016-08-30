package com.yimei.finance.entity.admin.finance;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by liuxinjie on 16/8/20.
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class AttachmentObject implements Serializable {
    @NonNull
    private String name;
    @NonNull
    private String type;
    @NonNull
    private String url;
    private String description;
    private String taskId;
    private String processInstanceId;
    private String userId;
    private Date time;
}
