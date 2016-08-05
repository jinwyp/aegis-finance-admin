package com.yimei.controllers.restfulapi;

import com.yimei.api.admin.AdminService;
import com.yimei.boot.ext.mvc.support.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by liuxinjie on 07/30/16.
 * 登陆相关接口
 */

@RestController
public class AuthLoginController {

    @Autowired
    private Session session;

    @Autowired
    private AdminService adminService;


    /**
     * 登陆方法
     */
    @RequestMapping(value = "api/admin/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object authLoginWithPassword(
        @RequestParam(value = "userName", required = true)String userName,
        @RequestParam(value = "password", required = true)String password
    ) {
//        return adminService.login(userName, password);
        return new ResponseEntity<String>("{}", HttpStatus.OK);
    }


    /**
     * 退出登陆
     */
    @RequestMapping(value = "api/admin/logout", method = RequestMethod.GET)
    public String logout() {
        session.logout();
        return "redirect:/login";
    }

}
