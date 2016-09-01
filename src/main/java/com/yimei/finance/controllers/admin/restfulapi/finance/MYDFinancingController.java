package com.yimei.finance.controllers.admin.restfulapi.finance;

import com.yimei.finance.config.session.AdminSession;
import com.yimei.finance.entity.admin.finance.*;
import com.yimei.finance.repository.admin.finance.FinanceOrderRepository;
import com.yimei.finance.representation.admin.finance.EnumAdminFinanceError;
import com.yimei.finance.representation.admin.finance.EnumFinanceOrderType;
import com.yimei.finance.representation.common.enums.EnumCommonError;
import com.yimei.finance.representation.common.result.CombineObject;
import com.yimei.finance.representation.common.result.Result;
import com.yimei.finance.representation.common.result.TaskMap;
import com.yimei.finance.service.admin.finance.FinanceFlowStepServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Api(tags = "admin-api-flow-myd", description = "煤易贷相关接口")
@RequestMapping("/api/financing/admin/myd")
@RestController("adminMYDFinancingController")
public class MYDFinancingController {
    @Autowired
    private FinanceFlowStepServiceImpl flowStepService;
    @Autowired
    private AdminSession adminSession;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private FinanceOrderRepository orderRepository;

    @RequestMapping(value = "/onlinetrader/audit/{taskId}", method = RequestMethod.POST)
    @ApiOperation(value = "线上交易员审核并填写材料", notes = "线上交易员审核并填写材料", response = Boolean.class)
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path")
    public Result mydOnlineTraderAddMaterialMethod(@PathVariable("taskId") String taskId,
                                                   @ApiParam(name = "map", value = "参数body对象", required = true) @RequestBody CombineObject<TaskMap, FinanceOrder> map) {
        Result result = checkMYDMethod(taskId, map.t);
        if (!result.isSuccess()) return result;
        CombineObject<Task, Long> object = (CombineObject<Task, Long>) result.getData();
        FinanceOrder order = map.u;
        order.setId(object.u);
        return flowStepService.onlineTraderAuditFinanceOrderMethod(adminSession.getUser().getId(), map.t, object.t, order);
    }

    @RequestMapping(value = "/salesman/audit/{taskId}", method = RequestMethod.POST)
    @ApiOperation(value = "业务员审核并填写材料", notes = "业务员审核并填写材料", response = Boolean.class)
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path")
    public Result mydSalesmanAddMaterialAndAuditMethod(@PathVariable("taskId") String taskId,
                                                       @ApiParam(name = "taskMap", value = "任务相关参数", required = true) @RequestBody CombineObject<TaskMap, FinanceOrderSalesmanInfo> map) {
        Result result = checkMYDMethod(taskId, map.t);
        if (!result.isSuccess()) return result;
        CombineObject<Task, Long> object = (CombineObject<Task, Long>) result.getData();
        return flowStepService.salesmanAuditFinanceOrderMethod(adminSession.getUser().getId(), map.t, map.u, object.t, object.u);
    }

    @RequestMapping(value = "/salesman/supply/investigation/material/{taskId}", method = RequestMethod.POST)
    @ApiOperation(value = "业务员补充尽调材料", notes = "业务员补充尽调材料", response = Boolean.class)
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path")
    public Result mydSalesmanSupplyInvestigationMaterialMethod(@PathVariable("taskId") String taskId,
                                                               @ApiParam(name = "taskMap", value = "任务相关参数", required = true) @RequestBody CombineObject<TaskMap, FinanceOrderInvestigatorInfo> map) {
        Result result = checkMYDMethod(taskId, map.t);
        if (!result.isSuccess()) return result;
        CombineObject<Task, Long> object = (CombineObject<Task, Long>) result.getData();
        return flowStepService.salesmanSupplyInvestigationMaterialFinanceOrderMethod(adminSession.getUser().getId(), map.t, map.u, object.t, object.u);
    }

    @RequestMapping(value = "/investigator/audit/{taskId}", method = RequestMethod.POST)
    @ApiOperation(value = "尽调员审核", notes = "尽调员审核", response = Boolean.class)
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path")
    public Result mydInvestigatorAuditMethod(@PathVariable("taskId") String taskId,
                                             @ApiParam(name = "taskMap", value = "任务相关参数", required = true) @RequestBody CombineObject<TaskMap, FinanceOrderInvestigatorInfo> map) {
        Result result = checkMYDMethod(taskId, map.t);
        if (!result.isSuccess()) return result;
        CombineObject<Task, Long> object = (CombineObject<Task, Long>) result.getData();
        return flowStepService.investigatorAuditFinanceOrderMethod(adminSession.getUser().getId(), map.t, object.t, object.u);
    }

    @RequestMapping(value = "/salesman/supply/supervision/material/{taskId}", method = RequestMethod.POST)
    @ApiOperation(value = "业务员补充监管材料", notes = "业务员补充监管材料")
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path")
    public Result mydSalesmanSupplySupervisionMaterialMethod(@PathVariable("taskId") String taskId,
                                                             @ApiParam(name = "taskMap", value = "任务相关参数", required = true) @RequestBody TaskMap taskMap) {
        Result result = checkMYDMethod(taskId, taskMap);
        if (!result.isSuccess()) return result;
        CombineObject<Task, Long> object = (CombineObject<Task, Long>) result.getData();
        return flowStepService.salesmanSupplySupervisionMaterialFinanceOrderMethod(adminSession.getUser().getId(), taskMap, object.t, object.u);
    }

    @RequestMapping(value = "/supervisor/audit/{taskId}", method = RequestMethod.POST)
    @ApiOperation(value = "监管员审核", notes = "监管员审核", response = Boolean.class)
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path")
    public Result mydSupervisorAuditMethod(@PathVariable("taskId") String taskId,
                                           @ApiParam(name = "taskMap", value = "任务相关参数", required = true) @RequestBody CombineObject<TaskMap, FinanceOrderSupervisorInfo> map) {
        Result result = checkMYDMethod(taskId, map.t);
        if (!result.isSuccess()) return result;
        CombineObject<Task, Long> object = (CombineObject<Task, Long>) result.getData();
        return flowStepService.supervisorAuditFinanceOrderMethod(adminSession.getUser().getId(), map.t, object.t, object.u);
    }

    @RequestMapping(value = "/investigator/supply/riskmanager/material/{taskId}", method = RequestMethod.POST)
    @ApiOperation(value = "尽调员补充材料", notes = "尽调员补充风控人员要求的材料")
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path")
    public Result mydInvestigatorSupplyRiskManagerMaterialMethod(@PathVariable("taskId") String taskId,
                                                                 @ApiParam(name = "taskMap", value = "任务相关参数", required = true) @RequestBody TaskMap taskMap) {
        Result result = checkMYDMethod(taskId, taskMap);
        if (!result.isSuccess()) return result;
        CombineObject<Task, Long> object = (CombineObject<Task, Long>) result.getData();
        return flowStepService.investigatorSupplyRiskMaterialFinanceOrderMethod(adminSession.getUser().getId(), taskMap, object.t, object.u);
    }

    @RequestMapping(value = "/supervisor/supply/riskmanager/material/{taskId}", method = RequestMethod.POST)
    @ApiOperation(value = "监管员补充材料", notes = "监管员补充风控人员要求的材料")
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "Integer", paramType = "path")
    public Result mydSupervisorSupplyRiskManagerMaterialMethod(@PathVariable("taskId") String taskId,
                                                               @ApiParam(name = "taskMap", value = "任务相关参数", required = true) @RequestBody TaskMap taskMap) {
        Result result = checkMYDMethod(taskId, taskMap);
        if (!result.isSuccess()) return result;
        CombineObject<Task, Long> object = (CombineObject<Task, Long>) result.getData();
        return flowStepService.supervisorSupplyRiskMaterialFinanceOrderMethod(adminSession.getUser().getId(), taskMap, object.t, object.u);
    }

    @RequestMapping(value = "/riskmanager/audit/{taskId}", method = RequestMethod.POST)
    @ApiOperation(value = "风控人员审核", notes = "风控人员审核")
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "Integer", paramType = "path")
    public Result mydRiskManagerAuditMethod(@PathVariable("taskId") String taskId,
                                            @ApiParam(name = "taskMap", value = "任务相关参数", required = true) @RequestBody CombineObject<TaskMap, FinanceOrderRiskManagerInfo> map) {
        Result result = checkMYDMethod(taskId, map.t);
        if (!result.isSuccess()) return result;
        CombineObject<Task, Long> object = (CombineObject<Task, Long>) result.getData();
        return flowStepService.riskManagerAuditMYRFinanceOrderMethod(adminSession.getUser().getId(), map.t, object.t, object.u);
    }

    private Result checkMYDMethod(String taskId, TaskMap taskMap) {
        if (taskMap.getSubmit() != 0 && taskMap.getSubmit() != 1) return Result.error(EnumCommonError.Admin_System_Error);
        Task task = taskService.createTaskQuery().taskId(taskId).active().taskAssignee(adminSession.getUser().getId()).singleResult();
        if (task == null) return Result.error(EnumAdminFinanceError.你没有权限处理此任务或者你已经处理过.toString());
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        if (processInstance == null || StringUtils.isEmpty(processInstance.getBusinessKey())) return Result.error(EnumCommonError.Admin_System_Error);
        FinanceOrder financeOrder = orderRepository.findOne(Long.valueOf(processInstance.getBusinessKey()));
        if (financeOrder == null) return Result.error(EnumCommonError.Admin_System_Error);
        if (!financeOrder.getApplyType().equals(EnumFinanceOrderType.MYD.toString())) return Result.error(EnumAdminFinanceError.此订单不是煤易融业务.toString());
        CombineObject<Task, Long> map = new CombineObject<>(task, Long.valueOf(processInstance.getBusinessKey()));
        return Result.success().setData(map);
    }
}
