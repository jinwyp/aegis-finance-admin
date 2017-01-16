package com.yimei.finance.admin.representation.user.object;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@ApiModel(description = "用户列表搜索参数")
@Data
public class AdminUserSearch implements Serializable {
    private String username;                          //登录名
    private String name;                              //姓名
    private String groupId;                           //组id

}
