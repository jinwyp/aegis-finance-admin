package com.yimei.finance.controllers.admin.restfulapi.user;

import com.yimei.finance.config.session.AdminSession;
import com.yimei.finance.representation.admin.user.UserLoginObject;
import com.yimei.finance.representation.admin.user.UserObject;
import com.yimei.finance.representation.common.result.Result;
import com.yimei.finance.service.admin.user.AdminUserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = {"admin-api-user"}, description = "用户登陆验证接口")
@RequestMapping("/api/financing/admin")
@RestController("adminUserAuthController")
public class UserAuthController {
    @Autowired
    private AdminSession adminSession;
    @Autowired
    private AdminUserServiceImpl adminService;

    @ApiOperation(value = "登陆接口", notes = "需要输入用户名和密码登陆", response = UserLoginObject.class)
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result authLoginWithPassword(@Valid @RequestBody UserLoginObject userLoginObject) {
        Result result = adminService.loginMethod(userLoginObject);
        if (!result.isSuccess()) return result;
        adminSession.login((UserObject) result.getData());
        return result;
    }

    @ApiOperation(value = "退出登录接口", notes = "退出登陆")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public Result logout() {
        adminSession.logout();
        return Result.success();
    }

    @ApiOperation(value = "获取session中当前用户对象", notes = "获取session中用户对象", response = UserObject.class)
    @RequestMapping(value = "/session", method = RequestMethod.GET)
    public Result getSessionUserMethod() {
        return Result.success().setData(adminSession.getUser());
    }

}
