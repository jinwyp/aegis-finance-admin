package com.yimei.site.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by liuxinjie on 16/7/29.
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    @ResponseBody
    public Object test() {
        int i = 0;
        return i;
    }
}
