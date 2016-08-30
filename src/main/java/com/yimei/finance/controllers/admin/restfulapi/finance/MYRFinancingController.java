package com.yimei.finance.controllers.admin.restfulapi.finance;

import com.yimei.finance.config.session.AdminSession;
import com.yimei.finance.entity.admin.finance.FinanceOrder;
import com.yimei.finance.entity.common.result.CombineObject;
import com.yimei.finance.entity.common.result.Result;
import com.yimei.finance.entity.common.result.TaskMap;
import com.yimei.finance.service.admin.finance.FinanceFlowStepServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "admin-api-flow-myr", description = "煤易融相关接口")
@RequestMapping("/api/financing/admin/myr")
@RestController("adminMYRFinancingController")
public class MYRFinancingController {
    @Autowired
    private AdminSession adminSession;
    @Autowired
    private FinanceFlowStepServiceImpl flowStepService;


    @RequestMapping(value = "/onlinetrader/audit/{taskId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "线上交易员审核并填写材料", notes = "线上交易员审核并填写材料", response = Boolean.class)
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path")
    public Result myrOnlineTraderAddMaterialMethod(@PathVariable("taskId") String taskId,
                                                   @ApiParam(name = "map", value = "参数body对象", required = true) @RequestBody @Valid CombineObject<TaskMap, FinanceOrder> map) {
        return flowStepService.onlineTraderAuditFinanceOrderMethod(adminSession.getUser().getId(), taskId, map.t, map.u);
    }

    @RequestMapping(value = "/salesman/audit/{taskId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "业务员审核并填写材料", notes = "业务员审核并填写材料", response = Boolean.class)
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path")
    public Result myrSalesmanAddMaterialAndAuditMethod(@PathVariable("taskId") String taskId,
                                                       @ApiParam(name = "taskMap", value = "任务相关参数", required = true) @Valid TaskMap taskMap) {
        return flowStepService.salesmanAutidFinanceOrderMethod(adminSession.getUser().getId(), taskId, taskMap);
    }

    @RequestMapping(value = "/salesman/supply/investigation/material/{taskId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "业务员补充尽调材料", notes = "业务员补充尽调材料", response = Boolean.class)
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path")
    public Result myrSalesmanSupplyInvestigationMaterialMethod(@PathVariable("taskId") String taskId,
                                                               @ApiParam(name = "taskMap", value = "任务相关参数", required = true) @Valid TaskMap taskMap) {
        return flowStepService.salesmanSupplyInvestigationMaterialFinanceOrderMethod(adminSession.getUser().getId(), taskId, taskMap);
    }

    @RequestMapping(value = "/investigator/audit/{taskId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "尽调员审核", notes = "尽调员审核", response = Boolean.class)
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path")
    public Result myrInvestigatorAuditMethod(@PathVariable("taskId") String taskId,
                                             @ApiParam(name = "taskMap", value = "任务相关参数", required = true) @Valid TaskMap taskMap) {
        return flowStepService.investigatorAuditFinanceOrderMethod(adminSession.getUser().getId(), taskId, taskMap);
    }

    @RequestMapping(value = "/investigator/supply/riskmanager/material/{taskId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "尽调员补充风控材料", notes = "尽调员补充风控人员要求的材料")
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path")
    public Result myrInvestigatorSupplyRiskManagerMaterialMethod(@PathVariable("taskId") String taskId,
                                                                 @ApiParam(name = "taskMap", value = "任务相关参数", required = true) @Valid TaskMap taskMap) {
        return flowStepService.investigatorSupplyRiskMaterialFinanceOrderMethod(adminSession.getUser().getId(), taskId, taskMap);
    }

    @RequestMapping(value = "/riskmanager/audit/{taskId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "风控人员审核", notes = "风控人员审核")
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "Integer", paramType = "path")
    public Result myrRiskManagerAuditMethod(@PathVariable("taskId") String taskId,
                                            @ApiParam(name = "taskMap", value = "任务相关参数", required = true) @Valid TaskMap taskMap) {
        return flowStepService.riskManagerAuditMYRFinanceOrderMethod(adminSession.getUser().getId(), taskId, taskMap);
    }

    @RequestMapping(value = "/riskmanager/contract/{taskId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "风控人员上传合同", notes = "风控人员上传合同,流程完成", response = Boolean.class)
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "Integer", paramType = "path")
    public Result myrRiskManagerUploadContractMethod(@PathVariable("taskId") String taskId,
                                                     @ApiParam(name = "taskMap", value = "任务相关参数", required = true) @Valid TaskMap taskMap) {
        return flowStepService.riskManagerAddContractFinanceOrderMethod(adminSession.getUser().getId(), taskId, taskMap);
    }


}
