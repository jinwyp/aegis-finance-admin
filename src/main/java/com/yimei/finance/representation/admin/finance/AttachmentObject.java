package com.yimei.finance.representation.admin.finance;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentObject implements Serializable {
    private String id;
    @Size(min = 1, max = 100, message = "文件名称应在1-100个字符之间")
    @NotBlank(message = "文件名称不能为空")
    private String name;
    private String type;
    @Size(min = 1, max = 500, message = "文件路径应在1-500个字符之间")
    @NotBlank(message = "文件路径不能为空")
    private String url;
    private String description;
    private String taskId;
    private String processInstanceId;
    private String userId;
    private Date time;

    public AttachmentObject(String name, String type, String url) {
        this.name = name;
        this.url = url;
        this.type = type;
    }
}
