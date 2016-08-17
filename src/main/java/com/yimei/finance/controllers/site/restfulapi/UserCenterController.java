package com.yimei.finance.controllers.site.restfulapi;

import com.yimei.finance.entity.admin.user.ApplyInfo;
import com.yimei.finance.ext.annotations.LoginRequired;
import com.yimei.finance.repository.admin.applyinfo.ApplyInfoRepository;
import com.yimei.finance.repository.admin.applyinfo.EnumAdminFinanceError;
import com.yimei.finance.repository.common.result.Result;
import com.yimei.finance.repository.tpl.JpaRepositoryDemo;
import com.yimei.finance.repository.tpl.TplRepository;
import com.yimei.finance.utils.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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
@RequestMapping("/api/financing/site")
@RestController
public class UserCenterController {

    @Autowired
    ApplyInfoRepository applyInfoRepository;
    @Autowired
    TplRepository tplRepository;
    @Autowired
    JpaRepositoryDemo jpaRepositoryDemo;

    /**
    * 供应链金融 - 发起融资申请
    */
    @ApiOperation(value = "供应链金融 - 发起融资申请 API", notes = "发起融资申请, 需要用户事先登录, 并完善企业信息")
    @ApiImplicitParam(name = "group", value = "Group 对象", required = true, dataType = "GroupEntity", paramType = "body")
    @LoginRequired
    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    public Result requestFinancingOrder(@RequestBody ApplyInfo applyInfo) {

        System.out.println("Order Type:" + applyInfo.getApplyType());
        System.out.println("UserId:" + applyInfo.getUserId());
        applyInfo.setSourceId(Utils.generateSourceId("JR"));
        applyInfo.setApplyDateTime(LocalDateTime.now());
        applyInfoRepository.save(applyInfo);

        return Result.success().setData(new HashMap() {{put("userId", applyInfo.getUserId()); put("orderId", applyInfo.getSourceId());}} );
    }


    /**
     * 供应链金融 - 用户中心 - 获取融资申请列表
     */
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
//    @LoginRequired
    @RequestMapping(value = "/applyInfo/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据 id 查看金融申请单", notes = "根据 金融申请单id 查看金融申请单")
    @ApiImplicitParam(name = "id", value = "金融申请单id", required = true, dataType = "Long", paramType = "path")
    public Result getFinancingApplyInfo(@PathVariable("id") Long id) {
        ApplyInfo applyInfo = applyInfoRepository.findById(id);
        if (applyInfo == null) return Result.error(EnumAdminFinanceError.此金融单不存在.toString());
        return Result.success().setData(applyInfo);
    }
}



