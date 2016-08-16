package com.yimei.finance.controllers.site.restfulapi;

import com.yimei.finance.repository.common.result.Result;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by JinWYP on 8/15/16.
 */

@RestController
public class UserCenterController {


    /**
    * 供应链金融 - 发起融资申请
    */
    @RequestMapping(value = "/api/financing/orders", method = RequestMethod.POST)
    public Result requestFinancingOrder(@RequestBody TempFinanceOrder order) {

        System.out.println("Order Type:" + order.applyType);
        System.out.println("UserId:" + order.userId);
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


    /**
     * 供应链金融 - 用户中心 - 获取融资申请列表
     */
    @RequestMapping(value = "/api/financing/applyInfo", method = RequestMethod.GET)
    public Result getFinancingApplyInfoList(@RequestParam(value = "applyType", required = false ) String applyType) {

        return Result.success().setData(new ArrayList()
        {{
            add(new TempFinanceOrder());
            add(new TempFinanceOrder());
            add(new TempFinanceOrder());
            add(new TempFinanceOrder());
        }}
        );
    }



    /**
     * 供应链金融 - 用户中心 - 获取融资申请详细信息
     */
    @RequestMapping(value = "/api/financing/applyInfo/{sourceID}", method = RequestMethod.GET)
    public Result getFinancingApplyInfo(@PathVariable String sourceID) {

        return Result.success().setData(new HashMap()
            {{
                put("sourceID", 12222);
                put("applyType", 2);
                put("approveState", 2);
                put("financingAmount", 2100000);
                put("expectedDate", 2100000);
            }}
        );
    }
}




// 临时使用
@Data
class TempFinanceOrder {
    String applyType = "12222";
    String userId = "2";
    String sourceID = "2";
    String approveState = "2";
    int financingAmount = 20000;
    int expectedDate = 22;

    // + getters, setters
}

