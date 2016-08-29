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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "admin-api-flow-myd", description = "煤易贷相关接口")
@RequestMapping("/api/financing/admin/myd")
@RestController("adminMYDFinancingController")
public class MYDFinancingController {
    @Autowired
    private FinanceFlowStepServiceImpl flowStepService;
    @Autowired
    private AdminSession adminSession;

    @RequestMapping(value = "/onlinetrader/audit/{taskId}", method = RequestMethod.POST)
    @ApiOperation(value = "线上交易员审核并填写材料", notes = "线上交易员审核并填写材料", response = Boolean.class)
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path")
    public Result mydOnlineTraderAddMaterialMethod(@PathVariable("taskId") String taskId,
                                                   @ApiParam(name = "map", value = "任务相关参数", required = true) @RequestBody @Valid CombineObject<TaskMap, FinanceOrder> map) {
        return flowStepService.onlineTraderAuditFinanceOrderMethod(adminSession.getUser().getId(), taskId, map.t, map.u);
    }

    @RequestMapping(value = "/salesman/audit/{taskId}", method = RequestMethod.POST)
    @ApiOperation(value = "业务员审核并填写材料", notes = "业务员审核并填写材料", response = Boolean.class)
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path")
    public Result mydSalesmanAddMaterialAndAuditMethod(@PathVariable("taskId") String taskId,
                                                       @ApiParam(name = "taskMap", value = "任务相关参数", required = true) @Valid TaskMap taskMap) {
        return flowStepService.salesmanAutidFinanceOrderMethod(adminSession.getUser().getId(), taskId, taskMap);
    }

    @RequestMapping(value = "/salesman/supply/investigation/material/{taskId}", method = RequestMethod.POST)
    @ApiOperation(value = "业务员补充尽调材料", notes = "业务员补充尽调材料", response = Boolean.class)
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path")
    public Result mydSalesmanSupplyInvestigationMaterialMethod(@PathVariable("taskId") String taskId,
                                                               @ApiParam(name = "taskMap", value = "任务相关参数", required = true) @Valid TaskMap taskMap) {
        return flowStepService.salesmanSupplyInvestigationMaterialFinanceOrderMethod(adminSession.getUser().getId(), taskId, taskMap);
    }

    @RequestMapping(value = "/investigator/audit/{taskId}", method = RequestMethod.POST)
    @ApiOperation(value = "尽调员审核", notes = "尽调员审核", response = Boolean.class)
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path")
    public Result mydInvestigatorAuditMethod(@PathVariable("taskId") String taskId,
                                             @ApiParam(name = "taskMap", value = "任务相关参数", required = true) @Valid TaskMap taskMap) {
        return flowStepService.investigatorAuditFinanceOrderMethod(adminSession.getUser().getId(), taskId, taskMap);
    }

    @RequestMapping(value = "/salesman/supply/supervision/material/{taskId}", method = RequestMethod.POST)
    @ApiOperation(value = "业务员补充监管材料", notes = "业务员补充监管材料")
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path")
    public Result mydSalesmanSupplySupervisionMaterialMethod(@PathVariable("taskId") String taskId,
                                                             @ApiParam(name = "taskMap", value = "任务相关参数", required = true) @Valid TaskMap taskMap) {
        return flowStepService.salesmanSupplySupervisionMaterialFinanceOrderMethod(adminSession.getUser().getId(), taskId, taskMap);
    }

    @RequestMapping(value = "/supervisor/audit/{taskId}", method = RequestMethod.POST)
    @ApiOperation(value = "监管员审核", notes = "监管员审核", response = Boolean.class)
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path")
    public Result mydSupervisorAuditMethod(@PathVariable("taskId") String taskId,
                                           @ApiParam(name = "taskMap", value = "任务相关参数", required = true) @Valid TaskMap taskMap) {
        return flowStepService.supervisorAuditFinanceOrderMethod(adminSession.getUser().getId(), taskId, taskMap);
    }

    @RequestMapping(value = "/investigator/supply/riskmanager/material/{taskId}", method = RequestMethod.POST)
    @ApiOperation(value = "尽调员补充材料", notes = "尽调员补充风控人员要求的材料")
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path")
    public Result mydInvestigatorSupplyRiskManagerMaterialMethod(@PathVariable("taskId") String taskId,
                                                                 @ApiParam(name = "taskMap", value = "任务相关参数", required = true) @Valid TaskMap taskMap) {
        return flowStepService.investigatorSupplyRiskMaterialFinanceOrderMethod(adminSession.getUser().getId(), taskId, taskMap);
    }

    @RequestMapping(value = "/supervisor/supply/riskmanager/material/{taskId}", method = RequestMethod.POST)
    @ApiOperation(value = "监管员补充材料", notes = "监管员补充风控人员要求的材料")
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "Integer", paramType = "path")
    public Result mydSupervisorSupplyRiskManagerMaterialMethod(@PathVariable("taskId") String taskId,
                                                               @ApiParam(name = "taskMap", value = "任务相关参数", required = true) @Valid TaskMap taskMap) {
        return flowStepService.supervisorSupplyRiskMaterialFinanceOrderMethod(adminSession.getUser().getId(), taskId, taskMap);
    }

    @RequestMapping(value = "/riskmanager/audit/{taskId}", method = RequestMethod.POST)
    @ApiOperation(value = "风控人员审核", notes = "风控人员审核")
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "Integer", paramType = "path")
    public Result mydRiskManagerAuditMethod(@PathVariable("taskId") String taskId,
                                            @ApiParam(name = "taskMap", value = "任务相关参数", required = true) @Valid TaskMap taskMap) {
        return flowStepService.riskManagerAuditMYRFinanceOrderMethod(adminSession.getUser().getId(), taskId, taskMap);
    }

    @RequestMapping(value = "/riskmanager/contract/{taskId}", method = RequestMethod.POST)
    @ApiOperation(value = "风控人员上传合同", notes = "风控人员上传合同,流程完成", response = Boolean.class)
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "Integer", paramType = "path")
    public Result mydRiskManagerUploadContractMethod(@PathVariable("taskId") String taskId,
                                                     @ApiParam(name = "taskMap", value = "任务相关参数", required = true) @Valid TaskMap taskMap) {
        return flowStepService.riskManagerAddContractFinanceOrderMethod(adminSession.getUser().getId(), taskId, taskMap);
    }
}
