package com.yimei.finance.representation.admin.user;

import com.yimei.finance.representation.admin.user.validated.CreateUser;
import com.yimei.finance.representation.admin.user.validated.EditUser;
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
    @Size(min = 2, max = 20, message = "登录名应在2-20个字符之间", groups = {CreateUser.class})
    @NotBlank(message = "用户登录名不能为空", groups = {CreateUser.class})
    private String username;                    //账号
    @Size(min = 2, max = 20, message = "姓名应在2-20个字符之间", groups = {CreateUser.class, EditUser.class})
    @NotBlank(message = "姓名不能为空", groups = {CreateUser.class, EditUser.class})
    private String name;                        //姓名
    @Size(min = 11, max = 11, message = "请输入正确的手机号", groups = {CreateUser.class, EditUser.class})
    @Pattern(regexp = "^[1][3,4,5,7,8][0-9]{9}$", message = "请输入正确的手机号", groups = {CreateUser.class, EditUser.class})
    private String phone;                       //手机号
    @Size(max = 50, message = "邮箱不能超过 {max} 个字符", groups = {CreateUser.class, EditUser.class})
    @NotBlank(message = "邮箱不能为空", groups = {CreateUser.class, EditUser.class})
    @Email(message = "请输入正确的邮箱", groups = {CreateUser.class, EditUser.class})
    private String email;                       //邮箱
    @Size(max = 30, message = "部门名称应在 {min}-{max} 个字符之间", groups = {CreateUser.class, EditUser.class})
    private String department;                  //部门
    private Date lastLoginTime;                 //最后一次登录时间
    private boolean operateAuthority;           //是否具有操作/更改此用户的权限, true: 有权限, false: 无
    private List<String> groupIds;              //用户组id数组
    private List<GroupObject> groupList;        //用户所在组列表

}