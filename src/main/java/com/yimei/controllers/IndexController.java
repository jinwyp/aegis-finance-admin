package com.yimei.controllers;

import com.yimei.boot.ext.mvc.support.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by liuxinjie on 07/30/16.
 * 初始化
 */
@Controller
public class IndexController {
    @Autowired
    private Session session;

    /**
     * 初始化页面
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        if(session.getUser() != null) {
            return "index";
        } else {
            return "redirect:/login";
        }
    }


}
