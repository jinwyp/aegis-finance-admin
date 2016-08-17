package com.yimei.finance.controllers.site.page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * Created by liuxinjie on 16/7/29.
 */
@Api(tags = {"sitepage"})
@Controller
public class IndexController {

    private Environment env;

    @Autowired
    public IndexController(Environment env) {
        this.env = env;
    }


    /**
     * 网站供应链金融 页面
     */
    @ApiOperation(value = "网站供应链金融 页面", notes = "网站页面需要登录")
    @RequestMapping(value = "/finance", method = RequestMethod.GET)
    public String financeIndex(Model model) {
        model.addAttribute("env", env.getProperty("spring.profiles"));
        model.addAttribute("items", Arrays.asList("iPhone 6", "iPhone 6 Plus", "iPhone 6S", "iPhone 6S Plus"));
        //System.out.println(" --------- " + tplService.test().toString());
        //System.out.println(" --------- " + tplService.test().toString());
        return "site/index";
    }


    /**
    * 网站供应链金融 - 个人中心 - 我的申请
    */
    @ApiOperation(value = "网站供应链金融 - 个人中心 - 我的申请", notes = "供应链金融 我的融资 申请列表")
    @RequestMapping(value = "/finance/user/financing", method = RequestMethod.GET)
    public String personCenterFinancingList(Model model) {
        model.addAttribute("env", env.getProperty("spring.profiles"));

        return "site/user/financingList";
    }


    /**
     * 网站供应链金融 - 个人中心 - 我的申请 - 业务详情
     */
    @ApiOperation(value = "网站供应链金融 - 个人中心 - 我的申请 - 业务详情", notes = "供应链金融 我的融资 申请详情页面")
    @RequestMapping(value = "/finance/user/financing/{id}", method = RequestMethod.GET)
    public String personCenterFinancingRequest(@PathVariable("id") int id, Model model) {
        model.addAttribute("env", env.getProperty("spring.profiles"));

        return "site/user/financingInfo";
    }

}
