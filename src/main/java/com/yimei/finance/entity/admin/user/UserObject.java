package com.yimei.finance.entity.admin.user;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel(value = "user", description = "用户对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserObject implements Serializable {
    private String id;
    @Size(min = 2, max = 20, message = "登录名应在2-20个字符之间")
    @NotBlank(message = "用户登录名不能为空")
    private String username;                    //账号
    @Size(min = 2, max = 20, message = "姓名应在2-20个字符之间")
    @NotBlank(message = "姓名不能为空")
    private String name;                        //姓名
    @Size(min = 11, max = 11, message = "请输入正确的手机号")
    @Pattern(regexp = "^[1][3,4,5,7,8][0-9]{9}$", message = "请输入正确的手机号")
    private String phone;                       //手机号
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "请输入正确的邮箱")
    private String email;                       //邮箱
    @Size(min = 1, max = 30, message = "部门名称营造1-30个字符之间")
    private String department;                  //部门
    private Date lastLoginTime;                 //最后一次登录时间
    private boolean operateAuthority;           //是否具有操作/更改此用户的权限, true: 有权限, false: 无
    private List<String> groupIds;              //用户组id数组
    private List<GroupObject> groupList;        //用户所在组列表

}
