package com.yimei.finance.service.admin.finance;

import com.yimei.finance.entity.admin.finance.*;
import com.yimei.finance.entity.common.enums.EnumCommonError;
import com.yimei.finance.entity.common.result.Result;
import com.yimei.finance.entity.common.result.TaskMap;
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

    public Result onlineTraderAuditFinanceOrderMethod(String userId, String taskId, TaskMap taskMap, FinanceOrder financeOrder) {
        if (taskMap.getSubmit() != 0 && taskMap.getSubmit() != 1) return Result.error(EnumCommonError.Admin_System_Error);
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
        flowMethodService.addComment(task.getId(), task.getProcessInstanceId(), taskMap.getComment(), EnumFinanceCommentType.OnlineTraderAuditComment.id);
        if (taskMap.getSubmit() == 0) {
            return Result.success();
        } else {
            if (taskMap.getPass() != 0 && taskMap.getPass() != 1) return Result.error(EnumCommonError.Admin_System_Error);
            Map<String, Object> vars = new HashMap<>();
            vars.put(EnumFinanceEventType.onlineTraderAudit.toString(), taskMap.getPass());
            taskService.complete(taskId, vars);
            if (taskMap.getPass() == 1) {
                return Result.success();
            } else {
                return flowMethodService.updateFinanceOrderApproveState(Long.valueOf(processInstance.getBusinessKey()), EnumFinanceStatus.AuditNotPass);
            }
        }
    }

    public Result salesmanAutidFinanceOrderMethod(String userId, String taskId, TaskMap taskMap) {
        if (taskMap.getSubmit() != 0 && taskMap.getSubmit() != 1) return Result.error(EnumCommonError.Admin_System_Error);
        Task task = taskService.createTaskQuery().taskId(taskId).taskAssignee(userId).active().singleResult();
        if (task == null) return Result.error(EnumAdminFinanceError.你没有权限处理此任务或者你已经处理过.toString());
        if (!task.getTaskDefinitionKey().equals(EnumFinanceEventType.salesmanAudit.toString()))
            return Result.error(EnumAdminFinanceError.此任务不能进行业务员审核操作.toString());
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).active().singleResult();
        if (processInstance == null) return Result.error(EnumAdminFinanceError.此流程不存在或已经结束.toString());
        flowMethodService.addComment(task.getId(), task.getProcessInstanceId(), taskMap.getComment(), EnumFinanceCommentType.SalesmanAuditComment.id);
        if (taskMap.getSubmit() == 0) {
            return Result.success();
        } else {
            if (taskMap.getPass() != 0 && taskMap.getPass() != 1) return Result.error(EnumCommonError.Admin_System_Error);
            Map<String, Object> vars = new HashMap<>();
            vars.put(EnumFinanceConditions.salesmanAudit.toString(), taskMap.getPass());
            taskService.complete(taskId, vars);
            if (taskMap.getPass() == 1) {
                return Result.success();
            } else {
                return flowMethodService.updateFinanceOrderApproveState(Long.valueOf(processInstance.getBusinessKey()), EnumFinanceStatus.AuditNotPass);
            }
        }
    }

    public Result salesmanSupplyInvestigationMaterialFinanceOrderMethod(String userId, String taskId, TaskMap taskMap) {
        if (taskMap.getSubmit() != 0 && taskMap.getSubmit() != 1)
            return Result.error(EnumCommonError.Admin_System_Error);
        Task task = taskService.createTaskQuery().taskId(taskId).taskAssignee(userId).active().singleResult();
        if (task == null) return Result.error(EnumAdminFinanceError.你没有权限处理此任务或者你已经处理过.toString());
        if (!task.getTaskDefinitionKey().equals(EnumFinanceEventType.salesmanSupplyInvestigationMaterial.toString()))
            return Result.error(EnumAdminFinanceError.此任务不能进行业务员补充尽调员材料操作.toString());
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).active().singleResult();
        if (processInstance == null) return Result.error(EnumAdminFinanceError.此流程不存在或已经结束.toString());
        flowMethodService.addComment(task.getId(), task.getProcessInstanceId(), taskMap.getComment(), EnumFinanceCommentType.SalesmanSupplyInvestigationMaterialComment.id);
        if (taskMap.getSubmit() == 0) {
            return Result.success();
        } else {
            taskService.complete(taskId);
            Result result1 = flowMethodService.updateFinanceOrderApproveState(Long.valueOf(processInstance.getBusinessKey()), EnumFinanceStatus.Auditing);
            if (!result1.isSuccess()) return result1;
            Result result = flowMethodService.getLastCompleteTaskUserId(task.getProcessInstanceId(), EnumFinanceEventType.investigatorAudit.toString());
            if (!result.isSuccess()) return result;
            return flowMethodService.setAssignUserMethod(task.getProcessInstanceId(), EnumFinanceEventType.investigatorAudit.toString(), String.valueOf(result.getData()));
        }
    }

    public Result investigatorAuditFinanceOrderMethod(String userId, String taskId, TaskMap taskMap) {
        if (taskMap.getSubmit() != 0 && taskMap.getSubmit() != 1) return Result.error(EnumCommonError.Admin_System_Error);
        Task task = taskService.createTaskQuery().taskId(taskId).taskAssignee(userId).active().singleResult();
        if (task == null) return Result.error(EnumAdminFinanceError.你没有权限处理此任务或者你已经处理过.toString());
        if (!task.getTaskDefinitionKey().equals(EnumFinanceEventType.investigatorAudit.toString())) return Result.error(EnumAdminFinanceError.此任务不能进行尽调员审核操作.toString());
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).active().singleResult();
        if (processInstance == null) return Result.error(EnumAdminFinanceError.此流程不存在或已经结束.toString());
        flowMethodService.addComment(task.getId(), task.getProcessInstanceId(), taskMap.getComment(), EnumFinanceCommentType.InvestigatorAuditComment.id);
        if (taskMap.getSubmit() == 0) {
            return Result.success();
        } else {
            if (taskMap.getNeed() != 0 && taskMap.getNeed() != 1 && taskMap.getPass() != 0 && taskMap.getPass() != 1) return Result.error(EnumCommonError.Admin_System_Error);
            Map<String, Object> vars = new HashMap<>();
            vars.put(EnumFinanceConditions.needSalesmanSupplyInvestigationMaterial.toString(), taskMap.getNeed());
            if (taskMap.getNeed() == 0) {
                vars.put(EnumFinanceConditions.investigatorAudit.toString(), taskMap.getPass());
            }
            taskService.complete(taskId, vars);
            if (taskMap.getNeed() == 1) {
                Result result = flowMethodService.getLastCompleteTaskUserId(task.getProcessInstanceId(), EnumFinanceEventType.salesmanAudit.toString());
                if (!result.isSuccess()) return result;
                Result result1 = flowMethodService.updateFinanceOrderApproveState(Long.valueOf(processInstance.getBusinessKey()), EnumFinanceStatus.SupplyMaterial);
                if (!result1.isSuccess()) return result1;
                return flowMethodService.setAssignUserMethod(task.getProcessInstanceId(), EnumFinanceEventType.salesmanSupplyInvestigationMaterial.toString(), String.valueOf(result.getData()));
            } else if (taskMap.getPass() == 1) {
                return Result.success();
            } else {
                return flowMethodService.updateFinanceOrderApproveState(Long.valueOf(processInstance.getBusinessKey()), EnumFinanceStatus.AuditNotPass);
            }
        }
    }

    public Result salesmanSupplySupervisionMaterialFinanceOrderMethod(String userId, String taskId, TaskMap taskMap) {
        if (taskMap.getSubmit() != 0 && taskMap.getSubmit() != 1) return Result.error(EnumCommonError.Admin_System_Error);
        Task task = taskService.createTaskQuery().taskId(taskId).taskAssignee(userId).active().singleResult();
        if (task == null) return Result.error(EnumAdminFinanceError.你没有权限处理此任务或者你已经处理过.toString());
        if (!task.getTaskDefinitionKey().equals(EnumFinanceEventType.salesmanSupplySupervisionMaterial.toString()))
            return Result.error(EnumAdminFinanceError.此任务不能进行业务员补充监管员材料操作.toString());
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).active().singleResult();
        if (processInstance == null) return Result.error(EnumAdminFinanceError.此流程不存在或已经结束.toString());
        flowMethodService.addComment(task.getId(), task.getProcessInstanceId(), taskMap.getComment(), EnumFinanceCommentType.SalesmanSupplySupervisionMaterialComment.id);
        if (taskMap.getSubmit() == 0) {
            return Result.success();
        } else {
            taskService.complete(taskId);
            Result result1 = flowMethodService.updateFinanceOrderApproveState(Long.valueOf(processInstance.getBusinessKey()), EnumFinanceStatus.Auditing);
            if (!result1.isSuccess()) return result1;
            Result result = flowMethodService.getLastCompleteTaskUserId(task.getProcessInstanceId(), EnumFinanceEventType.supervisorAudit.toString());
            if (!result.isSuccess()) return result;
            return flowMethodService.setAssignUserMethod(task.getProcessInstanceId(), EnumFinanceEventType.supervisorAudit.toString(), String.valueOf(result.getData()));
        }
    }

    public Result supervisorAuditFinanceOrderMethod(String userId, String taskId, TaskMap taskMap) {
        if (taskMap.getSubmit() != 0 && taskMap.getSubmit() != 1) return Result.error(EnumCommonError.Admin_System_Error);
        Task task = taskService.createTaskQuery().taskId(taskId).taskAssignee(userId).active().singleResult();
        if (task == null) return Result.error(EnumAdminFinanceError.你没有权限处理此任务或者你已经处理过.toString());
        if (!task.getTaskDefinitionKey().equals(EnumFinanceEventType.supervisorAudit.toString())) return Result.error(EnumAdminFinanceError.此任务不能进行监管员审核操作.toString());
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).active().singleResult();
        if (processInstance == null) return Result.error(EnumAdminFinanceError.此流程不存在或已经结束.toString());
        flowMethodService.addComment(task.getId(), task.getProcessInstanceId(), taskMap.getComment(), EnumFinanceCommentType.SupervisorAuditComment.id);
        if (taskMap.getSubmit() == 0) {
            return Result.success();
        } else {
            if (taskMap.getNeed() != 0 && taskMap.getNeed() != 1 && taskMap.getPass() != 0 && taskMap.getPass() != 1)
                return Result.error(EnumCommonError.Admin_System_Error);
            Map<String, Object> vars = new HashMap<>();
            vars.put(EnumFinanceConditions.needSalesmanSupplySupervisionMaterial.toString(), taskMap.getNeed());
            if (taskMap.getNeed() == 0) {
                vars.put(EnumFinanceConditions.supervisorAudit.toString(), taskMap.getPass());
            }
            taskService.complete(taskId, vars);
            if (taskMap.getNeed() == 1) {
                Result result = flowMethodService.getLastCompleteTaskUserId(task.getProcessInstanceId(), EnumFinanceEventType.salesmanAudit.toString());
                if (!result.isSuccess()) return result;
                Result result1 = flowMethodService.updateFinanceOrderApproveState(Long.valueOf(processInstance.getBusinessKey()), EnumFinanceStatus.SupplyMaterial);
                if (!result1.isSuccess()) return result1;
                return flowMethodService.setAssignUserMethod(task.getProcessInstanceId(), EnumFinanceEventType.salesmanSupplySupervisionMaterial.toString(), String.valueOf(result.getData()));
            } else if (taskMap.getPass() == 1) {
                return Result.success();
            } else {
                return flowMethodService.updateFinanceOrderApproveState(Long.valueOf(processInstance.getBusinessKey()), EnumFinanceStatus.AuditNotPass);
            }
        }
    }

    public Result investigatorSupplyRiskMaterialFinanceOrderMethod(String userId, String taskId, TaskMap taskMap) {
        if (taskMap.getSubmit() != 0 && taskMap.getSubmit() != 1)
            return Result.error(EnumCommonError.Admin_System_Error);
        Task task = taskService.createTaskQuery().taskId(taskId).taskAssignee(userId).active().singleResult();
        if (task == null) return Result.error(EnumAdminFinanceError.你没有权限处理此任务或者你已经处理过.toString());
        if (!task.getTaskDefinitionKey().equals(EnumFinanceEventType.investigatorSupplyRiskMaterial.toString()))
            return Result.error(EnumAdminFinanceError.此任务不能进行尽调员补充风控人员要求的材料操作.toString());
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).active().singleResult();
        if (processInstance == null) return Result.error(EnumAdminFinanceError.此流程不存在或已经结束.toString());
        flowMethodService.addComment(task.getId(), task.getProcessInstanceId(), taskMap.getComment(), EnumFinanceCommentType.InvestigatorSupplyRiskMaterial.id);
        if (taskMap.getSubmit() == 0) {
            return Result.success();
        } else {
            taskService.complete(taskId);
            Result result = flowMethodService.getLastCompleteTaskUserId(task.getProcessInstanceId(), EnumFinanceEventType.riskManagerAudit.toString());
            if (!result.isSuccess()) return result;
            Result result1 = flowMethodService.updateFinanceOrderApproveState(Long.valueOf(processInstance.getBusinessKey()), EnumFinanceStatus.Auditing);
            if (!result1.isSuccess()) return result1;
            return flowMethodService.setAssignUserMethod(task.getProcessInstanceId(), EnumFinanceEventType.riskManagerAudit.toString(), String.valueOf(result.getData()));
        }
    }

    public Result supervisorSupplyRiskMaterialFinanceOrderMethod(String userId, String taskId, TaskMap taskMap) {
        if (taskMap.getSubmit() != 0 && taskMap.getSubmit() != 1)
            return Result.error(EnumCommonError.Admin_System_Error);
        Task task = taskService.createTaskQuery().taskId(taskId).taskAssignee(userId).active().singleResult();
        if (task == null) return Result.error(EnumAdminFinanceError.你没有权限处理此任务或者你已经处理过.toString());
        if (!task.getTaskDefinitionKey().equals(EnumFinanceEventType.supervisorSupplyRiskMaterial.toString()))
            return Result.error(EnumAdminFinanceError.此任务不能进行监管员补充风控人员要求的材料操作.toString());
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).active().singleResult();
        if (processInstance == null) return Result.error(EnumAdminFinanceError.此流程不存在或已经结束.toString());
        flowMethodService.addComment(task.getId(), task.getProcessInstanceId(), taskMap.getComment(), EnumFinanceCommentType.SupervisorSupplyRiskMaterial.id);
        if (taskMap.getSubmit() == 0) {
            return Result.success();
        } else {
            taskService.complete(taskId);
            Result result1 = flowMethodService.updateFinanceOrderApproveState(Long.valueOf(processInstance.getBusinessKey()), EnumFinanceStatus.Auditing);
            if (!result1.isSuccess()) return result1;
            Result result = flowMethodService.getLastCompleteTaskUserId(task.getProcessInstanceId(), EnumFinanceEventType.riskManagerAudit.toString());
            if (!result.isSuccess()) return result;
            return flowMethodService.setAssignUserMethod(task.getProcessInstanceId(), EnumFinanceEventType.riskManagerAudit.toString(), String.valueOf(result.getData()));
        }
    }

    public Result riskManagerAuditMYRFinanceOrderMethod(String userId, String taskId, TaskMap taskMap) {
        if (taskMap.getSubmit() != 0 && taskMap.getSubmit() != 1)
            return Result.error(EnumCommonError.Admin_System_Error);
        Task task = taskService.createTaskQuery().taskId(taskId).taskAssignee(userId).active().singleResult();
        if (task == null) return Result.error(EnumAdminFinanceError.你没有权限处理此任务或者你已经处理过.toString());
        if (!task.getTaskDefinitionKey().equals(EnumFinanceEventType.riskManagerAudit.toString())) return Result.error(EnumAdminFinanceError.此任务不能进行风控人员审核操作.toString());
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).active().singleResult();
        if (processInstance == null) return Result.error(EnumAdminFinanceError.此流程不存在或已经结束.toString());
        flowMethodService.addComment(task.getId(), task.getProcessInstanceId(), taskMap.getComment(), EnumFinanceCommentType.RiskManagerAuditComment.id);
        if (taskMap.getSubmit() == 0) {
            return Result.success();
        } else {
            if (taskMap.getNeed() != 0 && taskMap.getNeed() != 1 && taskMap.getNeed2() != 0 && taskMap.getNeed2() != 1 && taskMap.getPass() != 0 && taskMap.getPass() != 1)
                return Result.error(EnumCommonError.Admin_System_Error);
            Map<String, Object> vars = new HashMap<>();
            vars.put(EnumFinanceConditions.needInvestigatorSupplyRiskMaterial.toString(), taskMap.getNeed());
            vars.put(EnumFinanceConditions.needSupervisorSupplyRiskMaterial.toString(), taskMap.getNeed2());
            if (taskMap.getNeed() == 0 && taskMap.getNeed2() == 0) {
                vars.put(EnumFinanceConditions.riskManagerAudit.toString(), taskMap.getPass());
            }
            taskService.complete(taskId, vars);
            if (taskMap.getNeed() == 1) {
                Result result = flowMethodService.getLastCompleteTaskUserId(task.getProcessInstanceId(), EnumFinanceEventType.investigatorAudit.toString());
                if (!result.isSuccess()) return result;
                Result result1 = flowMethodService.updateFinanceOrderApproveState(Long.valueOf(processInstance.getBusinessKey()), EnumFinanceStatus.SupplyMaterial);
                if (!result1.isSuccess()) return result1;
                return flowMethodService.setAssignUserMethod(task.getProcessInstanceId(), EnumFinanceEventType.investigatorSupplyRiskMaterial.toString(), String.valueOf(result.getData()));
            } else if (taskMap.getNeed() == 1) {
                return flowMethodService.setAssignUserMethod(task.getProcessInstanceId(), EnumFinanceEventType.riskManagerAuditSuccess.toString(), userId);
            } else {
                return flowMethodService.updateFinanceOrderApproveState(Long.valueOf(processInstance.getBusinessKey()), EnumFinanceStatus.AuditNotPass);
            }
        }
    }

    public Result riskManagerAddContractFinanceOrderMethod(String userId, String taskId, TaskMap taskMap) {
        if (taskMap.getSubmit() != 0 && taskMap.getSubmit() != 1) return Result.error(EnumCommonError.Admin_System_Error);
        Task task = taskService.createTaskQuery().taskId(taskId).taskAssignee(userId).active().singleResult();
        if (task == null) return Result.error(EnumAdminFinanceError.你没有权限处理此任务或者你已经处理过.toString());
        if (!task.getTaskDefinitionKey().equals(EnumFinanceEventType.riskManagerAuditSuccess.toString())) return Result.error(EnumAdminFinanceError.此任务不能进行填写合同操作.toString());
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).active().singleResult();
        if (processInstance == null) return Result.error(EnumAdminFinanceError.此流程不存在或已经结束.toString());
        flowMethodService.addComment(task.getId(), task.getProcessInstanceId(), taskMap.getComment(), EnumFinanceCommentType.RiskManagerContractComment.id);
        if (taskMap.getSubmit() == 0) {
            return Result.success();
        } else {
            taskService.complete(task.getId());
            return flowMethodService.updateFinanceOrderApproveState(Long.valueOf(processInstance.getBusinessKey()), EnumFinanceStatus.AuditPass);
        }
    }



}
