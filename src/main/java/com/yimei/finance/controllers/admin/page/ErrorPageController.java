package com.yimei.finance.controllers.admin.page;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Api(tags={"admin-page-error"})
@Controller("adminErrorPageController")
public class ErrorPageController {

    @RequestMapping(value = "/finance/admin/404", method = RequestMethod.GET)
    public String Page404() {
        return "admin/http/404";
    }

}
