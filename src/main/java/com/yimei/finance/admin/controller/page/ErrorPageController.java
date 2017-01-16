package com.yimei.finance.admin.controller.page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Api(tags={"admin-page"})
@Controller("adminErrorPageController")
public class ErrorPageController {

    @ApiOperation(value = "网站管理后台 404 页面", notes = "网站管理后台 404 页面 Page Not Found")
    @RequestMapping(value = "/finance/admin/404", method = RequestMethod.GET)
    public String Page404() {
        return "admin/http/404";
    }

    @ApiOperation(value = "网站管理后台 500 页面", notes = "网站管理后台 500 页面 Page Not Found")
    @RequestMapping(value = "/finance/admin/500", method = RequestMethod.GET)
    public String Page500() {
        return "admin/http/500";
    }


}
