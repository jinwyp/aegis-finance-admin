package com.yimei.finance.service.admin.finance;

import com.yimei.finance.entity.admin.finance.*;
import com.yimei.finance.entity.admin.user.UserObject;
import com.yimei.finance.repository.admin.finance.FinanceOrderRepository;
import com.yimei.finance.representation.admin.finance.*;
import com.yimei.finance.representation.common.enums.EnumCommonError;
import com.yimei.finance.representation.common.result.Result;
import com.yimei.finance.representation.common.result.TaskMap;
import com.yimei.finance.service.admin.user.AdminUserServiceImpl;
import com.yimei.finance.service.common.message.MessageServiceImpl;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("financeFlowStepService")
public class FinanceFlowStepServiceImpl {
    @Autowired
    private TaskService taskService;
    @Autowired
    private FinanceFlowMethodServiceImpl methodService;
    @Autowired
    private FinanceOrderServiceImpl orderService;
    @Autowired
    private MessageServiceImpl messageService;
    @Autowired
    private FinanceOrderRepository financeOrderRepository;
    @Autowired
    private AdminUserServiceImpl userService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private HistoryService historyService;

    /**
     * 线上交易员审核
     */
    public Result onlineTraderAuditFinanceOrderMethod(String userId, TaskMap taskMap, Task task, FinanceOrder financeOrder) {
        if (!task.getTaskDefinitionKey().equals(EnumFinanceEventType.onlineTraderAudit.toString()))
            return Result.error(EnumAdminFinanceError.此任务不能进行交易员审核操作.toString());
        orderService.updateFinanceOrderByOnlineTrader(financeOrder);
        methodService.addAttachmentsMethod(financeOrder.getAttachmentList(), task.getId(), task.getProcessInstanceId(), EnumFinanceAttachment.OnlineTraderAuditAttachment);
        if (taskMap.getSubmit() == 0) {
            return Result.success();
        } else {
            if (taskMap.getPass() != 0 && taskMap.getPass() != 1) return Result.error(EnumCommonError.Admin_System_Error);
            Map<String, Object> vars = new HashMap<>();
            vars.put(EnumFinanceEventType.onlineTraderAudit.toString(), taskMap.getPass());
            taskService.complete(task.getId(), vars);
            if (taskMap.getPass() == 1) {
                return Result.success();
            } else {
                return methodService.updateFinanceOrderApproveState(financeOrder.getId(), EnumFinanceStatus.AuditNotPass, userId);
            }
        }
    }

    /**
     * 业务员审核
     */
    public Result salesmanAuditFinanceOrderMethod(String userId, TaskMap taskMap, FinanceOrderSalesmanInfo salesmanInfo, Task task, Long financeId) {
        if (!task.getTaskDefinitionKey().equals(EnumFinanceEventType.salesmanAudit.toString()))
            return Result.error(EnumAdminFinanceError.此任务不能进行业务员审核操作.toString());
        salesmanInfo.setFinanceId(financeId);
        salesmanInfo.setCreateManId(userId);
        salesmanInfo.setCreateTime(new Date());
        salesmanInfo.setLastUpdateManId(userId);
        salesmanInfo.setLastUpdateTime(new Date());
        orderService.saveFinanceOrderSalesmanInfo(salesmanInfo);
        methodService.addAttachmentsMethod(salesmanInfo.getAttachmentList(), task.getId(), task.getProcessInstanceId(), EnumFinanceAttachment.SalesmanAuditAttachment);
        if (salesmanInfo.isNeedSupplyMaterial() && salesmanInfo.isNoticeApplyUser()) {
            FinanceOrder financeOrder = financeOrderRepository.findOne(financeId);
            if (!StringUtils.isEmpty(financeOrder.getApplyUserPhone())) {
                messageService.sendSMS(financeOrder.getApplyUserPhone(), FinanceSendSMSUtils.getUserNeedSupplyMaterialMessage(financeOrder.getSourceId(), "业务"));
            }
        }
        if (taskMap.getSubmit() == 0) {
            return Result.success();
        } else {
            if (taskMap.getPass() != 0 && taskMap.getPass() != 1) return Result.error(EnumCommonError.Admin_System_Error);
            Map<String, Object> vars = new HashMap<>();
            vars.put(EnumFinanceConditions.salesmanAudit.toString(), taskMap.getPass());
            taskService.complete(task.getId(), vars);
            if (taskMap.getPass() == 1) {
                return Result.success();
            } else {
                return methodService.updateFinanceOrderApproveState(financeId, EnumFinanceStatus.AuditNotPass, userId);
            }
        }
    }

    /**
     * 业务员补充尽调员材料
     */
    public Result salesmanSupplyInvestigationMaterialFinanceOrderMethod(String userId, TaskMap taskMap, List<AttachmentObject> attachmentList, Task task, Long financeId) {
        if (!task.getTaskDefinitionKey().equals(EnumFinanceEventType.salesmanSupplyInvestigationMaterial.toString()))
            return Result.error(EnumAdminFinanceError.此任务不能进行业务员补充尽调员材料操作.toString());
        methodService.addAttachmentsMethod(attachmentList, task.getId(), task.getProcessInstanceId(), EnumFinanceAttachment.SalesmanSupplyAttachment_Investigator);
        if (taskMap.getSubmit() == 0) {
            return Result.success();
        } else {
            taskService.complete(task.getId());
            Result result1 = methodService.updateFinanceOrderApproveState(financeId, EnumFinanceStatus.Auditing, userId);
            if (!result1.isSuccess()) return result1;
            Result result = methodService.getLastCompleteTaskUserId(task.getProcessInstanceId(), EnumFinanceEventType.investigatorAudit.toString());
            if (!result.isSuccess()) return result;
            return methodService.setAssignUserMethod(task.getProcessInstanceId(), EnumFinanceEventType.investigatorAudit.toString(), String.valueOf(result.getData()));
        }
    }

    /**
     * 尽调员审核
     */
    public Result investigatorAuditFinanceOrderMethod(String userId, TaskMap taskMap, FinanceOrderInvestigatorInfo investigatorInfo, Task task, Long financeId) {
        if (!task.getTaskDefinitionKey().equals(EnumFinanceEventType.investigatorAudit.toString())) return Result.error(EnumAdminFinanceError.此任务不能进行尽调员审核操作.toString());
        investigatorInfo.setFinanceId(financeId);
        investigatorInfo.setCreateManId(userId);
        investigatorInfo.setCreateTime(new Date());
        investigatorInfo.setLastUpdateManId(userId);
        investigatorInfo.setLastUpdateTime(new Date());
        orderService.saveFinanceOrderInvestigatorInfo(investigatorInfo);
        methodService.addAttachmentsMethod(investigatorInfo.getAttachmentList(), task.getId(), task.getProcessInstanceId(), EnumFinanceAttachment.InvestigatorAuditAttachment);
        if (investigatorInfo.isNeedSupplyMaterial()) {
            FinanceOrder financeOrder = financeOrderRepository.findOne(financeId);
            if (investigatorInfo.isNoticeApplyUser() && !StringUtils.isEmpty(financeOrder.getApplyUserPhone())) {
                messageService.sendSMS(financeOrder.getApplyUserPhone(), FinanceSendSMSUtils.getUserNeedSupplyMaterialMessage(financeOrder.getSourceId(), "尽调"));
            }
            if (investigatorInfo.isNoticeSalesman()) {
                List<HistoricTaskInstance> historicTaskInstanceList = historyService.createHistoricTaskInstanceQuery().processInstanceBusinessKey(String.valueOf(financeId)).finished().orderByTaskCreateTime().desc().list();
                for (HistoricTaskInstance taskInstance : historicTaskInstanceList) {
                    if (taskInstance.getTaskDefinitionKey().equals(EnumFinanceEventType.salesmanAudit.toString())) {
                        UserObject userObject = userService.changeUserObject(identityService.createUserQuery().userId(task.getAssignee()).singleResult());
                        if (userObject == null) return Result.error(EnumCommonError.Admin_System_Error);
                        if (!StringUtils.isEmpty(userObject.getPhone())) {
                            messageService.sendSMS(userObject.getPhone(), FinanceSendSMSUtils.getAdminNeedSupplyMaterialMessage("尽调员", financeOrder.getSourceId(), "尽调"));
                        }
                    }
                }
            }
        }
        if (taskMap.getSubmit() == 0) {
            return Result.success();
        } else {
            if (taskMap.getNeed() != 0 && taskMap.getNeed() != 1 && taskMap.getPass() != 0 && taskMap.getPass() != 1) return Result.error(EnumCommonError.Admin_System_Error);
            Map<String, Object> vars = new HashMap<>();
            vars.put(EnumFinanceConditions.needSalesmanSupplyInvestigationMaterial.toString(), taskMap.getNeed());
            if (taskMap.getNeed() == 0) {
                vars.put(EnumFinanceConditions.investigatorAudit.toString(), taskMap.getPass());
            }
            taskService.complete(task.getId(), vars);
            if (taskMap.getNeed() == 1) {
                Result result1 = methodService.updateFinanceOrderApproveState(financeId, EnumFinanceStatus.SupplyMaterial, userId);
                if (!result1.isSuccess()) return result1;
                Result result = methodService.getLastCompleteTaskUserId(task.getProcessInstanceId(), EnumFinanceEventType.salesmanAudit.toString());
                if (!result.isSuccess()) return result;
                return methodService.setAssignUserMethod(task.getProcessInstanceId(), EnumFinanceEventType.salesmanSupplyInvestigationMaterial.toString(), String.valueOf(result.getData()));
            } else if (taskMap.getPass() == 1) {
                return Result.success();
            } else {
                return methodService.updateFinanceOrderApproveState(financeId, EnumFinanceStatus.AuditNotPass, userId);
            }
        }
    }

    /**
     * 业务员补充监管员材料
     */
    public Result salesmanSupplySupervisionMaterialFinanceOrderMethod(String userId, TaskMap taskMap, Task task, Long financeId) {
        if (!task.getTaskDefinitionKey().equals(EnumFinanceEventType.salesmanSupplySupervisionMaterial.toString()))
            return Result.error(EnumAdminFinanceError.此任务不能进行业务员补充监管员材料操作.toString());
        if (taskMap.getSubmit() == 0) {
            return Result.success();
        } else {
            taskService.complete(task.getId());
            Result result1 = methodService.updateFinanceOrderApproveState(financeId, EnumFinanceStatus.Auditing, userId);
            if (!result1.isSuccess()) return result1;
            Result result = methodService.getLastCompleteTaskUserId(task.getProcessInstanceId(), EnumFinanceEventType.supervisorAudit.toString());
            if (!result.isSuccess()) return result;
            return methodService.setAssignUserMethod(task.getProcessInstanceId(), EnumFinanceEventType.supervisorAudit.toString(), String.valueOf(result.getData()));
        }
    }

    /**
     * 监管员审核
     */
    public Result supervisorAuditFinanceOrderMethod(String userId, TaskMap taskMap, Task task, Long financeId) {
        if (!task.getTaskDefinitionKey().equals(EnumFinanceEventType.supervisorAudit.toString())) return Result.error(EnumAdminFinanceError.此任务不能进行监管员审核操作.toString());
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
            taskService.complete(task.getId(), vars);
            if (taskMap.getNeed() == 1) {
                Result result1 = methodService.updateFinanceOrderApproveState(financeId, EnumFinanceStatus.SupplyMaterial, userId);
                if (!result1.isSuccess()) return result1;
                Result result = methodService.getLastCompleteTaskUserId(task.getProcessInstanceId(), EnumFinanceEventType.salesmanAudit.toString());
                if (!result.isSuccess()) return result;
                return methodService.setAssignUserMethod(task.getProcessInstanceId(), EnumFinanceEventType.salesmanSupplySupervisionMaterial.toString(), String.valueOf(result.getData()));
            } else if (taskMap.getPass() == 1) {
                return Result.success();
            } else {
                return methodService.updateFinanceOrderApproveState(financeId, EnumFinanceStatus.AuditNotPass, userId);
            }
        }
    }

    public Result investigatorSupplyRiskMaterialFinanceOrderMethod(String userId, TaskMap taskMap, Task task, Long financeId) {
        if (!task.getTaskDefinitionKey().equals(EnumFinanceEventType.investigatorSupplyRiskMaterial.toString()))
            return Result.error(EnumAdminFinanceError.此任务不能进行尽调员补充风控人员要求的材料操作.toString());
        if (taskMap.getSubmit() == 0) {
            return Result.success();
        } else {
            taskService.complete(task.getId());
            Result result1 = methodService.updateFinanceOrderApproveState(financeId, EnumFinanceStatus.Auditing, userId);
            if (!result1.isSuccess()) return result1;
            Result result = methodService.getLastCompleteTaskUserId(task.getProcessInstanceId(), EnumFinanceEventType.riskManagerAudit.toString());
            if (!result.isSuccess()) return result;
            return methodService.setAssignUserMethod(task.getProcessInstanceId(), EnumFinanceEventType.riskManagerAudit.toString(), String.valueOf(result.getData()));
        }
    }

    public Result supervisorSupplyRiskMaterialFinanceOrderMethod(String userId, TaskMap taskMap, Task task, Long financeId) {
        if (!task.getTaskDefinitionKey().equals(EnumFinanceEventType.supervisorSupplyRiskMaterial.toString()))
            return Result.error(EnumAdminFinanceError.此任务不能进行监管员补充风控人员要求的材料操作.toString());
        if (taskMap.getSubmit() == 0) {
            return Result.success();
        } else {
            taskService.complete(task.getId());
            Result result1 = methodService.updateFinanceOrderApproveState(financeId, EnumFinanceStatus.Auditing, userId);
            if (!result1.isSuccess()) return result1;
            Result result = methodService.getLastCompleteTaskUserId(task.getProcessInstanceId(), EnumFinanceEventType.riskManagerAudit.toString());
            if (!result.isSuccess()) return result;
            return methodService.setAssignUserMethod(task.getProcessInstanceId(), EnumFinanceEventType.riskManagerAudit.toString(), String.valueOf(result.getData()));
        }
    }

    public Result riskManagerAuditMYRFinanceOrderMethod(String userId, TaskMap taskMap, Task task, Long financeId) {
        if (!task.getTaskDefinitionKey().equals(EnumFinanceEventType.riskManagerAudit.toString())) return Result.error(EnumAdminFinanceError.此任务不能进行风控人员审核操作.toString());
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
            taskService.complete(task.getId(), vars);
            if (taskMap.getNeed() == 1) {
                Result result1 = methodService.updateFinanceOrderApproveState(financeId, EnumFinanceStatus.SupplyMaterial, userId);
                if (!result1.isSuccess()) return result1;
                Result result = methodService.getLastCompleteTaskUserId(task.getProcessInstanceId(), EnumFinanceEventType.investigatorAudit.toString());
                if (!result.isSuccess()) return result;
                return methodService.setAssignUserMethod(task.getProcessInstanceId(), EnumFinanceEventType.investigatorSupplyRiskMaterial.toString(), String.valueOf(result.getData()));
            } else if (taskMap.getPass() == 1) {
                return methodService.updateFinanceOrderApproveState(financeId, EnumFinanceStatus.AuditPass, userId);
            } else {
                return methodService.updateFinanceOrderApproveState(financeId, EnumFinanceStatus.AuditNotPass, userId);
            }
        }
    }


}
