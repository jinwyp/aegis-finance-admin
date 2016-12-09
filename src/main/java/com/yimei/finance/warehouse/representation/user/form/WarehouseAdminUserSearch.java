package com.yimei.finance.warehouse.representation.user.form;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@ApiModel(description = "仓押后台 - 用户列表搜索参数")
@Data
public class WarehouseAdminUserSearch implements Serializable {
    private String username;                          //登录名
    private String name;                              //姓名
    private String companyName;                       //公司名
    private String roleName;                          //角色名
}
