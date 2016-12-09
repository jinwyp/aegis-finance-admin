package com.yimei.finance.warehouse.controller.user;

import com.yimei.finance.common.representation.result.Result;
import com.yimei.finance.config.session.WarehouseAdminSession;
import com.yimei.finance.warehouse.representation.user.object.WarehouseAdminUserLoginObject;
import com.yimei.finance.warehouse.representation.user.object.WarehouseAdminUserObject;
import com.yimei.finance.warehouse.service.user.WarehouseAdminUserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = {"warehouse-admin-api-user"}, description = "用户登陆验证接口")
@RequestMapping("/api/warehouse/admin")
@RestController("warehouseAdminUserAuthController")
public class WarehouseUserAuthController {
    @Autowired
    private WarehouseAdminSession session;
    @Autowired
    private WarehouseAdminUserServiceImpl adminService;

    @ApiOperation(value = "登陆接口", notes = "需要输入用户名和密码登陆", response = WarehouseAdminUserObject.class)
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result authLoginWithPassword(@Valid @RequestBody WarehouseAdminUserLoginObject userLoginObject) {
        Result result = adminService.loginMethod(userLoginObject);
        if (!result.isSuccess()) return result;
        session.login((WarehouseAdminUserObject) result.getData());
        return result;
    }

    @ApiOperation(value = "退出登录接口", notes = "退出登陆")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public Result logout() {
        session.logout();
        return Result.success();
    }

    @ApiOperation(value = "获取session中当前用户对象", notes = "获取session中用户对象", response = WarehouseAdminUserObject.class)
    @RequestMapping(value = "/session", method = RequestMethod.GET)
    public Result getSessionUserMethod() {
        return Result.success().setData(session.getUser());
    }

}
