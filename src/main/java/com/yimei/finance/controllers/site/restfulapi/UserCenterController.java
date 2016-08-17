package com.yimei.finance.controllers.site.restfulapi;

import com.yimei.finance.config.UserSession;
import com.yimei.finance.entity.admin.user.ApplyInfo;
import com.yimei.finance.ext.annotations.LoginRequired;
import com.yimei.finance.repository.common.result.Result;
import com.yimei.finance.service.admin.finance.ApplyInfoServiceImpl;
import com.yimei.finance.utils.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by JinWYP on 8/15/16.
 */
@Api(tags = {"siteapi"})
@RestController
public class UserCenterController {

    @Autowired
    ApplyInfoServiceImpl applyInfoService;

    @Autowired
    UserSession userSession;

    /**
    * 供应链金融 - 发起融资申请
    */
    @ApiOperation(value = "供应链金融 - 发起融资申请", notes = "发起融资申请, 需要用户事先登录, 并完善企业信息", response = ApplyInfo.class)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "applyType", value = "融资类型", required = false, dataType = "int", paramType = "form")
    })
    @LoginRequired
    @RequestMapping(value = "/api/financing/applyInfo", method = RequestMethod.POST)
    public Result requestFinancingOrder(@RequestBody ApplyInfo applyInfo) {

        System.out.println("Order Type:" + applyInfo.getApplyType());

        applyInfo.setSourceId(Utils.generateSourceId("JR"));
        applyInfo.setUserId(userSession.getUser().getId());
        applyInfo.setApplyDateTime(LocalDateTime.now());
        applyInfoService.save(applyInfo);

        return Result.success().setData(new HashMap() {{put("userId", applyInfo.getUserId()); put("orderId", applyInfo.getSourceId());}} );
    }




    /**
     * 供应链金融 - 用户中心 - 获取融资申请列表
     */
    @ApiOperation(value = "融资申请列表", notes = "用户查询融资申请列表", response = ApplyInfo.class, responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页数", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "count", value = "每页显示数量", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "offset", value = "偏移数", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "total", value = "结果总数量", required = false, dataType = "int", paramType = "query")
    })
    @LoginRequired
    @RequestMapping(value = "/api/financing/applyInfo", method = RequestMethod.GET)
    public Result getFinancingApplyInfoList(@RequestParam(value = "applyType", required = false ) String applyType) {

        return Result.success().setData(new ArrayList()
        {{
            add(new ApplyInfo());
            add(new ApplyInfo());
            add(new ApplyInfo());
            add(new ApplyInfo());
        }}
        );
    }



    /**
     * 供应链金融 - 用户中心 - 获取融资申请详细信息
     */
    @ApiOperation(value = "融资申请详细信息", notes = "用户获取融资申请详细信息", response = ApplyInfo.class)
    @ApiImplicitParam(name = "sourceId", value = "融资申请ID", required = true, dataType = "String", paramType = "path")
    @LoginRequired
    @RequestMapping(value = "/api/financing/applyInfo/{sourceId}", method = RequestMethod.GET)
    public Result getFinancingApplyInfo(@PathVariable String sourceId) {

        return Result.success().setData(new HashMap()
            {{
                put("sourceId", 12222);
                put("applyType", "MYR");
                put("approveState", 2);
                put("financingAmount", 2100000);
                put("expectedDate", 2100000);
            }}
        );
    }
}



