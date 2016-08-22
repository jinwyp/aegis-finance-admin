package com.yimei.finance.controllers.admin.restfulapi.user;

import com.yimei.finance.config.session.AdminSession;
import com.yimei.finance.entity.admin.user.UserObject;
import com.yimei.finance.entity.common.result.Result;
import com.yimei.finance.service.admin.user.AdminUserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"admin-api-user"}, description = "用户登陆验证接口")
@RequestMapping("/api/financing/admin")
@RestController("adminUserAuthController")
public class UserAuthController {
    @Autowired
    private AdminSession adminSession;
    @Autowired
    private AdminUserServiceImpl adminService;

    /**
     * 管理员登陆
     */
    @ApiOperation(value = "登陆接口", notes = "需要输入用户名和密码登陆", response = UserObject.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "登陆用户名", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String", paramType = "form")
    })
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Result authLoginWithPassword(@RequestParam(value = "userName", required = true) String userName,
                                        @RequestParam(value = "password", required = true) String password) {
        return adminService.login(userName, password);
    }

    /**
     * 管理员退出登录
     */
    @ApiOperation(value = "退出登录接口", notes = "退出登陆")
    @RequestMapping(value = "/logout", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Result logout() {
        adminSession.logout();
        return Result.success();
    }

}
