package com.yimei.finance.controllers.admin.restfulapi.finance;

import com.yimei.finance.config.session.AdminSession;
import com.yimei.finance.entity.admin.finance.*;
import com.yimei.finance.entity.admin.user.EnumSpecialGroup;
import com.yimei.finance.entity.common.enums.EnumCommonError;
import com.yimei.finance.entity.common.result.Result;
import com.yimei.finance.repository.admin.finance.FinanceOrderRepository;
import com.yimei.finance.service.admin.user.FinanceOrderServiceImpl;
import io.swagger.annotations.*;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLinkType;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "admin-api-flow-myr", description = "煤易融相关接口")
@RequestMapping("/api/financing/admin/myr")
@RestController("adminMYRFinancingController")
public class MYRFinancingController {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private AdminSession adminSession;
    @Autowired
    private FinanceOrderRepository financeOrderRepository;
    @Autowired
    private FinanceOrderServiceImpl financeOrderService;

    @RequestMapping(value = "/onlinetrader/material/{taskId}", method = RequestMethod.POST)
    @ApiOperation(value = "线上交易员审核并填写材料", notes = "线上交易员审核并填写材料", response = Boolean.class)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path"),
        @ApiImplicitParam(name = "pass", value = "是否审核通过, 0:审核不通过,1:审核通过", required = true, dataType = "Integer", paramType = "query")
    })
    public Result myrOnlineTraderAddMaterialMethod(@PathVariable("taskId")String taskId,
                                                   @RequestParam(value = "pass", required = true) int pass,
                                                   @ApiParam(name = "financeOrder", value = "金融申请单对象", required = true) FinanceOrder financeOrder,
                                                   @ApiParam(name = "attachmentList", value = "金融申请单上传单据列表", required = false)@RequestBody AttachmentList attachmentList) {
        if (pass != 0 && pass != 1) return Result.error(EnumCommonError.Admin_System_Error);
        Task task = taskService.createTaskQuery().taskId(taskId).taskAssignee(adminSession.getUser().getId()).active().singleResult();
        if (task == null) return Result.error(EnumAdminFinanceError.你没有权限处理此任务或者你已经处理过.toString());
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).active().singleResult();
        if (processInstance == null) return Result.error(EnumAdminFinanceError.此流程不存在或已经结束.toString());
        if (StringUtils.isEmpty(processInstance.getBusinessKey()) || financeOrderRepository.findOne(Long.valueOf(processInstance.getBusinessKey())) == null) return Result.error(EnumCommonError.Admin_System_Error);
        financeOrder.setId(Long.valueOf(processInstance.getBusinessKey()));
        financeOrderService.updateFinanceOrder(financeOrder);
        addAttachmentsMethod(attachmentList, taskId, task.getProcessInstanceId());
        Map<String, Object> vars = new HashMap<>();
        vars.put(EnumFinanceEventType.onlineTraderAudit.toString(), pass);
        taskService.complete(taskId, vars);
        return addGroupIdentityLinkMethod(task.getProcessInstanceId(), EnumFinanceAssignType.assignSalesman.toString(), EnumSpecialGroup.ManageSalesmanGroup.id);
    }

    @RequestMapping(value = "/salesman/audit/{taskId}", method = RequestMethod.POST)
    @ApiOperation(value = "业务员填写材料并审核", notes = "业务员填写材料并审核")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "pass", value = "审核结果: 0:审核不通过, 1:审核通过", required = true, dataType = "Integer", paramType = "query")
    })
    public Result myrSalesmanAddMaterialAndAuditMethod(@PathVariable("taskId")String taskId,
                                                       @RequestParam(value = "pass", required = true) int pass,
                                                       @ApiParam(name = "attachmentList", value = "业务员上传资料文件", required = false)@RequestBody AttachmentList attachmentList) {
        if (pass != 0 && pass != 1) return Result.error(EnumCommonError.Admin_System_Error);
        Task task = taskService.createTaskQuery().taskId(taskId).taskAssignee(adminSession.getUser().getId()).active().singleResult();
        if (task == null) return Result.error(EnumAdminFinanceError.你没有权限处理此任务或者你已经处理过.toString());
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).active().singleResult();
        if (processInstance == null) return Result.error(EnumAdminFinanceError.此流程不存在或已经结束.toString());
        addAttachmentsMethod(attachmentList, taskId, task.getProcessInstanceId());
        Map<String, Object> vars = new HashMap<>();
        vars.put(EnumFinanceConditions.salesmanAudit.toString(), pass);
        taskService.complete(taskId, vars);
        if (pass == 1) {
            return addGroupIdentityLinkMethod(task.getProcessInstanceId(), EnumFinanceAssignType.assignInvestigator.toString(), EnumSpecialGroup.ManageInvestigatorGroup.id);
        } else {
            return auditNotPassMethod(Long.valueOf(processInstance.getBusinessKey()));
        }
    }

    @RequestMapping(value = "/salesman/supply/investigation/material/{taskId}", method = RequestMethod.PUT)
    @ApiOperation(value = "业务员补充尽调材料", notes = "业务员补充尽调材料")
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "Integer", paramType = "path")
    public Result myrSalesmanSupplyInvestigationMaterialMethod(@PathVariable("taskId")String taskId,
                                                               @ApiParam(name = "attachmentList", value = "业务员上传资料文件", required = false)@RequestBody AttachmentList attachmentList) {
        Task task = taskService.createTaskQuery().taskId(taskId).taskAssignee(adminSession.getUser().getId()).active().singleResult();
        if (task == null) return Result.error(EnumAdminFinanceError.你没有权限处理此任务或者你已经处理过.toString());
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).active().singleResult();
        if (processInstance == null) return Result.error(EnumAdminFinanceError.此流程不存在或已经结束.toString());
        addAttachmentsMethod(attachmentList, taskId, task.getProcessInstanceId());
        taskService.complete(taskId);
        Task lastTask = taskService.createTaskQuery().taskId(task.getParentTaskId()).singleResult();
        return setAssignUserMethod(task.getProcessInstanceId(), EnumFinanceEventType.investigatorAudit.toString(), lastTask.getAssignee());
    }

    @RequestMapping(value = "/investigator/audit/{taskId}", method = RequestMethod.PUT)
    @ApiOperation(value = "尽调员审核", notes = "尽调员审核")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "金融申请单id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "need", value = "是否需要补充材料: 0:不需要, 1:需要", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pass", value = "审核结果: 0:审核不通过, 1:审核通过", required = true, dataType = "Integer", paramType = "query")
    })
    public Result myrInvestigatorAuditMethod(@PathVariable("taskId")String taskId,
                                             @RequestParam(value = "need", required = true) int need,
                                             @RequestParam(value = "pass", required = true) int pass,
                                             @ApiParam(name = "attachmentList", value = "尽调员上传资料文件", required = false)@RequestBody AttachmentList attachmentList) {
        if (need != 0 && need != 1 && pass != 0 && pass != 1) return Result.error(EnumCommonError.Admin_System_Error);
        Task task = taskService.createTaskQuery().taskId(taskId).taskAssignee(adminSession.getUser().getId()).active().singleResult();
        if (task == null) return Result.error(EnumAdminFinanceError.你没有权限处理此任务或者你已经处理过.toString());
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).active().singleResult();
        if (processInstance == null) return Result.error(EnumAdminFinanceError.此流程不存在或已经结束.toString());
        addAttachmentsMethod(attachmentList, taskId, task.getProcessInstanceId());
        Map<String, Object> vars = new HashMap<>();
        vars.put(EnumFinanceConditions.needSalesmanInvestigationMaterial.toString(), need);
        vars.put(EnumFinanceConditions.investigatorAudit.toString(), pass);
        taskService.complete(taskId, vars);
        if (need == 1) {
            return Result.success();
        } else if (pass == 1) {
            return addGroupIdentityLinkMethod(task.getProcessInstanceId(), EnumFinanceAssignType.assignRiskManager.toString(), EnumSpecialGroup.ManageRiskGroup.id);
        } else {
            return auditNotPassMethod(Long.valueOf(processInstance.getBusinessKey()));
        }
    }

    @RequestMapping(value = "/investigator/supply/riskmanager/material/{taskId}", method = RequestMethod.PUT)
    @ApiOperation(value = "监管员补充材料", notes = "监管员补充风控人员要求的材料")
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "Integer", paramType = "path")
    public Result myrInvestigatorSupplyRiskManagerMaterialMethod(@PathVariable("taskId")String taskId,
                                                                 @ApiParam(name = "attachmentList", value = "业务员上传资料文件", required = false)@RequestBody AttachmentList attachmentList) {
        Task task = taskService.createTaskQuery().taskId(taskId).taskAssignee(adminSession.getUser().getId()).active().singleResult();
        if (task == null) return Result.error(EnumAdminFinanceError.你没有权限处理此任务或者你已经处理过.toString());
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).active().singleResult();
        if (processInstance == null) return Result.error(EnumAdminFinanceError.此流程不存在或已经结束.toString());
        addAttachmentsMethod(attachmentList, taskId, task.getProcessInstanceId());
        taskService.complete(taskId);
        Task lastTask = taskService.createTaskQuery().taskId(task.getParentTaskId()).singleResult();
        return setAssignUserMethod(task.getProcessInstanceId(), EnumFinanceEventType.riskManagerAudit.toString(), lastTask.getAssignee());
    }

    @RequestMapping(value = "/riskmanager/audit/{taskId}", method = RequestMethod.PUT)
    @ApiOperation(value = "风控人员审核", notes = "风控人员审核")
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "Integer", paramType = "path")
    public Result myrRiskManagerAuditMethod(@PathVariable("taskId")String taskId,
                                            @RequestParam(value = "need", required = true) int need,
                                            @RequestParam(value = "pass", required = true) int pass,
                                            @ApiParam(name = "attachmentList", value = "尽调员上传资料文件", required = false)@RequestBody AttachmentList attachmentList) {
        if (need != 0 && need != 1 && pass != 0 && pass != 1) return Result.error(EnumCommonError.Admin_System_Error);
        Task task = taskService.createTaskQuery().taskId(taskId).taskAssignee(adminSession.getUser().getId()).active().singleResult();
        if (task == null) return Result.error(EnumAdminFinanceError.你没有权限处理此任务或者你已经处理过.toString());
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).active().singleResult();
        if (processInstance == null) return Result.error(EnumAdminFinanceError.此流程不存在或已经结束.toString());
        addAttachmentsMethod(attachmentList, taskId, task.getProcessInstanceId());
        Map<String, Object> vars = new HashMap<>();
        vars.put(EnumFinanceConditions.needInvestigatorRiskMaterial.toString(), need);
        vars.put(EnumFinanceConditions.riskManagerAudit.toString(), pass);
        taskService.complete(taskId, vars);
        if (need == 1) {
            return Result.success();
        } else if (pass == 1) {
            return setAssignUserMethod(task.getProcessInstanceId(), EnumFinanceEventType.riskManagerAuditSuccess.toString(), task.getParentTaskId());
        } else {
            return auditNotPassMethod(Long.valueOf(processInstance.getBusinessKey()));
        }
    }

    @RequestMapping(value = "/riskmanager/contract/{taskId}", method = RequestMethod.POST)
    @ApiOperation(value = "风控人员上传合同", notes = "风控人员上传合同,流程完成", response = Boolean.class)
    @ApiImplicitParam(name = "financeId", value = "金融申请单id", required = true, dataType = "Integer", paramType = "path")
    public Result myrRiskManagerUploadContractMethod(@PathVariable("taskId")String taskId,
                                                     @ApiParam(name = "attachmentList", value = "尽调员上传资料文件", required = false)@RequestBody AttachmentList attachmentList) {
        Task task = taskService.createTaskQuery().taskId(taskId).taskAssignee(adminSession.getUser().getId()).active().singleResult();
        if (task == null) return Result.error(EnumAdminFinanceError.你没有权限处理此任务或者你已经处理过.toString());
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).active().singleResult();
        if (processInstance == null) return Result.error(EnumAdminFinanceError.此流程不存在或已经结束.toString());
        addAttachmentsMethod(attachmentList, taskId, task.getProcessInstanceId());
        taskService.complete(task.getId());
        FinanceOrder financeOrder = financeOrderRepository.findOne(Long.valueOf(processInstance.getBusinessKey()));
        if (financeOrder == null) return Result.error(EnumCommonError.Admin_System_Error);
        financeOrder.setApproveState(EnumFinanceStatus.审核通过.toString());
        financeOrderRepository.save(financeOrder);
        return Result.success().setData(true);
    }

    /**
     * 添加附件方法
     */
    void addAttachmentsMethod(AttachmentList attachmentList, String taskId, String processInstanceId) {
        if (attachmentList != null && attachmentList.getAttachmentObjects() != null && attachmentList.getAttachmentObjects().size() != 0) {
            for (AttachmentObject attachmentObject : attachmentList.getAttachmentObjects()) {
                if (!StringUtils.isEmpty(attachmentObject.getName()) && !StringUtils.isEmpty(attachmentObject.getUrl())) {
                    taskService.createAttachment(attachmentObject.getType(), taskId, processInstanceId, attachmentObject.getName(), attachmentObject.getDescription(), attachmentObject.getUrl());
                }
            }
        }
    }

    /**
     * 分配待选组方法
     */
    Result addGroupIdentityLinkMethod(String processInstanceId, String financeAssignType, String groupId) {
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstanceId).active().list();
        for (Task t : taskList) {
            Execution exe = runtimeService.createExecutionQuery().executionId(t.getExecutionId()).singleResult();
            if (exe.getActivityId().equals(financeAssignType)) {
                taskService.addGroupIdentityLink(t.getId(), groupId, IdentityLinkType.CANDIDATE);
                return Result.success().setData(true);
            }
        }
        return Result.error(EnumCommonError.Admin_System_Error);
    }

    /**
     * 指派给人方法
     */
    Result setAssignUserMethod(String processInstanceId, String financeEventType, String userId) {
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstanceId).active().list();
        for (Task t : taskList) {
            Execution exe = runtimeService.createExecutionQuery().executionId(t.getExecutionId()).singleResult();
            if (exe.getActivityId().equals(financeEventType)) {
                taskService.setAssignee(t.getId(), userId);
                return Result.success().setData(true);
            }
        }
        return Result.error(EnumCommonError.Admin_System_Error);
    }

    /**
     * 审核不通过
     */
    Result auditNotPassMethod(Long financeId) {
        FinanceOrder financeOrder = financeOrderRepository.findOne(Long.valueOf(financeId));
        if (financeOrder == null) return Result.error(EnumCommonError.Admin_System_Error);
        financeOrder.setApproveState(EnumFinanceStatus.审核不通过.toString());
        financeOrderRepository.save(financeOrder);
        return Result.success().setData(true);
    }


}
