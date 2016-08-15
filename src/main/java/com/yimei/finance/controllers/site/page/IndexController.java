package com.yimei.finance.controllers.site.page;

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
    @RequestMapping(value = "/finance/user/financing", method = RequestMethod.GET)
    public String personCenterFinancingList(Model model) {
        model.addAttribute("env", env.getProperty("spring.profiles"));
        model.addAttribute("title", env.getProperty("title"));

        return "site/user/financingList";
    }


    /**
     * 网站供应链金融 - 个人中心 - 我的申请 - 业务
     */
    @RequestMapping(value = "/finance/user/financing/{id}", method = RequestMethod.GET)
    public String personCenterFinancingRequest(@PathVariable("id") int id, Model model) {
        model.addAttribute("env", env.getProperty("spring.profiles"));
        model.addAttribute("title", env.getProperty("title"));

        return "site/user/financingInfo";
    }

}
