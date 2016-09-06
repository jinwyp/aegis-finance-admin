package com.yimei.finance.representation.admin.user;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@ApiModel(description = "用户列表搜索参数")
@Data
public class AdminUserSearch implements Serializable {
    private String username;                          //登录名
    private String name;                              //姓名
    private String groupName;                         //组名

}
