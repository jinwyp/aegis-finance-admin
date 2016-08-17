package com.yimei.finance.controllers.admin.page;

import com.yimei.finance.config.AdminSession;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by liuxinjie on 07/30/16.
 * 登陆页面
 */

@RequestMapping("/api/financing/admin")
@Api(tags = {"adminpage"}, description = "登陆, 退出等页面")
@Controller
public class LoginPageController {
    @Autowired
    private AdminSession adminSession;

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

    /**
     * 退出
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        adminSession.logout();
        return "admin/login";
    }

}
