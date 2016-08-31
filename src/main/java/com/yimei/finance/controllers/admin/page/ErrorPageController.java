package com.yimei.finance.controllers.admin.page;

import com.yimei.finance.config.session.AdminSession;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Api(tags={"admin-page-error"})
@Controller("adminErrorPageController")
public class ErrorPageController {
    @Autowired
    private AdminSession adminSession;


    @RequestMapping(value = "/admin/404", method = RequestMethod.GET)
    public String Page404() {
        return "admin/http/404";
    }

}
