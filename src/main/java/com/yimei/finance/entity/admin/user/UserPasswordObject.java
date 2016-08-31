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
public class UserPasswordObject implements Serializable {
    @Size(min=6, max=16, message = "密码应在6-16个字符之间")
    @NotBlank(message = "原密码不能为空")
    private String oldPassword;
    @Size(min=6, max=16, message = "密码应在6-16个字符之间")
    @NotBlank(message = "新密码不能为空")
    private String newPassword;
}
