package com.yimei.finance.controllers.admin.common;

import
        com.yimei.finance.config.session.AdminSession;
import com.yimei.finance.representation.admin.group.EnumSpecialGroup;
import com.yimei.finance.representation.admin.user.UserObject;
import com.yimei.finance.representation.common.result.Result;
import com.yimei.finance.service.admin.user.AdminGroupServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"admin-api-userList"}, description = "没有用到, 获取管理人员列表 如:交易员列表 等")
@RequestMapping("/api/financing/admin")
@RestController("adminPersonListController")
public class PersonListController {
    @Autowired
    private AdminSession adminSession;
    @Autowired
    private AdminGroupServiceImpl groupService;

    @RequestMapping(value = "/trader", method = RequestMethod.GET)
    @ApiOperation(value = "获取本公司交易员列表", notes = "获取交易员列表数据", response = UserObject.class, responseContainer = "List")
    public Result getFinanceOnlineTraderListMethod() {
        return groupService.findCompanyUserListByGroupId(EnumSpecialGroup.OnlineTraderGroup.id, adminSession.getUser());
    }

    @RequestMapping(value = "/salesman", method = RequestMethod.GET)
    @ApiOperation(value = "获取本公司业务员列表", notes = "获取业务员列表数据", response = UserObject.class, responseContainer = "List")
    public Result getFinanceSalesmanListMethod() {
        return groupService.findCompanyUserListByGroupId(EnumSpecialGroup.SalesmanGroup.id, adminSession.getUser());
    }

    @RequestMapping(value = "/investigator", method = RequestMethod.GET)
    @ApiOperation(value = "获取本公司尽调员列表", notes = "获取尽调员列表数据", response = UserObject.class, responseContainer = "List")
    public Result getFinanceInvestigatorListMethod() {
        return groupService.findCompanyUserListByGroupId(EnumSpecialGroup.InvestigatorGroup.id, adminSession.getUser());
    }

    @RequestMapping(value = "/supervisor", method = RequestMethod.GET)
    @ApiOperation(value = "获取本公司监管员列表", notes = "获取监管员列表数据", response = UserObject.class, responseContainer = "List")
    public Result getFinanceSupervisorListMethod() {
        return groupService.findCompanyUserListByGroupId(EnumSpecialGroup.SupervisorGroup.id, adminSession.getUser());
    }

    @RequestMapping(value = "/riskmanager", method = RequestMethod.GET)
    @ApiOperation(value = "获取本公司风控人员列表", notes = "获取风控人员列表数据", response = UserObject.class, responseContainer = "List")
    public Result getRiskManagerListMethod() {
        return groupService.findCompanyUserListByGroupId(EnumSpecialGroup.RiskGroup.id, adminSession.getUser());
    }

}
