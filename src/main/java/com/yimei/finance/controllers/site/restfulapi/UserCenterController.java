package com.yimei.finance.controllers.site.restfulapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by JinWYP on 8/15/16.
 */
public class UserCenterController {


    /**
    * 供应链金融-个人中心
    */
    @RequestMapping(value = "/api/financing/orders", method = RequestMethod.GET)
    @ResponseBody
    public Object addFinanceOrder(@RequestParam(value = "type", required = true)int type) {

    return new ResponseEntity<String>("{\n" +
            "    \"success\" : true,\n" +
            "    \"error\" : null,\n" +
            "    \"meta\" : null,\n" +
            "    \"data\" : {\n" +
            "        \"id\" : 11,\n" +
            "        \"orderNo\" : 1001\n" +
            "    }\n" +
            "}", HttpStatus.OK);
    }

}
