package com.yimei.finance.controllers.admin.page;

import com.yimei.finance.config.AdminSession;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Api(tags = {"admin-page"}, description = "管理后台首页")
@Controller
public class HomePageController {
    @Autowired
    private AdminSession adminSession;

    /**
     * 管理首页 页面
     */
    @ApiOperation(value = "管理后台管理员首页", notes = "管理后台管理员首页 需要管理员登录")
    @RequestMapping(value = "/finance/admin/home", method = RequestMethod.GET)
    public String index() {
//        if(session.getUser() != null) {
//            return "admin/home";
//        } else {
//            return "redirect:/finance/admin/login";
//        }
        return "admin/home";
    }

    /**
     * 管理页面 为了前端路由需要重定向
     */
    @ApiOperation(value = "管理后台前端路由重定向", notes = "管理页面为了前端路由需要重定向")
    @RequestMapping(value = "/finance/admin/home/**", method = RequestMethod.GET)
    public String adminHome() {
//        if(session.getUser() != null) {
//            return "admin/home";
//        } else {
//            return "redirect:/finance/admin/login";
//        }
        return "forward:/finance/admin/home";
    }
}
