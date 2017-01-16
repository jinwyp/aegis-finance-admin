package com.yimei.finance.site.controller.restfulapi.finance;

import com.yimei.finance.config.session.UserSession;
import com.yimei.finance.entity.admin.finance.FinanceOrder;
import com.yimei.finance.ext.annotations.LoginRequired;
import com.yimei.finance.admin.representation.finance.object.FinanceOrderObject;
import com.yimei.finance.admin.representation.finance.object.validated.CreateFinanceOrder;
import com.yimei.finance.common.representation.result.Result;
import com.yimei.finance.site.service.finance.SiteFinanceOrderServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/financing")
@Api(tags = {"site-api"})
@RestController("siteFinanceController")
public class SiteFinanceController {
    @Autowired
    private UserSession userSession;
    @Autowired
    private SiteFinanceOrderServiceImpl financeOrderService;

    @LoginRequired
    @ApiOperation(value = "供应链金融 - 发起融资申请", notes = "发起融资申请, 需要用户事先登录, 并完善企业信息", response = FinanceOrder.class)
    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    public Result requestFinancingOrder(@ApiParam(name = "financeOrder", value = "只需填写applyType 字段即可", required = true) @Validated(CreateFinanceOrder.class) @RequestBody FinanceOrderObject financeOrderObject) {
        System.out.println("Order Type:" + financeOrderObject.getApplyType());
        return financeOrderService.customerApplyFinanceOrder(financeOrderObject, userSession.getUser());
    }
}
