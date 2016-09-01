package com.yimei.finance.controllers.admin.restfulapi.finance;

import com.yimei.finance.config.session.AdminSession;
import com.yimei.finance.entity.admin.finance.FinanceOrder;
import com.yimei.finance.entity.admin.finance.FinanceOrderInvestigatorInfo;
import com.yimei.finance.entity.admin.finance.FinanceOrderRiskManagerInfo;
import com.yimei.finance.entity.admin.finance.FinanceOrderSalesmanInfo;
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
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Api(tags = "admin-api-flow-myr", description = "煤易融相关接口")
@RequestMapping("/api/financing/admin/myr")
@RestController("adminMYRFinancingController")
public class MYRFinancingController {
    @Autowired
    private AdminSession adminSession;
    @Autowired
    private FinanceFlowStepServiceImpl flowStepService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private FinanceOrderRepository orderRepository;

    @RequestMapping(value = "/onlinetrader/audit/{taskId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "线上交易员审核并填写材料", notes = "线上交易员审核并填写材料", response = Boolean.class)
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path")
    public Result myrOnlineTraderAddMaterialMethod(@PathVariable("taskId") String taskId,
                                                   @ApiParam(name = "map", value = "参数body对象", required = true) @RequestBody CombineObject<TaskMap, FinanceOrder> map) {
        Result result = checkMYRMethod(taskId, map.t);
        if (!result.isSuccess()) return result;
        CombineObject<Task, String> object = (CombineObject<Task, String>) result.getData();
        FinanceOrder order = map.u;
        order.setId(Long.valueOf(object.getU()));
        return flowStepService.onlineTraderAuditFinanceOrderMethod(adminSession.getUser().getId(), map.t, object.getT(), order);
    }

    @RequestMapping(value = "/salesman/audit/{taskId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "业务员审核并填写材料", notes = "业务员审核并填写材料", response = Boolean.class)
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path")
    public Result myrSalesmanAddMaterialAndAuditMethod(@PathVariable("taskId") String taskId,
                                                       @ApiParam(name = "taskMap", value = "任务相关参数", required = true) @RequestBody CombineObject<TaskMap, FinanceOrderSalesmanInfo> map) {
        Result result = checkMYRMethod(taskId, map.t);
        if (!result.isSuccess()) return result;
        CombineObject<Task, String> object = (CombineObject<Task, String>) result.getData();
        return flowStepService.salesmanAuditFinanceOrderMethod(adminSession.getUser().getId(), map.t, map.u, object.t, Long.valueOf(object.u));
    }

    @RequestMapping(value = "/salesman/supply/investigation/material/{taskId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "业务员补充尽调材料", notes = "业务员补充尽调材料", response = Boolean.class)
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path")
    public Result myrSalesmanSupplyInvestigationMaterialMethod(@PathVariable("taskId") String taskId,
                                                               @ApiParam(name = "taskMap", value = "任务相关参数", required = true) @RequestBody CombineObject<TaskMap, FinanceOrderInvestigatorInfo> map) {
        Result result = checkMYRMethod(taskId, map.t);
        if (!result.isSuccess()) return result;
        CombineObject<Task, String> object = (CombineObject<Task, String>) result.getData();
        return flowStepService.salesmanSupplyInvestigationMaterialFinanceOrderMethod(adminSession.getUser().getId(), map.t, map.u, object.t, Long.valueOf(object.u));
    }

    @RequestMapping(value = "/investigator/audit/{taskId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "尽调员审核", notes = "尽调员审核", response = Boolean.class)
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path")
    public Result myrInvestigatorAuditMethod(@PathVariable("taskId") String taskId,
                                             @ApiParam(name = "taskMap", value = "任务相关参数", required = true) @RequestBody CombineObject<TaskMap, FinanceOrderInvestigatorInfo> map) {
        Result result = checkMYRMethod(taskId, map.t);
        if (!result.isSuccess()) return result;
        CombineObject<Task, String> object = (CombineObject<Task, String>) result.getData();
        return flowStepService.investigatorAuditFinanceOrderMethod(adminSession.getUser().getId(), map.t, object.t, Long.valueOf(object.u));
    }

    @RequestMapping(value = "/investigator/supply/riskmanager/material/{taskId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "尽调员补充风控材料", notes = "尽调员补充风控人员要求的材料")
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path")
    public Result myrInvestigatorSupplyRiskManagerMaterialMethod(@PathVariable("taskId") String taskId,
                                                                 @ApiParam(name = "taskMap", value = "任务相关参数", required = true) @RequestBody TaskMap taskMap) {
        Result result = checkMYRMethod(taskId, taskMap);
        if (!result.isSuccess()) return result;
        CombineObject<Task, String> object = (CombineObject<Task, String>) result.getData();
        return flowStepService.investigatorSupplyRiskMaterialFinanceOrderMethod(adminSession.getUser().getId(), taskMap, object.t, Long.valueOf(object.u));
    }

    @RequestMapping(value = "/riskmanager/audit/{taskId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "风控人员审核", notes = "风控人员审核")
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "Integer", paramType = "path")
    public Result myrRiskManagerAuditMethod(@PathVariable("taskId") String taskId,
                                            @ApiParam(name = "taskMap", value = "任务相关参数", required = true) @RequestBody CombineObject<TaskMap, FinanceOrderRiskManagerInfo> map) {
        Result result = checkMYRMethod(taskId, map.t);
        if (!result.isSuccess()) return result;
        CombineObject<Task, String> object = (CombineObject<Task, String>) result.getData();
        return flowStepService.riskManagerAuditMYRFinanceOrderMethod(adminSession.getUser().getId(), map.t, object.t, Long.valueOf(object.u));
    }

    private Result checkMYRMethod(String taskId, TaskMap taskMap) {
        if (taskMap.getSubmit() != 0 && taskMap.getSubmit() != 1) return Result.error(EnumCommonError.Admin_System_Error);
        Task task = taskService.createTaskQuery().taskId(taskId).active().taskAssignee(adminSession.getUser().getId()).singleResult();
        if (task == null) return Result.error(EnumAdminFinanceError.你没有权限处理此任务或者你已经处理过.toString());
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        if (processInstance == null || StringUtils.isEmpty(processInstance.getBusinessKey())) return Result.error(EnumCommonError.Admin_System_Error);
        FinanceOrder financeOrder = orderRepository.findOne(Long.valueOf(processInstance.getBusinessKey()));
        if (financeOrder == null) return Result.error(EnumCommonError.Admin_System_Error);
        if (!financeOrder.getApplyType().equals(EnumFinanceOrderType.MYR.toString())) return Result.error(EnumAdminFinanceError.此订单不是煤易融业务.toString());
        CombineObject<Task, String> map = new CombineObject<>(task, processInstance.getBusinessKey());
        return Result.success().setData(map);
    }


}
