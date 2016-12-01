package com.yimei.finance.admin.representation.user.object;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.io.Serializable;

@ApiModel(description = "用户登陆对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginObject implements Serializable {
    @Size(min=2, max=20, message = "账号应在2-20个字符之间")
    @NotBlank(message = "账号不能为空")
    private String username;                    //账号
    @Size(min=6, max=16, message = "密码应在6-16个字符之间")
    @NotBlank(message = "密码不能为空")
    private String password;                    //密码

}
