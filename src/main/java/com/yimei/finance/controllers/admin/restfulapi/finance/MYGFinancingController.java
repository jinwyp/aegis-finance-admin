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
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "admin-api-flow-myg", description = "煤易购相关接口")
@RequestMapping("/api/financing/admin/myg")
@RestController("adminMYGFinancingController")
public class MYGFinancingController {
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

    @RequestMapping(value = "/onlinetrader/audit/{taskId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "线上交易员审核并填写材料", notes = "线上交易员审核并填写材料", response = Boolean.class)
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path")
    public Result mygOnlineTraderAddMaterialMethod(@PathVariable("taskId") String taskId,
                                                   @ApiParam(name = "map", value = "参数body对象", required = true) @RequestBody CombineObject<TaskMap, FinanceOrder> map) {
        Result result = checkMYGMethod(taskId, map.t);
        if (!result.isSuccess()) return result;
        CombineObject<Task, Long> object = (CombineObject<Task, Long>) result.getData();
        FinanceOrder order = map.u;
        order.setId(object.u);
        return flowStepService.onlineTraderAuditFinanceOrderMethod(adminSession.getUser().getId(), map.t, object.getT(), order);
    }

    @RequestMapping(value = "/salesman/audit/{taskId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "业务员审核并填写材料", notes = "业务员审核并填写材料", response = Boolean.class)
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path")
    public Result mygSalesmanAddMaterialAndAuditMethod(@PathVariable("taskId") String taskId,
                                                       @ApiParam(name = "taskMap", value = "任务相关参数", required = true) @RequestBody CombineObject<TaskMap, FinanceOrderSalesmanInfo> map) {
        Result result = checkMYGMethod(taskId, map.t);
        if (!result.isSuccess()) return result;
        CombineObject<Task, Long> object = (CombineObject<Task, Long>) result.getData();
        return flowStepService.salesmanAuditFinanceOrderMethod(adminSession.getUser().getId(), map.t, map.u, object.t, object.u);
    }

    @RequestMapping(value = "/salesman/supply/investigation/material/{taskId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "业务员补充尽调材料", notes = "业务员补充尽调材料", response = Boolean.class)
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path")
    public Result mygSalesmanSupplyInvestigationMaterialMethod(@PathVariable("taskId") String taskId,
                                                               @ApiParam(name = "attachmentList", value = "附件list", required = true) @RequestBody List<AttachmentObject> attachmentList) {
        Result result = checkMYGMethod(taskId);
        if (!result.isSuccess()) return result;
        CombineObject<Task, Long> object = (CombineObject<Task, Long>) result.getData();
        return flowStepService.salesmanSupplyInvestigationMaterialFinanceOrderMethod(adminSession.getUser().getId(), attachmentList, object.t, object.u);
    }

    @RequestMapping(value = "/investigator/audit/{taskId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "尽调员审核", notes = "尽调员审核", response = Boolean.class)
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path")
    public Result mygInvestigatorAuditMethod(@PathVariable("taskId") String taskId,
                                             @ApiParam(name = "map", value = "任务相关参数", required = true) @RequestBody CombineObject<TaskMap, FinanceOrderInvestigatorInfo> map) {
        Result result = checkMYGMethod(taskId, map.t);
        if (!result.isSuccess()) return result;
        CombineObject<Task, Long> object = (CombineObject<Task, Long>) result.getData();
        return flowStepService.investigatorAuditFinanceOrderMethod(adminSession.getUser().getId(), map.t, map.u, object.t, object.u);
    }

    @RequestMapping(value = "/salesman/supply/supervision/material/{taskId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "业务员补充监管材料", notes = "业务员补充监管材料")
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path")
    public Result mygSalesmanSupplySupervisionMaterialMethod(@PathVariable("taskId") String taskId,
                                                             @ApiParam(name = "attachmentList", value = "附件list", required = true) @RequestBody List<AttachmentObject> attachmentList) {
        Result result = checkMYGMethod(taskId);
        if (!result.isSuccess()) return result;
        CombineObject<Task, Long> object = (CombineObject<Task, Long>) result.getData();
        return flowStepService.salesmanSupplySupervisionMaterialFinanceOrderMethod(adminSession.getUser().getId(), attachmentList, object.t, object.u);
    }

    @RequestMapping(value = "/supervisor/audit/{taskId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "监管员审核", notes = "监管员审核", response = Boolean.class)
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path")
    public Result mygSupervisorAuditMethod(@PathVariable("taskId") String taskId,
                                           @ApiParam(name = "map", value = "任务相关参数", required = true) @RequestBody CombineObject<TaskMap, FinanceOrderSupervisorInfo> map) {
        Result result = checkMYGMethod(taskId, map.t);
        if (!result.isSuccess()) return result;
        CombineObject<Task, Long> object = (CombineObject<Task, Long>) result.getData();
        return flowStepService.supervisorAuditFinanceOrderMethod(adminSession.getUser().getId(), map.t, map.u, object.t, object.u);
    }

    @RequestMapping(value = "/investigator/supply/riskmanager/material/{taskId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "尽调员补充风控材料", notes = "尽调员补充风控人员要求的材料")
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path")
    public Result mygInvestigatorSupplyRiskManagerMaterialMethod(@PathVariable("taskId") String taskId,
                                                                 @ApiParam(name = "attachmentList", value = "附件list", required = true) @RequestBody List<AttachmentObject> attachmentList) {
        Result result = checkMYGMethod(taskId);
        if (!result.isSuccess()) return result;
        CombineObject<Task, Long> object = (CombineObject<Task, Long>) result.getData();
        return flowStepService.investigatorSupplyRiskMaterialFinanceOrderMethod(adminSession.getUser().getId(), attachmentList, object.t, object.u);
    }

    @RequestMapping(value = "/supervisor/supply/riskmanager/material/{taskId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "监管员补充风控材料", notes = "监管员补充风控人员要求的材料")
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "Integer", paramType = "path")
    public Result mygSupervisorSupplyRiskManagerMaterialMethod(@PathVariable("taskId") String taskId,
                                                               @ApiParam(name = "attachmentList", value = "附件list", required = true) @RequestBody List<AttachmentObject> attachmentList) {
        Result result = checkMYGMethod(taskId);
        if (!result.isSuccess()) return result;
        CombineObject<Task, Long> object = (CombineObject<Task, Long>) result.getData();
        return flowStepService.supervisorSupplyRiskMaterialFinanceOrderMethod(adminSession.getUser().getId(), attachmentList, object.t, object.u);
    }

    @RequestMapping(value = "/riskmanager/audit/{taskId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "风控人员审核", notes = "风控人员审核")
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "Integer", paramType = "path")
    public Result mygRiskManagerAuditMethod(@PathVariable("taskId") String taskId,
                                            @ApiParam(name = "taskMap", value = "任务相关参数", required = true) @RequestBody CombineObject<TaskMap, FinanceOrderRiskManagerInfo> map) {
        Result result = checkMYGMethod(taskId, map.t);
        if (!result.isSuccess()) return result;
        CombineObject<Task, Long> object = (CombineObject<Task, Long>) result.getData();
        return flowStepService.riskManagerAuditFinanceOrderMethod(adminSession.getUser().getId(), map.t, map.u, object.t, object.u);
    }

    private Result checkMYGMethod(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).active().taskAssignee(adminSession.getUser().getId()).singleResult();
        if (task == null) return Result.error(EnumAdminFinanceError.你没有权限处理此任务或者你已经处理过.toString());
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        if (processInstance == null || StringUtils.isEmpty(processInstance.getBusinessKey())) return Result.error(EnumCommonError.Admin_System_Error);
        FinanceOrder financeOrder = orderRepository.findOne(Long.valueOf(processInstance.getBusinessKey()));
        if (financeOrder == null) return Result.error(EnumCommonError.Admin_System_Error);
        if (!financeOrder.getApplyType().equals(EnumFinanceOrderType.MYG.toString())) return Result.error(EnumAdminFinanceError.此订单不是煤易融业务.toString());
        CombineObject<Task, Long> map = new CombineObject<>(task, Long.valueOf(processInstance.getBusinessKey()));
        return Result.success().setData(map);
    }

    private Result checkMYGMethod(String taskId, TaskMap taskMap) {
        if (taskMap.submit != 0 && taskMap.submit != 1) return Result.error(EnumCommonError.Admin_System_Error);
        return checkMYGMethod(taskId);
    }


}
