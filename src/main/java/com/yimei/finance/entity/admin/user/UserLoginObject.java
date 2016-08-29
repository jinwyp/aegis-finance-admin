package com.yimei.finance.entity.admin.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginObject implements Serializable {

    @Size(min=3, max=20, message = "账号应在3-20个字符之间")
    @NotBlank(message = "账号不能为空")
    private String username;                    //账号

    @Size(min=6, max=20, message = "密码应在6-16个字符之间")
    @NotBlank(message = "密码不能为空")
    private String password;

}
