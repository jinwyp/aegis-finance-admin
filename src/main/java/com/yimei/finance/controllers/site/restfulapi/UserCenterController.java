package com.yimei.finance.controllers.site.restfulapi;

import com.yimei.finance.config.session.UserSession;
import com.yimei.finance.entity.admin.finance.FinanceApplyInfo;
import com.yimei.finance.ext.annotations.LoginRequired;
import com.yimei.finance.repository.admin.applyinfo.FinanceApplyInfoRepository;
import com.yimei.finance.repository.admin.applyinfo.EnumAdminFinanceError;
import com.yimei.finance.entity.common.result.Page;
import com.yimei.finance.entity.common.result.Result;
import com.yimei.finance.repository.tpl.JpaRepositoryDemo;
import com.yimei.finance.repository.tpl.TplRepository;
import com.yimei.finance.utils.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by JinWYP on 8/15/16.
 */
@RequestMapping("/api/financing")
@Api(tags = {"site-api"})
@RestController
public class UserCenterController {

    @Autowired
    FinanceApplyInfoRepository financeApplyInfoRepository;
    @Autowired
    TplRepository tplRepository;
    @Autowired
    JpaRepositoryDemo jpaRepositoryDemo;
    @Autowired
    UserSession userSession;

    /**
    * 供应链金融 - 发起融资申请
    */
//    @LoginRequired
    @ApiOperation(value = "供应链金融 - 发起融资申请", notes = "发起融资申请, 需要用户事先登录, 并完善企业信息", response = FinanceApplyInfo.class)
    @ApiImplicitParam(name = "applyType", value = "融资类型", required = false, dataType = "String", paramType = "form")
    @LoginRequired
    @RequestMapping(value = "/applyInfo", method = RequestMethod.POST)
    public Result requestFinancingOrder(@RequestBody FinanceApplyInfo financeApplyInfo) {

        System.out.println("Order Type:" + financeApplyInfo.getApplyType());

        financeApplyInfo.setSourceId(Utils.generateSourceId("JR"));
        financeApplyInfo.setUserId(userSession.getUser().getId());
        financeApplyInfo.setApplyDateTime(LocalDateTime.now());
        financeApplyInfoRepository.save(financeApplyInfo);
        return Result.success().setData(financeApplyInfoRepository.findOne(financeApplyInfo.getId()));
    }




    /**
     * 供应链金融 - 用户中心 - 获取融资申请列表
     */

    @ApiOperation(value = "融资申请列表", notes = "用户查询融资申请列表", response = FinanceApplyInfo.class, responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页数", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "count", value = "每页显示数量", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "offset", value = "偏移数", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "total", value = "结果总数量", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "applyType", value = "融资类型", required = false, dataType = "String", paramType = "query")
    })
    @LoginRequired
    @RequestMapping(value = "/applyInfo", method = RequestMethod.GET)
    public Result getFinancingApplyInfoList(@RequestParam(value = "applyType", required = false ) String applyType, Page page) {
        List<FinanceApplyInfo> financeApplyInfoList = financeApplyInfoRepository.findByUserId(userSession.getUser().getId());

        return Result.success().setData(financeApplyInfoList).setMeta(page);
    }



    /**
     * 供应链金融 - 用户中心 - 获取融资申请详细信息
     */
//    @LoginRequired

    @ApiOperation(value = "根据 id 查看金融申请单", notes = "根据 金融申请单id 查看金融申请单", response = FinanceApplyInfo.class)
    @ApiImplicitParam(name = "id", value = "金融申请单id", required = true, dataType = "Long", paramType = "path")
    @LoginRequired
    @RequestMapping(value = "/applyInfo/{id}", method = RequestMethod.GET)
    public Result getFinancingApplyInfo(@PathVariable("id") Long id) {
        FinanceApplyInfo financeApplyInfo = financeApplyInfoRepository.findOne(id);
        if (financeApplyInfo == null) return Result.error(EnumAdminFinanceError.此金融单不存在.toString());
        return Result.success().setData(financeApplyInfo);
    }
}



