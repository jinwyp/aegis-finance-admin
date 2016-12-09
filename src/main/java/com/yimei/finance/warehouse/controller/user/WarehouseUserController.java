package com.yimei.finance.warehouse.controller.user;

import com.yimei.finance.common.representation.result.Page;
import com.yimei.finance.common.representation.result.Result;
import com.yimei.finance.config.session.WarehouseAdminSession;
import com.yimei.finance.warehouse.representation.user.form.WarehouseAdminUserSearch;
import com.yimei.finance.warehouse.representation.user.object.WarehouseAdminUserObject;
import com.yimei.finance.warehouse.service.user.WarehouseAdminUserServiceImpl;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"warehouse-admin-api-user"})
@RequestMapping("/api/warehouse/admin/user")
@RestController("warehouseUserController")
public class WarehouseUserController {
    @Autowired
    private WarehouseAdminSession session;
    @Autowired
    private WarehouseAdminUserServiceImpl userService;

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "查询所有用户", notes = "查询所有用户列表", response = WarehouseAdminUserObject.class, responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户账号", required = false, defaultValue = "", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "用户姓名", required = false, defaultValue = "", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "companyName", value = "公司名", required = false, defaultValue = "", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "roleName", value = "角色名", required = false, defaultValue = "", dataType = "string", paramType = "query")
    })
    public Result getAllUsersMethod(WarehouseAdminUserSearch userSearch, @ApiParam(name = "page", value = "分页参数", required = false) Page page) {
//        return userService.getUserListBySelect(session.getUser(), userSearch, page);
        return null;
    }
}
