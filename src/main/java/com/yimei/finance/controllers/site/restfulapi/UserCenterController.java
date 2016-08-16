package com.yimei.finance.controllers.site.restfulapi;

import com.yimei.finance.repository.common.result.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * Created by JinWYP on 8/15/16.
 */

@RestController
public class UserCenterController {


    /**
    * 供应链金融-个人中心
    */
    @RequestMapping(value = "/api/financing/orders", method = RequestMethod.POST)
    public Result requestFinancingOrder() {
        //    return new ResponseEntity<String>("{\n" +
//            "    \"success\" : true,\n" +
//            "    \"error\" : null,\n" +
//            "    \"meta\" : null,\n" +
//            "    \"data\" : {\n" +
//            "        \"id\" : 11,\n" +
//            "        \"orderNo\" : 1001\n" +
//            "    }\n" +
//            "}", HttpStatus.OK);
//    }
//        return Result.error("cccccccc");
//        return Result.error(1003, "cccc");
//        return Result.error(1000, "cccc", "field");

        return Result.success().setData(new HashMap() {{put("userId", 1); put("orderId", 2);}} );
    }



}
