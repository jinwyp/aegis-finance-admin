package com.yimei.finance.warehouse.representation.user.form;

import com.yimei.finance.utils.Where;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;

@ApiModel(description = "仓押后台 - 用户列表搜索参数")
@Data
public class WarehouseAdminUserSearch implements Serializable {
    private String username;                          //登录名
    private String name;                              //姓名
    private String companyName;                       //公司名
    private String roleName;                          //角色名

    public String getUsername() {
        if (StringUtils.isEmpty(username)) username = "";
        return Where.$like$(username);
    }

    public String getName() {
        if (StringUtils.isEmpty(name)) name = "";
        return Where.$like$(name);
    }

    public String getCompanyName() {
        if (StringUtils.isEmpty(companyName)) companyName = "";
        return Where.$like$(companyName);
    }

    public String getRoleName() {
        if (StringUtils.isEmpty(roleName)) roleName = "";
        return Where.$like$(roleName);
    }
}
