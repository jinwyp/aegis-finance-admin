package com.yimei.finance.service.admin.finance;

import com.yimei.finance.entity.admin.finance.*;
import com.yimei.finance.entity.common.enums.EnumCommonError;
import com.yimei.finance.entity.common.result.Result;
import com.yimei.finance.repository.admin.finance.FinanceOrderRepository;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Service("financeFlowStepService")
public class FinanceFlowStepServiceImpl {
    @Autowired
    private TaskService taskService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private FinanceFlowMethodServiceImpl flowMethodService;
    @Autowired
    private FinanceOrderRepository orderRepository;
    @Autowired
    private FinanceOrderServiceImpl orderService;

    public Result onlineTraderAuditFinanceOrderMethod(String userId, String taskId, int pass, String comment, FinanceOrder financeOrder, AttachmentList attachmentList) {
        if (pass != 0 && pass != 1) return Result.error(EnumCommonError.Admin_System_Error);
        Task task = taskService.createTaskQuery().taskId(taskId).taskAssignee(userId).active().singleResult();
        if (task == null) return Result.error(EnumAdminFinanceError.你没有权限处理此任务或者你已经处理过.toString());
        if (!task.getTaskDefinitionKey().equals(EnumFinanceEventType.onlineTraderAudit.toString()))
            return Result.error(EnumAdminFinanceError.此任务不能进行交易员审核操作.toString());
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).active().singleResult();
        if (processInstance == null) return Result.error(EnumAdminFinanceError.此流程不存在或已经结束.toString());
        if (StringUtils.isEmpty(processInstance.getBusinessKey()) || orderRepository.findOne(Long.valueOf(processInstance.getBusinessKey())) == null)
            return Result.error(EnumCommonError.Admin_System_Error);
        financeOrder.setId(Long.valueOf(processInstance.getBusinessKey()));
        orderService.updateFinanceOrderByOnlineTrader(financeOrder);
        flowMethodService.addAttachmentsMethod(attachmentList, taskId, task.getProcessInstanceId());
        flowMethodService.addComment(task.getId(), task.getProcessInstanceId(), comment, EnumFinanceCommentType.OnlineTraderAuditComment.id);
        Map<String, Object> vars = new HashMap<>();
        vars.put(EnumFinanceEventType.onlineTraderAudit.toString(), pass);
        taskService.complete(taskId, vars);
        if (pass == 1) {
            return Result.success();
        } else {
            return flowMethodService.updateFinanceOrderApproveState(Long.valueOf(processInstance.getBusinessKey()), EnumFinanceStatus.AuditNotPass);
        }
    }

    public Result salesmanAutidFinanceOrderMethod(String userId, String taskId, int pass, String comment, AttachmentList attachmentList) {
        if (pass != 0 && pass != 1) return Result.error(EnumCommonError.Admin_System_Error);
        Task task = taskService.createTaskQuery().taskId(taskId).taskAssignee(userId).active().singleResult();
        if (task == null) return Result.error(EnumAdminFinanceError.你没有权限处理此任务或者你已经处理过.toString());
        if (!task.getTaskDefinitionKey().equals(EnumFinanceEventType.salesmanAudit.toString())) return Result.error(EnumAdminFinanceError.此任务不能进行业务员审核操作.toString());
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).active().singleResult();
        if (processInstance == null) return Result.error(EnumAdminFinanceError.此流程不存在或已经结束.toString());
        flowMethodService.addAttachmentsMethod(attachmentList, taskId, task.getProcessInstanceId());
        flowMethodService.addComment(task.getId(), task.getProcessInstanceId(), comment, EnumFinanceCommentType.SalesmanAuditComment.id);
        Map<String, Object> vars = new HashMap<>();
        vars.put(EnumFinanceConditions.salesmanAudit.toString(), pass);
        taskService.complete(taskId, vars);
        if (pass == 1) {
            return Result.success();
        } else {
            return flowMethodService.updateFinanceOrderApproveState(Long.valueOf(processInstance.getBusinessKey()), EnumFinanceStatus.AuditNotPass);
        }
    }

    public Result salesmanSupplyInvestigationMaterialFinanceOrderMethod(String userId, String taskId, String comment, AttachmentList attachmentList) {
        Task task = taskService.createTaskQuery().taskId(taskId).taskAssignee(userId).active().singleResult();
        if (task == null) return Result.error(EnumAdminFinanceError.你没有权限处理此任务或者你已经处理过.toString());
        if (!task.getTaskDefinitionKey().equals(EnumFinanceEventType.salesmanSupplyInvestigationMaterial.toString())) return Result.error(EnumAdminFinanceError.此任务不能进行业务员补充尽调员材料操作.toString());
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).active().singleResult();
        if (processInstance == null) return Result.error(EnumAdminFinanceError.此流程不存在或已经结束.toString());
        flowMethodService.addAttachmentsMethod(attachmentList, taskId, task.getProcessInstanceId());
        flowMethodService.addComment(task.getId(), task.getProcessInstanceId(), comment, EnumFinanceCommentType.SalesmanSupplyInvestigationMaterialComment.id);
        taskService.complete(taskId);
        Result result = flowMethodService.getLastCompleteTaskUserId(task.getProcessInstanceId(), EnumFinanceEventType.investigatorAudit.toString());
        if (!result.isSuccess()) return result;
        Result result1 = flowMethodService.updateFinanceOrderApproveState(Long.valueOf(processInstance.getBusinessKey()), EnumFinanceStatus.Auditing);
        if (!result1.isSuccess()) return result1;
        return flowMethodService.setAssignUserMethod(task.getProcessInstanceId(), EnumFinanceEventType.investigatorAudit.toString(), String.valueOf(result.getData()));
    }

    public Result investigatorAuditFinanceOrderMethod(String userId, String taskId, int need, int pass, String comment, AttachmentList attachmentList) {
        if (need != 0 && need != 1 && pass != 0 && pass != 1) return Result.error(EnumCommonError.Admin_System_Error);
        Task task = taskService.createTaskQuery().taskId(taskId).taskAssignee(userId).active().singleResult();
        if (task == null) return Result.error(EnumAdminFinanceError.你没有权限处理此任务或者你已经处理过.toString());
        if (!task.getTaskDefinitionKey().equals(EnumFinanceEventType.investigatorAudit.toString())) return Result.error(EnumAdminFinanceError.此任务不能进行尽调员审核操作.toString());
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).active().singleResult();
        if (processInstance == null) return Result.error(EnumAdminFinanceError.此流程不存在或已经结束.toString());
        flowMethodService.addAttachmentsMethod(attachmentList, taskId, task.getProcessInstanceId());
        flowMethodService.addComment(task.getId(), task.getProcessInstanceId(), comment, EnumFinanceCommentType.InvestigatorAuditComment.id);
        Map<String, Object> vars = new HashMap<>();
        vars.put(EnumFinanceConditions.needSalesmanSupplyInvestigationMaterial.toString(), need);
        if (need == 0) {
            vars.put(EnumFinanceConditions.investigatorAudit.toString(), pass);
        }
        taskService.complete(taskId, vars);
        if (need == 1) {
            Result result = flowMethodService.getLastCompleteTaskUserId(task.getProcessInstanceId(), EnumFinanceEventType.salesmanAudit.toString());
            if (!result.isSuccess()) return result;
            Result result1 = flowMethodService.updateFinanceOrderApproveState(Long.valueOf(processInstance.getBusinessKey()), EnumFinanceStatus.SupplyMaterial);
            if (!result1.isSuccess()) return result1;
            return flowMethodService.setAssignUserMethod(task.getProcessInstanceId(), EnumFinanceEventType.salesmanSupplyInvestigationMaterial.toString(), String.valueOf(result.getData()));
        } else if (pass == 1) {
            return Result.success();
        } else {
            return flowMethodService.updateFinanceOrderApproveState(Long.valueOf(processInstance.getBusinessKey()), EnumFinanceStatus.AuditNotPass);
        }
    }

    public Result salesmanSupplySupervisionMaterialFinanceOrderMethod(String userId, String taskId, String comment, AttachmentList attachmentList) {
        Task task = taskService.createTaskQuery().taskId(taskId).taskAssignee(userId).active().singleResult();
        if (task == null) return Result.error(EnumAdminFinanceError.你没有权限处理此任务或者你已经处理过.toString());
        if (!task.getTaskDefinitionKey().equals(EnumFinanceEventType.salesmanSupplySupervisionMaterial.toString())) return Result.error(EnumAdminFinanceError.此任务不能进行业务员补充监管员材料操作.toString());
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).active().singleResult();
        if (processInstance == null) return Result.error(EnumAdminFinanceError.此流程不存在或已经结束.toString());
        flowMethodService.addAttachmentsMethod(attachmentList, taskId, task.getProcessInstanceId());
        flowMethodService.addComment(task.getId(), task.getProcessInstanceId(), comment, EnumFinanceCommentType.SalesmanSupplySupervisionMaterialComment.id);
        taskService.complete(taskId);
        Result result = flowMethodService.getLastCompleteTaskUserId(task.getProcessInstanceId(), EnumFinanceEventType.supervisorAudit.toString());
        if (!result.isSuccess()) return result;
        Result result1 = flowMethodService.updateFinanceOrderApproveState(Long.valueOf(processInstance.getBusinessKey()), EnumFinanceStatus.Auditing);
        if (!result1.isSuccess()) return result1;
        return flowMethodService.setAssignUserMethod(task.getProcessInstanceId(), EnumFinanceEventType.supervisorAudit.toString(), String.valueOf(result.getData()));
    }

    public Result supervisorAuditFinanceOrderMethod(String userId, String taskId, int need, int pass, String comment, AttachmentList attachmentList) {
        if (need != 0 && need != 1 && pass != 0 && pass != 1) return Result.error(EnumCommonError.Admin_System_Error);
        Task task = taskService.createTaskQuery().taskId(taskId).taskAssignee(userId).active().singleResult();
        if (task == null) return Result.error(EnumAdminFinanceError.你没有权限处理此任务或者你已经处理过.toString());
        if (!task.getTaskDefinitionKey().equals(EnumFinanceEventType.supervisorAudit.toString())) return Result.error(EnumAdminFinanceError.此任务不能进行监管员审核操作.toString());
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).active().singleResult();
        if (processInstance == null) return Result.error(EnumAdminFinanceError.此流程不存在或已经结束.toString());
        flowMethodService.addAttachmentsMethod(attachmentList, taskId, task.getProcessInstanceId());
        flowMethodService.addComment(task.getId(), task.getProcessInstanceId(), comment, EnumFinanceCommentType.SupervisorAuditComment.id);
        Map<String, Object> vars = new HashMap<>();
        vars.put(EnumFinanceConditions.needSalesmanSupplySupervisionMaterial.toString(), need);
        if (need == 0) {
            vars.put(EnumFinanceConditions.supervisorAudit.toString(), pass);
        }
        taskService.complete(taskId, vars);
        if (need == 1) {
            Result result = flowMethodService.getLastCompleteTaskUserId(task.getProcessInstanceId(), EnumFinanceEventType.salesmanAudit.toString());
            if (!result.isSuccess()) return result;
            Result result1 = flowMethodService.updateFinanceOrderApproveState(Long.valueOf(processInstance.getBusinessKey()), EnumFinanceStatus.SupplyMaterial);
            if (!result1.isSuccess()) return result1;
            return flowMethodService.setAssignUserMethod(task.getProcessInstanceId(), EnumFinanceEventType.salesmanSupplySupervisionMaterial.toString(), String.valueOf(result.getData()));
        } else if (pass == 1) {
            return Result.success();
        } else {
            return flowMethodService.updateFinanceOrderApproveState(Long.valueOf(processInstance.getBusinessKey()), EnumFinanceStatus.AuditNotPass);
        }
    }

    public Result investigatorSupplyRiskMaterialFinanceOrderMethod(String userId, String taskId, String comment, AttachmentList attachmentList) {
        Task task = taskService.createTaskQuery().taskId(taskId).taskAssignee(userId).active().singleResult();
        if (task == null) return Result.error(EnumAdminFinanceError.你没有权限处理此任务或者你已经处理过.toString());
        if (!task.getTaskDefinitionKey().equals(EnumFinanceEventType.investigatorSupplyRiskMaterial.toString())) return Result.error(EnumAdminFinanceError.此任务不能进行尽调员补充风控人员要求的材料操作.toString());
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).active().singleResult();
        if (processInstance == null) return Result.error(EnumAdminFinanceError.此流程不存在或已经结束.toString());
        flowMethodService.addAttachmentsMethod(attachmentList, taskId, task.getProcessInstanceId());
        flowMethodService.addComment(task.getId(), task.getProcessInstanceId(), comment, EnumFinanceCommentType.InvestigatorSupplyRiskMaterial.id);
        taskService.complete(taskId);
        Result result = flowMethodService.getLastCompleteTaskUserId(task.getProcessInstanceId(), EnumFinanceEventType.riskManagerAudit.toString());
        if (!result.isSuccess()) return result;
        Result result1 = flowMethodService.updateFinanceOrderApproveState(Long.valueOf(processInstance.getBusinessKey()), EnumFinanceStatus.Auditing);
        if (!result1.isSuccess()) return result1;
        return flowMethodService.setAssignUserMethod(task.getProcessInstanceId(), EnumFinanceEventType.riskManagerAudit.toString(), String.valueOf(result.getData()));
    }

    public Result supervisorSupplyRiskMaterialFinanceOrderMethod(String userId, String taskId, String comment, AttachmentList attachmentList) {
        Task task = taskService.createTaskQuery().taskId(taskId).taskAssignee(userId).active().singleResult();
        if (task == null) return Result.error(EnumAdminFinanceError.你没有权限处理此任务或者你已经处理过.toString());
        if (!task.getTaskDefinitionKey().equals(EnumFinanceEventType.supervisorSupplyRiskMaterial.toString())) return Result.error(EnumAdminFinanceError.此任务不能进行监管员补充风控人员要求的材料操作.toString());
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).active().singleResult();
        if (processInstance == null) return Result.error(EnumAdminFinanceError.此流程不存在或已经结束.toString());
        flowMethodService.addAttachmentsMethod(attachmentList, taskId, task.getProcessInstanceId());
        flowMethodService.addComment(task.getId(), task.getProcessInstanceId(), comment, EnumFinanceCommentType.SupervisorSupplyRiskMaterial.id);
        taskService.complete(taskId);
        Result result = flowMethodService.getLastCompleteTaskUserId(task.getProcessInstanceId(), EnumFinanceEventType.riskManagerAudit.toString());
        if (!result.isSuccess()) return result;
        Result result1 = flowMethodService.updateFinanceOrderApproveState(Long.valueOf(processInstance.getBusinessKey()), EnumFinanceStatus.Auditing);
        if (!result1.isSuccess()) return result1;
        return flowMethodService.setAssignUserMethod(task.getProcessInstanceId(), EnumFinanceEventType.riskManagerAudit.toString(), String.valueOf(result.getData()));
    }

    public Result riskManagerAuditMYRFinanceOrderMethod(String userId, String taskId, int need, int pass, String comment, AttachmentList attachmentList) {
        if (need != 0 && need != 1 && pass != 0 && pass != 1) return Result.error(EnumCommonError.Admin_System_Error);
        Task task = taskService.createTaskQuery().taskId(taskId).taskAssignee(userId).active().singleResult();
        if (task == null) return Result.error(EnumAdminFinanceError.你没有权限处理此任务或者你已经处理过.toString());
        if (!task.getTaskDefinitionKey().equals(EnumFinanceEventType.riskManagerAudit.toString())) return Result.error(EnumAdminFinanceError.此任务不能进行风控人员审核操作.toString());
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).active().singleResult();
        if (processInstance == null) return Result.error(EnumAdminFinanceError.此流程不存在或已经结束.toString());
        flowMethodService.addAttachmentsMethod(attachmentList, taskId, task.getProcessInstanceId());
        flowMethodService.addComment(task.getId(), task.getProcessInstanceId(), comment, EnumFinanceCommentType.RiskManagerAuditComment.id);
        Map<String, Object> vars = new HashMap<>();
        vars.put(EnumFinanceConditions.needInvestigatorSupplyRiskMaterial.toString(), need);
        vars.put(EnumFinanceConditions.riskManagerAudit.toString(), pass);
        taskService.complete(taskId, vars);
        if (need == 1) {
            Result result = flowMethodService.getLastCompleteTaskUserId(task.getProcessInstanceId(), EnumFinanceEventType.investigatorAudit.toString());
            if (!result.isSuccess()) return result;
            Result result1 = flowMethodService.updateFinanceOrderApproveState(Long.valueOf(processInstance.getBusinessKey()), EnumFinanceStatus.SupplyMaterial);
            if (!result1.isSuccess()) return result1;
            return flowMethodService.setAssignUserMethod(task.getProcessInstanceId(), EnumFinanceEventType.investigatorSupplyRiskMaterial.toString(), String.valueOf(result.getData()));
        } else if (pass == 1) {
            return flowMethodService.setAssignUserMethod(task.getProcessInstanceId(), EnumFinanceEventType.riskManagerAuditSuccess.toString(), userId);
        } else {
            return flowMethodService.updateFinanceOrderApproveState(Long.valueOf(processInstance.getBusinessKey()), EnumFinanceStatus.AuditNotPass);
        }
    }

    public Result riskManagerAddContractFinanceOrderMethod(String userId, String taskId, String comment, AttachmentList attachmentList) {
        Task task = taskService.createTaskQuery().taskId(taskId).taskAssignee(userId).active().singleResult();
        if (task == null) return Result.error(EnumAdminFinanceError.你没有权限处理此任务或者你已经处理过.toString());
        if (!task.getTaskDefinitionKey().equals(EnumFinanceEventType.riskManagerAuditSuccess.toString())) return Result.error(EnumAdminFinanceError.此任务不能进行填写合同操作.toString());
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).active().singleResult();
        if (processInstance == null) return Result.error(EnumAdminFinanceError.此流程不存在或已经结束.toString());
        flowMethodService.addAttachmentsMethod(attachmentList, taskId, task.getProcessInstanceId());
        flowMethodService.addComment(task.getId(), task.getProcessInstanceId(), comment, EnumFinanceCommentType.RiskManagerContractComment.id);
        taskService.complete(task.getId());
        return flowMethodService.updateFinanceOrderApproveState(Long.valueOf(processInstance.getBusinessKey()), EnumFinanceStatus.AuditPass);
    }



}
