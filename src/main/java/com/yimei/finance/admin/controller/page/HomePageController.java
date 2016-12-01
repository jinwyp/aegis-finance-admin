package com.yimei.finance.admin.controller.page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Api(tags = {"admin-page"}, description = "管理后台首页")
@Controller("adminHomePageController")
public class HomePageController {

    /**
     * 管理首页 页面
     */
    @ApiOperation(value = "管理后台管理员首页", notes = "管理后台管理员首页 需要管理员登录")
    @RequestMapping(value = "/finance/admin/home", method = RequestMethod.GET)
    public String index() {
        return "admin/home";
    }

    /**
     * 管理页面 为了前端路由需要重定向
     */
    @ApiOperation(value = "管理后台前端路由重定向", notes = "管理页面为了前端路由需要重定向")
    @RequestMapping(value = "/finance/admin/home/**", method = RequestMethod.GET)
    public String adminHome() {
        return "forward:/finance/admin/home";
    }



    /**
     * 仓押管理首页 页面
     */
    @ApiOperation(value = "仓押管理后台管理员首页", notes = "仓押管理后台管理员首页 需要管理员登录")
    @RequestMapping(value = "/cangya/admin/home", method = RequestMethod.GET)
    public String indexCangya() {
//        if (adminSession.getUser() == null) {
//            return "redirect:/finance/admin/login";
//        }
        return "admin/homeCangya";
    }

    /**
     * 仓押管理页面 为了前端路由需要重定向
     */
    @ApiOperation(value = "仓押管理后台前端路由重定向", notes = "仓押管理页面为了前端路由需要重定向")
    @RequestMapping(value = "/cangya/admin/home/**", method = RequestMethod.GET)
    public String adminHomeCangya() {
//        if (adminSession.getUser() == null) {
//            return "redirect:/finance/admin/login";
//        }
        return "forward:/cangya/admin/home";
    }
}
