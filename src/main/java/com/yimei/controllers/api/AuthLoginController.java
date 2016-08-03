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
public class AuthLoginController {
    @Autowired
    private Session session;
    @Autowired
    private AdminService adminService;


    /**
     * 登陆方法
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Object doLogin(@RequestParam(value = "userName", required = true)String userName,
                          @RequestParam(value = "password", required = true)String password) {
        return adminService.login(userName, password);
    }

    /**
     * 退出登陆
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        session.logout();
        return "redirect:/login";
    }
}
