package com.yimei.finance.controllers.site.tpl;

import com.yimei.finance.config.UserSession;
import com.yimei.finance.ext.annotations.LoginRequired;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wangqi on 16/8/17.
 */

@Api(tags = {"example"})
@RestController
public class LoginDemoController {

    @Autowired
    UserSession userSession;

    @RequestMapping("/api/financing/site/demo/logindemo")
    @LoginRequired
    private Object login() {
        return userSession.getUser();
    }

    @RequestMapping("/api/financing/site/demo/logoutdemo")
    private Object logout() {
        return userSession.getUser();
    }
}
