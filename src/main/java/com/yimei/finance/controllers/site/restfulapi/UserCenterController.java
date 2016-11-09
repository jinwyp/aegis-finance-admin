package com.yimei.finance.controllers.site.restfulapi;

import com.yimei.finance.config.session.UserSession;
import com.yimei.finance.entity.admin.finance.FinanceOrder;
import com.yimei.finance.ext.annotations.LoginRequired;
import com.yimei.finance.representation.admin.finance.object.FinanceOrderObject;
import com.yimei.finance.representation.admin.finance.object.validated.CreateFinanceOrder;
import com.yimei.finance.representation.common.result.MapObject;
import com.yimei.finance.representation.common.result.Page;
import com.yimei.finance.representation.common.result.Result;
import com.yimei.finance.representation.site.finance.result.FinanceOrderSearch;
import com.yimei.finance.service.site.finance.SiteFinanceOrderServiceImpl;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/financing")
@Api(tags = {"site-api"})
@RestController("siteUserCenterController")
public class UserCenterController {
    @Autowired
    private UserSession userSession;
    @Autowired
    private SiteFinanceOrderServiceImpl financeOrderService;

    @LoginRequired
    @Transactional
    @ApiOperation(value = "供应链金融 - 发起融资申请", notes = "发起融资申请, 需要用户事先登录, 并完善企业信息", response = FinanceOrder.class)
    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    public Result requestFinancingOrder(@ApiParam(name = "financeOrder", value = "只需填写applyType 字段即可", required = true) @Validated(CreateFinanceOrder.class) @RequestBody FinanceOrderObject financeOrderObject) {
        System.out.println("Order Type:" + financeOrderObject.getApplyType());
        return financeOrderService.customerApplyFinanceOrder(financeOrderObject, userSession.getUser());
    }

    @ApiOperation(value = "融资申请列表", notes = "用户查询融资申请列表", response = FinanceOrder.class, responseContainer = "List")
    @LoginRequired
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数", required = false, defaultValue = "0", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "sourceId", value = "业务编号", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "startDate", value = "开始时间", required = false, dataType = "Date", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束时间", required = false, dataType = "Date", paramType = "query"),
            @ApiImplicitParam(name = "approveStateId", value = "审批状态id", required = false, defaultValue = "0", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "applyType", value = "业务类型", required = false, dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/financeOrder", method = RequestMethod.GET)
    public Result getFinancingApplyInfoList(FinanceOrderSearch orderSearch, Page page) {
        page.setCount(10);
        return financeOrderService.getFinanceOrderBySelect(Long.valueOf(userSession.getUser().getId()), Long.valueOf(userSession.getUser().getCompanyId()), orderSearch, page);
    }

    @ApiOperation(value = "根据 id 查看金融申请单", notes = "根据 金融申请单id 查看金融申请单", response = FinanceOrder.class)
    @ApiImplicitParam(name = "id", value = "金融申请单id", required = true, dataType = "Long", paramType = "path")
    @LoginRequired
    @RequestMapping(value = "/financeOrder/{id}", method = RequestMethod.GET)
    public Result getFinancingApplyInfo(@PathVariable("id") Long id) {
        return financeOrderService.findByIdAndUserIdOrCompanyId(id, Long.valueOf(userSession.getUser().getId()), Long.valueOf(userSession.getUser().getCompanyId()));
    }

    @RequestMapping(value = "/status", method = RequestMethod.GET)
    @ApiOperation(value = "融资申请状态列表", notes = "融资申请状态列表", response = MapObject.class, responseContainer = "List")
    public Result findFinanceStatusList() {
        return financeOrderService.financeOrderStatusList();
    }



    @ApiOperation(value = "融资申请相关合同列表", notes = "用户查询融资申请相关合同列表", response = FinanceOrder.class, responseContainer = "List")
    @LoginRequired
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数", required = false, defaultValue = "0", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "sourceId", value = "业务编号", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "startDate", value = "开始时间", required = false, dataType = "Date", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束时间", required = false, dataType = "Date", paramType = "query"),
            @ApiImplicitParam(name = "approveStateId", value = "审批状态id", required = false, defaultValue = "0", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "applyType", value = "业务类型", required = false, dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/contract", method = RequestMethod.GET)
    public Result getFinancingContractList(FinanceOrderSearch orderSearch, Page page) {
        page.setCount(10);
        return financeOrderService.getFinanceOrderBySelect(Long.valueOf(userSession.getUser().getId()), Long.valueOf(userSession.getUser().getCompanyId()), orderSearch, page);
    }

    @ApiOperation(value = "查看合同详情预览", notes = "根据 合同id 查看合同详情预览", response = FinanceOrder.class)
    @ApiImplicitParam(name = "id", value = "合同id", required = true, dataType = "Long", paramType = "path")
    @LoginRequired
    @RequestMapping(value = "/contract/{id}", method = RequestMethod.GET)
    public Result getFinancingContractInfo(@PathVariable("id") Long id) {
        return financeOrderService.findByIdAndUserIdOrCompanyId(id, Long.valueOf(userSession.getUser().getId()), Long.valueOf(userSession.getUser().getCompanyId()));
    }

}




