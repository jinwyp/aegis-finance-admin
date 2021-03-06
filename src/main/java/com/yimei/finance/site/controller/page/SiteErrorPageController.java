package com.yimei.finance.site.controller.page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Api(tags = {"site-page"}, description = "错误页面")
@Controller("siteErrorPageController")
public class SiteErrorPageController {

    @ApiOperation(value = "网站 404 页面", notes = "网站 404 页面 Page Not Found")
    @RequestMapping(value = "/finance/404", method = RequestMethod.GET)
    public String Page404() {
        return "site/http/404";
    }


    @ApiOperation(value = "网站 500 页面", notes = "网站 500 页面 System Error !")
    @RequestMapping(value = "/finance/500", method = RequestMethod.GET)
    public String Page500() {
        return "site/http/500";
    }


}
