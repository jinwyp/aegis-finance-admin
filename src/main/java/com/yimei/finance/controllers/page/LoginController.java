package com.yimei.finance.controllers.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by liuxinjie on 07/30/16.
 * 登陆页面
 */

@Controller
public class LoginController {

    /**
     * 登录页面
     */

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String indexPage() {
        return "redirect:/admin/login";
    }

    /**
     * 登录页面
     */
    @RequestMapping(value = "/admin/login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }



}
