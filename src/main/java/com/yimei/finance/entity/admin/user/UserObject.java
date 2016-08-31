package com.yimei.finance.entity.admin.user;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@ApiModel(value = "user", description = "用户对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserObject implements Serializable {
    private String id;
    @Size(min=3, max=20, message = "账号应在3-20个字符之间")
    @NotBlank(message = "用户登录名不能为空")
    private String username;                    //账号
    @Size(min=3, max=20, message = "姓名应在3-20个字符之间")
    @NotBlank(message = "姓名不能为空")
    private String name;                        //姓名
    private String phone;                       //手机号
    @NotBlank(message = "邮箱不能为空")
    @Email
    private String email;                       //邮箱
    private String department;                  //部门
    private String password;
    private List<String> groupIds;              //用户组id数组
    private List<GroupObject> groupList;        //用户所在组列表

}
