package com.yimei.finance.controllers.admin.page;

import com.yimei.finance.config.AdminSession;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/api/financing/admin")
@Api(tags = {"adminpage"}, description = "admin 模块主页")
@Controller
public class HomePageController {
    @Autowired
    private AdminSession adminSession;

    /**
     * 管理首页 页面
     */
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
