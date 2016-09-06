package com.yimei.finance.controllers.site.tpl;

import com.yimei.finance.config.session.UserSession;
import com.yimei.finance.ext.annotations.LoginRequired;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wangqi on 16/8/17.
 */

@Api(tags = {"example"})
@RestController
public class LoginDemoController {

    @Autowired
    UserSession userSession;

    @RequestMapping(value = "/api/financing/site/demo/logindemo", method = RequestMethod.GET)
    @LoginRequired
    private Object login() {
        return userSession.getUser();
    }


    @RequestMapping(value = "/api/financing/site/demo/logoutdemo", method = RequestMethod.GET)
    private Object logout() {
        return userSession.getUser();
    }

    @RequestMapping("/exceptiondemo")
    public Object exceptiondemo() throws Exception {
        throw new Exception("我的错");
    }
}
