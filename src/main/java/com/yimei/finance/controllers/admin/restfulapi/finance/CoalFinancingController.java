package com.yimei.finance.controllers.admin.restfulapi.finance;

import com.yimei.finance.entity.admin.EnumGroupId;
import com.yimei.finance.repository.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 处理煤易融相关逻辑
 */
@RequestMapping("/api/finance/financing")
@Api(value = "CoalFinancingApi", description = "煤易融相关接口")
@RestController
public class CoalFinancingController {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private IdentityService identityService;

    @RequestMapping(value = "/start", method = RequestMethod.POST)
    @ApiOperation(value = "发起流程", notes = "发起流程")
    @ApiImplicitParam(name = "id", value = "金融申请单id", required = true, dataType = "String", paramType = "query")
    public Object startFinancingWorkFlow(@RequestParam(value = "id", required = true) String id) {
        runtimeService.startProcessInstanceByKey("financingWorkFlow", id);
        return Result.success();
    }

    @RequestMapping(value = "/assignTrader", method = RequestMethod.GET)
    @ApiOperation(value = "分配交易员页面", notes = "分配线上交易员页面")
    @ApiImplicitParam(name = "id", value = "金融申请单id", required = true, dataType = "String", paramType = "query")
    public Object assignFinancingOnlineTrader() {
        return Result.success().setData(identityService.createGroupQuery().groupId(EnumGroupId.ManageTraderGroup.id));
    }

}
