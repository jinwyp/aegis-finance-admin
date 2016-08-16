package com.yimei.finance.controllers.admin.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by liuxinjie on 07/30/16.
 * 登陆页面
 */

@Controller
@RequestMapping("/api/financing/admin")
public class LoginController {

    /**
     * 跳转到管理登录页面
     */

    @RequestMapping(method = RequestMethod.GET)
    public String indexPage() {
        return "redirect:/finance/admin/login";
    }

    /**
     * 管理登录页面
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "admin/login";
    }

}
