package com.yimei.finance.controllers.page;

import com.yimei.finance.config.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by liuxinjie on 07/30/16.
 * 错误相关页面 例如 404 页面
 */


@Controller
public class ErrorController {
    @Autowired
    private Session session;


    @RequestMapping(value = "/404", method = RequestMethod.GET)
    public String Page404() {
        return "http/404";
    }

}
