package com.yimei.finance.controllers.admin.common;

import com.yimei.finance.entity.admin.user.EnumSpecialGroup;
import com.yimei.finance.entity.admin.user.UserObject;
import com.yimei.finance.entity.common.result.Result;
import com.yimei.finance.utils.DozerUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"admin-api-adminlist"}, description = "获取相关人员列表接口")
@RequestMapping("/api/financing/admin")
@RestController("adminPersonListController")
public class PersonListController {
    @Autowired
    IdentityService identityService;

    @RequestMapping(value = "/trader", method = RequestMethod.GET)
    @ApiOperation(value = "获取交易员列表", notes = "获取交易员列表数据", response = UserObject.class, responseContainer = "List")
    public Result getFinanceOnlineTraderListMethod() {
        return Result.success().setData(DozerUtils.copy(identityService.createUserQuery().memberOfGroup(EnumSpecialGroup.OnlineTraderGroup.id).list(), UserObject.class));
    }

    @RequestMapping(value = "/salesman", method = RequestMethod.GET)
    @ApiOperation(value = "获取业务员列表", notes = "获取业务员列表数据", response = UserObject.class, responseContainer = "List")
    public Result getFinanceSalesmanListMethod() {
        return Result.success().setData(DozerUtils.copy(identityService.createUserQuery().memberOfGroup(EnumSpecialGroup.SalesmanGroup.id).list(), UserObject.class));
    }

    @RequestMapping(value = "/investigator", method = RequestMethod.GET)
    @ApiOperation(value = "获取尽调员列表", notes = "获取尽调员列表数据", response = UserObject.class, responseContainer = "List")
    public Result getFinanceInvestigatorListMethod() {
        return Result.success().setData(DozerUtils.copy(identityService.createUserQuery().memberOfGroup(EnumSpecialGroup.InvestigatorGroup.id).list(), UserObject.class));
    }

    @RequestMapping(value = "/supervisor", method = RequestMethod.GET)
    @ApiOperation(value = "获取监管员列表", notes = "获取监管员列表数据", response = UserObject.class, responseContainer = "List")
    public Result getFinanceSupervisorListMethod() {
        return Result.success().setData(DozerUtils.copy(identityService.createUserQuery().memberOfGroup(EnumSpecialGroup.SupervisorGroup.id).list(), UserObject.class));
    }

    @RequestMapping(value = "/riskmanager", method = RequestMethod.GET)
    @ApiOperation(value = "获取风控人员列表", notes = "获取风控人员列表数据", response = UserObject.class, responseContainer = "List")
    public Result getRiskManagerListMethod() {
        return Result.success().setData(DozerUtils.copy(identityService.createUserQuery().memberOfGroup(EnumSpecialGroup.RiskGroup.id).list(), UserObject.class));
    }

}
