package com.yimei.controllers.page;

import com.yimei.boot.ext.mvc.support.Session;
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
    private Session session;

    /**
     * 首页 页面
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
//        if(session.getUser() != null) {
//            return "home";
//        } else {
//            return "redirect:/login";
//        }
        return "home";
    }
}
