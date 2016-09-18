package com.yimei.finance.representation.admin.user;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.io.Serializable;

@ApiModel(value = "group")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupObject implements Serializable {
    private String id;
    @Size(min = 2, max = 30, message = "组名应在2-30个字符之间")
    @NotBlank(message = "组名不能为空")
    private String name;
    private String type;
    private Integer memberNums;

}
