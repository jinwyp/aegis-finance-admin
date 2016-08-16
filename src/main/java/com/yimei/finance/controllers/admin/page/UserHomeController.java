package com.yimei.finance.controllers.admin.page;

import com.yimei.finance.config.AdminSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by liuxinjie on 07/30/16.
 * 管理员登录后页面
 */


@Controller
public class UserHomeController {
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
