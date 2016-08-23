package com.yimei.finance.entity.admin.user;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by liuxinjie on 16/8/19.
 */
@ApiModel(value = "user", description = "用户对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserObject implements Serializable {
    private String id;
    private String username;                    //账号
    private String name;                        //姓名
    private String phone;                       //手机号
    private String email;                       //邮箱
    private String department;                  //部门
    private String password;
    private String[] groupIds;                  //用户组id数组

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
