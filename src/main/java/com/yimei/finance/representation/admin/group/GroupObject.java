package com.yimei.finance.representation.admin.group;

import com.yimei.finance.representation.admin.group.validated.CreateGroup;
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
    @Size(min = 2, max = 30, message = "组名应在 {min}-{max} 个字符之间", groups = {CreateGroup.class})
    @NotBlank(message = "组名不能为空", groups = {CreateGroup.class})
    private String name;
    private String type;
    private Long memberNums;


}
