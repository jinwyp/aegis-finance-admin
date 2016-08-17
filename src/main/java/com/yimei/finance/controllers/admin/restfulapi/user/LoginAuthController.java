package com.yimei.finance.controllers.admin.restfulapi.user;

import com.yimei.finance.repository.common.result.Result;
import com.yimei.finance.service.admin.AdminUserServiceImpl;
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

@RequestMapping("/api/financing/admin")
@Api(value = "Admin-Login-Auth-API", description = "用户登陆验证接口")
@RestController
public class LoginAuthController {

    @Autowired
    private AdminUserServiceImpl adminService;

    /**
     * 登陆方法
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "登陆验证方法", notes = "登陆验证方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "登陆用户名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String", paramType = "query")
    })
    public Result authLoginWithPassword(@RequestParam(value = "userName", required = true) String userName,
                                        @RequestParam(value = "password", required = true) String password) {
        return adminService.login(userName, password);
    }


}
