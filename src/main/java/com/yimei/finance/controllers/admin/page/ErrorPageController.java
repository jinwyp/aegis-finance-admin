package com.yimei.finance.controllers.admin.page;

import com.yimei.finance.config.AdminSession;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by liuxinjie on 07/30/16.
 * 错误相关页面 例如 404 页面
 */

@RequestMapping("/api/financing/admin")
@Api(value = "Admin-Error-Page-API", description = "错误页面处理接口")
@Controller
public class ErrorPageController {
    @Autowired
    private AdminSession adminSession;


    @RequestMapping(value = "/404", method = RequestMethod.GET)
    public String Page404() {
        return "admin/http/404";
    }

}
