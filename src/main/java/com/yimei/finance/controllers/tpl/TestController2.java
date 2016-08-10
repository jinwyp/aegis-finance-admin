package com.yimei.finance.controllers.tpl;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wangqi on 16/8/10.
 */
@RestController
public class TestController2 {

    @ApiOperation(value="获取用户列表", notes="")
    @RequestMapping(value="wangqi", method= RequestMethod.GET)
    public String getUserList() {
        return "wangqi";
    }
}
