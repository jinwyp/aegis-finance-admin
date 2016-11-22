package com.yimei.finance.controllers.site.restfulapi;

import com.yimei.finance.config.session.UserSession;
import com.yimei.finance.entity.admin.finance.FinanceOrder;
import com.yimei.finance.entity.admin.finance.FinanceOrderContract;
import com.yimei.finance.ext.annotations.LoginRequired;
import com.yimei.finance.representation.common.result.MapObject;
import com.yimei.finance.representation.common.result.Page;
import com.yimei.finance.representation.common.result.Result;
import com.yimei.finance.representation.site.finance.FinanceOrderContractAttachment;
import com.yimei.finance.representation.site.finance.FinanceOrderSearch;
import com.yimei.finance.service.site.finance.SiteFinanceOrderServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/financing")
@Api(tags = {"site-api"})
@RestController("siteUserCenterController")
public class SiteUserCenterController {
    @Autowired
    private UserSession userSession;
    @Autowired
    private SiteFinanceOrderServiceImpl financeOrderService;

    @ApiOperation(value = "融资申请列表", notes = "用户查询融资申请列表", response = FinanceOrder.class, responseContainer = "List")
    @LoginRequired
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数", required = false, defaultValue = "0", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "sourceId", value = "业务编号", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "startDate", value = "开始时间", required = false, dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束时间", required = false, dataType = "date", paramType = "query"),
            @ApiImplicitParam(name = "approveStateId", value = "审批状态id", required = false, defaultValue = "0", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "applyType", value = "业务类型", required = false, dataType = "string", paramType = "query")
    })
    @RequestMapping(value = "/financeOrder", method = RequestMethod.GET)
    public Result getFinancingApplyInfoList(FinanceOrderSearch orderSearch, Page page) {
        page.setCount(10);
        return financeOrderService.getFinanceOrderBySelect(Long.valueOf(userSession.getUser().getId()), Long.valueOf(userSession.getUser().getCompanyId()), orderSearch, page);
    }

    @ApiOperation(value = "根据 id 查看金融申请单", notes = "根据 金融申请单id 查看金融申请单", response = FinanceOrder.class)
    @ApiImplicitParam(name = "id", value = "金融申请单id", required = true, dataType = "int", paramType = "path")
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

    @ApiOperation(value = "查看合同详情", notes = "根据 合同金融id 查看合同详情", response = FinanceOrderContract.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "financeId", value = "金融申请单id", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "type", value = "合同类型", required = true, dataType = "int", paramType = "path")
    })
    @LoginRequired
    @RequestMapping(value = "/financeOrder/{financeId}/contract/{type}", method = RequestMethod.GET)
    public Result getFinancingContractInfo(@PathVariable("financeId") Long financeId,
                                           @PathVariable("type") int type) {
        return financeOrderService.findFinanceContractByFinanceIdUserIdCompanyId(financeId, type, Long.valueOf(userSession.getUser().getId()), Long.valueOf(userSession.getUser().getCompanyId()));
    }

    @ApiOperation(value = "根据金融单id查看合同附件列表", notes = "根据 金融单id 查看合同附件列表", response = FinanceOrderContractAttachment.class)
    @ApiImplicitParam(name = "id", value = "合同id", required = true, dataType = "int", paramType = "path")
    @LoginRequired
    @RequestMapping(value = "/financeOrder/{financeId}/contract/attachment", method = RequestMethod.GET)
    public Result getFinancingContractAttachmentList(@PathVariable("financeId") Long financeId) {
        return financeOrderService.findFinanceContractAttachmentListByFinanceIdUserIdCompanyId(financeId, Long.valueOf(userSession.getUser().getId()), Long.valueOf(userSession.getUser().getCompanyId()));
    }

}




