package com.yimei.finance.controllers.admin.restfulapi.finance;

import com.yimei.finance.config.session.AdminSession;
import com.yimei.finance.entity.admin.finance.FinanceOrder;
import com.yimei.finance.repository.admin.finance.FinanceOrderRepository;
import com.yimei.finance.representation.admin.finance.enums.EnumAdminFinanceError;
import com.yimei.finance.representation.admin.finance.enums.EnumFinanceOrderType;
import com.yimei.finance.representation.admin.finance.object.*;
import com.yimei.finance.representation.admin.finance.object.validated.*;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @RequestMapping(value = "/onlinetrader/audit/{taskId}", method = RequestMethod.POST, params = {"type=0"})
    @ApiOperation(value = "线上交易员填写材料-保存", notes = "线上交易员填写材料-保存", response = Boolean.class)
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path")
    public Result myrOnlineTraderAddMaterialMethod(@PathVariable("taskId") String taskId,
                                                   @ApiParam(name = "map", value = "参数body对象", required = true) @Validated(value = {SaveFinanceOrder.class}) @RequestBody FinanceOrderObject financeOrder) {
        return onlineTraderAddMaterialMethod(taskId, null, financeOrder, false);
    }

    @RequestMapping(value = "/onlinetrader/audit/{taskId}", method = RequestMethod.POST, params = {"type=1"})
    @ApiOperation(value = "线上交易员填写材料-提交", notes = "线上交易员填写材料-提交", response = Boolean.class)
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path")
    public Result myrOnlineTraderAddMaterialAndAuditMethod(@PathVariable("taskId") String taskId,
                                                           @ApiParam(name = "map", value = "参数body对象", required = true) @Validated(value = {SubmitFinanceOrder.class}) @RequestBody CombineObject<TaskMap, FinanceOrderObject> map) {
        return onlineTraderAddMaterialMethod(taskId, map.t, map.u, true);
    }

    private Result onlineTraderAddMaterialMethod(String taskId, TaskMap taskMap, FinanceOrderObject financeOrder, boolean submit) {
        Result result = checkMYRMethod(taskId);
        if (!result.isSuccess()) return result;
        CombineObject<Task, Long> object = (CombineObject<Task, Long>) result.getData();
        financeOrder.setId(object.u);
        return flowStepService.onlineTraderAuditFinanceOrderMethod(adminSession.getUser().getId(), taskMap, object.t, financeOrder, submit);
    }

    @RequestMapping(value = "/salesman/audit/{taskId}", method = RequestMethod.POST, params = {"type=0"})
    @ApiOperation(value = "业务员审核-保存", notes = "业务员审核-保存", response = Boolean.class)
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path")
    public Result myrSalesmanAddMaterialMethod(@PathVariable("taskId") String taskId,
                                               @ApiParam(name = "map", value = "参数body对象", required = true) @Validated(value = {SaveFinanceSalesmanInfo.class}) @RequestBody FinanceOrderSalesmanInfoObject salesmanInfoObject) {
        return salesmanAddMaterialAndAuditMethod(taskId, null, salesmanInfoObject, false);
    }

    @RequestMapping(value = "/salesman/audit/{taskId}", method = RequestMethod.POST, params = {"type=1"})
    @ApiOperation(value = "业务员审核-提交", notes = "业务员审核-提交", response = Boolean.class)
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path")
    public Result myrSalesmanAddMaterialAndAuditMethod(@PathVariable("taskId") String taskId,
                                                       @ApiParam(name = "map", value = "参数body对象", required = true) @Validated(value = {SubmitFinanceSalesmanInfo.class}) @RequestBody CombineObject<TaskMap, FinanceOrderSalesmanInfoObject> map) {
        return salesmanAddMaterialAndAuditMethod(taskId, map.t, map.u, true);
    }

    private Result salesmanAddMaterialAndAuditMethod(String taskId, TaskMap taskMap, FinanceOrderSalesmanInfoObject salesmanInfoObject, boolean submit) {
        Result result = checkMYRMethod(taskId);
        if (!result.isSuccess()) return result;
        CombineObject<Task, Long> object = (CombineObject<Task, Long>) result.getData();
        return flowStepService.salesmanAuditFinanceOrderMethod(adminSession.getUser().getId(), taskMap, salesmanInfoObject, object.t, object.u, submit);
    }

    @RequestMapping(value = "/salesman/supply/investigation/material/{taskId}", method = RequestMethod.POST)
    @ApiOperation(value = "业务员补充尽调材料", notes = "业务员补充尽调材料", response = Boolean.class)
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path")
    public Result myrSalesmanSupplyInvestigationMaterialMethod(@PathVariable("taskId") String taskId,
                                                               @ApiParam(name = "attachmentList", value = "附件list", required = true) @Validated @RequestBody List<AttachmentObject> attachmentList) {
        Result result = checkMYRMethod(taskId);
        if (!result.isSuccess()) return result;
        CombineObject<Task, Long> object = (CombineObject<Task, Long>) result.getData();
        return flowStepService.salesmanSupplyInvestigationMaterialFinanceOrderMethod(adminSession.getUser().getId(), attachmentList, object.t, object.u);
    }

    @RequestMapping(value = "/investigator/audit/{taskId}", method = RequestMethod.POST, params = {"type=0"})
    @ApiOperation(value = "尽调员审核-保存", notes = "尽调员审核-保存", response = Boolean.class)
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path")
    public Result myrInvestigatorAddMaterialMethod(@PathVariable("taskId") String taskId,
                                                   @ApiParam(name = "map", value = "任务相关参数", required = true) @Validated(value = {SaveFinanceInvestigatorInfo.class}) @RequestBody FinanceOrderInvestigatorInfoObject investigatorInfoObject) {
        return investigatorAddMaterialAndAuditMethod(taskId, null, investigatorInfoObject, false);
    }

    @RequestMapping(value = "/investigator/audit/{taskId}", method = RequestMethod.POST, params = {"type=1"})
    @ApiOperation(value = "尽调员审核-提交", notes = "尽调员审核-提交", response = Boolean.class)
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path")
    public Result myrInvestigatorAddMaterialAndAuditMethod(@PathVariable("taskId") String taskId,
                                                           @ApiParam(name = "map", value = "任务相关参数", required = true) @Validated(value = {SubmitFinanceInvestigatorInfo.class}) @RequestBody CombineObject<TaskMap, FinanceOrderInvestigatorInfoObject> map) {
        return investigatorAddMaterialAndAuditMethod(taskId, map.t, map.u, true);
    }

    private Result investigatorAddMaterialAndAuditMethod(String taskId, TaskMap taskMap, FinanceOrderInvestigatorInfoObject investigatorInfoObject, boolean submit) {
        Result result = checkMYRMethod(taskId);
        if (!result.isSuccess()) return result;
        CombineObject<Task, Long> object = (CombineObject<Task, Long>) result.getData();
        return flowStepService.investigatorAuditFinanceOrderMethod(adminSession.getUser().getId(), taskMap, investigatorInfoObject, object.t, object.u, submit);
    }

    @RequestMapping(value = "/investigator/supply/riskmanager/material/{taskId}", method = RequestMethod.POST)
    @ApiOperation(value = "尽调员补充风控材料", notes = "尽调员补充风控人员要求的材料")
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path")
    public Result myrInvestigatorSupplyRiskManagerMaterialMethod(@PathVariable("taskId") String taskId,
                                                                 @ApiParam(name = "attachmentList", value = "附件list", required = true) @Validated @RequestBody List<AttachmentObject> attachmentList) {
        Result result = checkMYRMethod(taskId);
        if (!result.isSuccess()) return result;
        CombineObject<Task, Long> object = (CombineObject<Task, Long>) result.getData();
        return flowStepService.investigatorSupplyRiskMaterialFinanceOrderMethod(adminSession.getUser().getId(), attachmentList, object.t, object.u);
    }

    @RequestMapping(value = "/riskmanager/audit/{taskId}", method = RequestMethod.POST, params = {"type=0"})
    @ApiOperation(value = "风控人员审核-保存", notes = "风控人员审核-保存")
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "Integer", paramType = "path")
    public Result myrRiskManagerAddMaterialMethod(@PathVariable("taskId") String taskId,
                                                  @ApiParam(name = "map", value = "任务相关参数", required = true) @Validated(value = {SaveFinanceRiskManagerInfo.class}) @RequestBody FinanceOrderRiskManagerInfoObject riskManagerInfoObject) {
        return riskManagerAddMaterialAndAuditMethod(taskId, null, riskManagerInfoObject, false);
    }

    @RequestMapping(value = "/riskmanager/audit/{taskId}", method = RequestMethod.POST, params = {"type=1"})
    @ApiOperation(value = "风控人员审核-提交", notes = "风控人员审核-提交")
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "Integer", paramType = "path")
    public Result myrRiskManagerAddMaterialAndAuditMethod(@PathVariable("taskId") String taskId,
                                                          @ApiParam(name = "map", value = "任务相关参数", required = true) @Validated(value = {SubmitFinanceRiskManagerInfo.class}) @RequestBody CombineObject<TaskMap, FinanceOrderRiskManagerInfoObject> map) {
        return riskManagerAddMaterialAndAuditMethod(taskId, map.t, map.u, true);
    }

    private Result riskManagerAddMaterialAndAuditMethod(String taskId, TaskMap taskMap, FinanceOrderRiskManagerInfoObject riskManagerInfoObject, boolean submit) {
        Result result = checkMYRMethod(taskId);
        if (!result.isSuccess()) return result;
        CombineObject<Task, Long> object = (CombineObject<Task, Long>) result.getData();
        return flowStepService.riskManagerAuditFinanceOrderMethod(adminSession.getUser().getId(), taskMap, riskManagerInfoObject, object.t, object.u, submit);
    }

    private Result checkMYRMethod(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).active().taskAssignee(adminSession.getUser().getId()).singleResult();
        if (task == null) return Result.error(EnumAdminFinanceError.你没有权限处理此任务或者你已经处理过.toString());
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        if (processInstance == null || StringUtils.isEmpty(processInstance.getBusinessKey()))
            return Result.error(EnumCommonError.Admin_System_Error);
        FinanceOrder financeOrder = orderRepository.findOne(Long.valueOf(processInstance.getBusinessKey()));
        if (financeOrder == null) return Result.error(EnumCommonError.Admin_System_Error);
        if (!financeOrder.getApplyType().equals(EnumFinanceOrderType.MYR.toString()))
            return Result.error(EnumAdminFinanceError.此订单不是煤易融业务.toString());
        CombineObject<Task, Long> map = new CombineObject<>(task, Long.valueOf(processInstance.getBusinessKey()));
        return Result.success().setData(map);
    }


}
