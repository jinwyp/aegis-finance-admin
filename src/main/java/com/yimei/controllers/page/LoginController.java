package com.yimei.controllers.index;

import com.yimei.api.admin.AdminService;
import com.yimei.boot.ext.mvc.support.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by liuxinjie on 07/30/16.
 * 登陆相关接口
 */
@Controller
public class LoginController {
    @Autowired
    private Session session;
    @Autowired
    private AdminService adminService;

    /**
     * 登录页面
     */

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

}
