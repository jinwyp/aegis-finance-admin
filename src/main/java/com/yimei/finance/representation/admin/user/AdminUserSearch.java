package com.yimei.finance.representation.admin.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class AdminUserSearch implements Serializable {
    private String username;                          //登录名
    private String name;                              //姓名
    private String groupName;                         //组名

}
