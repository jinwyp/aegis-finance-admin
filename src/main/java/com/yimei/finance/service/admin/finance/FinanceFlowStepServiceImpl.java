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
import org.activiti.engine.IdentityService;
import org.activiti.engine.TaskService;
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

    /**
     * 线上交易员审核
     */
    public Result onlineTraderAuditFinanceOrderMethod(String userId, TaskMap taskMap, Task task, FinanceOrder financeOrder) {
        if (!task.getTaskDefinitionKey().equals(EnumFinanceEventType.onlineTraderAudit.toString()))
            return Result.error(EnumAdminFinanceError.此任务不能进行交易员审核操作.toString());
        orderService.updateFinanceOrderByOnlineTrader(userId, financeOrder);
        methodService.addAttachmentsMethod(financeOrder.getAttachmentList(), task.getId(), task.getProcessInstanceId(), EnumFinanceAttachment.OnlineTraderAuditAttachment);
        if (taskMap.submit == 0) {
            return Result.success();
        } else {
            if (taskMap.pass != 0 && taskMap.pass != 1) return Result.error(EnumCommonError.Admin_System_Error);
            Map<String, Object> vars = new HashMap<>();
            vars.put(EnumFinanceEventType.onlineTraderAudit.toString(), taskMap.pass);
            taskService.complete(task.getId(), vars);
            if (taskMap.pass == 1) {
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
        if (salesmanInfo.getNeedSupplyMaterial() == 1 && salesmanInfo.getNoticeApplyUser() == 1) {
            FinanceOrder financeOrder = financeOrderRepository.findOne(financeId);
            if (!StringUtils.isEmpty(financeOrder.getApplyUserPhone())) {
                messageService.sendSMS(financeOrder.getApplyUserPhone(), FinanceSMSMessage.getUserNeedSupplyMaterialMessage(financeOrder.getSourceId(), "业务"));
            }
        }
        if (taskMap.submit == 0) {
            return Result.success();
        } else {
            if (taskMap.pass != 0 && taskMap.pass != 1) return Result.error(EnumCommonError.Admin_System_Error);
            Map<String, Object> vars = new HashMap<>();
            vars.put(EnumFinanceConditions.salesmanAudit.toString(), taskMap.pass);
            taskService.complete(task.getId(), vars);
            if (taskMap.pass == 1) {
                return Result.success();
            } else {
                return methodService.updateFinanceOrderApproveState(financeId, EnumFinanceStatus.AuditNotPass, userId);
            }
        }
    }

    /**
     * 业务员补充尽调员材料
     */
    public Result salesmanSupplyInvestigationMaterialFinanceOrderMethod(String userId, List<AttachmentObject> attachmentList, Task task, Long financeId) {
        if (!task.getTaskDefinitionKey().equals(EnumFinanceEventType.salesmanSupplyInvestigationMaterial.toString()))
            return Result.error(EnumAdminFinanceError.此任务不能进行业务员补充尽调员材料操作.toString());
        methodService.addAttachmentsMethod(attachmentList, task.getId(), task.getProcessInstanceId(), EnumFinanceAttachment.SalesmanSupplyAttachment_Investigator);
        taskService.complete(task.getId());
        Result result1 = methodService.updateFinanceOrderApproveState(financeId, EnumFinanceStatus.Auditing, userId);
        if (!result1.isSuccess()) return result1;
        Result result = methodService.getLastCompleteTaskUserId(task.getProcessInstanceId(), EnumFinanceEventType.investigatorAudit.toString());
        if (!result.isSuccess()) return result;
        return methodService.setAssignUserMethod(task.getProcessInstanceId(), EnumFinanceEventType.investigatorAudit.toString(), String.valueOf(result.getData()));
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
        if (taskMap.submit == 0) {
            return Result.success();
        } else {
            if (taskMap.need != 0 && taskMap.need != 1) return Result.error(EnumCommonError.Admin_System_Error);
            Map<String, Object> vars = new HashMap<>();
            vars.put(EnumFinanceConditions.needSalesmanSupplyInvestigationMaterial.toString(), taskMap.need);
            taskService.complete(task.getId(), vars);
            if (taskMap.need == 1) {
                Result result1 = methodService.updateFinanceOrderApproveState(financeId, EnumFinanceStatus.SupplyMaterial, userId);
                if (!result1.isSuccess()) return result1;
                Result result = methodService.getLastCompleteTaskUserId(task.getProcessInstanceId(), EnumFinanceEventType.salesmanAudit.toString());
                if (!result.isSuccess()) return result;
                FinanceOrder financeOrder = financeOrderRepository.findOne(financeId);
                noticeUser(taskMap.need == 1, investigatorInfo.getNoticeApplyUser() == 1, financeOrder.getApplyUserName(), financeOrder.getSourceId(), "尽调");
                noticeAdmin(String.valueOf(result.getData()), "尽调员", financeOrder.getSourceId(), "尽调");
                return methodService.setAssignUserMethod(task.getProcessInstanceId(), EnumFinanceEventType.salesmanSupplyInvestigationMaterial.toString(), String.valueOf(result.getData()));
            } else {
                return Result.success();
            }
        }
    }

    /**
     * 业务员补充监管员材料
     */
    public Result salesmanSupplySupervisionMaterialFinanceOrderMethod(String userId, List<AttachmentObject> attachmentList, Task task, Long financeId) {
        if (!task.getTaskDefinitionKey().equals(EnumFinanceEventType.salesmanSupplySupervisionMaterial.toString()))
            return Result.error(EnumAdminFinanceError.此任务不能进行业务员补充监管员材料操作.toString());
        methodService.addAttachmentsMethod(attachmentList, task.getId(), task.getProcessInstanceId(), EnumFinanceAttachment.SalesmanSupplyAttachment_Supervisor);
        taskService.complete(task.getId());
        Result result1 = methodService.updateFinanceOrderApproveState(financeId, EnumFinanceStatus.Auditing, userId);
        if (!result1.isSuccess()) return result1;
        Result result = methodService.getLastCompleteTaskUserId(task.getProcessInstanceId(), EnumFinanceEventType.supervisorAudit.toString());
        if (!result.isSuccess()) return result;
        return methodService.setAssignUserMethod(task.getProcessInstanceId(), EnumFinanceEventType.supervisorAudit.toString(), String.valueOf(result.getData()));
    }

    /**
     * 监管员审核
     */
    public Result supervisorAuditFinanceOrderMethod(String userId, TaskMap taskMap, FinanceOrderSupervisorInfo supervisorInfo, Task task, Long financeId) {
        if (!task.getTaskDefinitionKey().equals(EnumFinanceEventType.supervisorAudit.toString())) return Result.error(EnumAdminFinanceError.此任务不能进行监管员审核操作.toString());
        supervisorInfo.setFinanceId(financeId);
        supervisorInfo.setCreateManId(userId);
        supervisorInfo.setCreateTime(new Date());
        supervisorInfo.setLastUpdateManId(userId);
        supervisorInfo.setLastUpdateTime(new Date());
        orderService.saveFinanceOrderSupervisorInfo(supervisorInfo);
        methodService.addAttachmentsMethod(supervisorInfo.getAttachmentList(), task.getId(), task.getProcessInstanceId(), EnumFinanceAttachment.SupervisorAuditAttachment);
        if (taskMap.need != 0 && taskMap.need != 1 && taskMap.pass != 0 && taskMap.pass != 1)
            return Result.error(EnumCommonError.Admin_System_Error);
        Map<String, Object> vars = new HashMap<>();
        vars.put(EnumFinanceConditions.needSalesmanSupplySupervisionMaterial.toString(), taskMap.need);
        if (taskMap.need == 0) {
            vars.put(EnumFinanceConditions.supervisorAudit.toString(), taskMap.pass);
        }
        taskService.complete(task.getId(), vars);
        if (taskMap.submit == 1) {
            Result result1 = methodService.updateFinanceOrderApproveState(financeId, EnumFinanceStatus.SupplyMaterial, userId);
            if (!result1.isSuccess()) return result1;
            Result result = methodService.getLastCompleteTaskUserId(task.getProcessInstanceId(), EnumFinanceEventType.salesmanAudit.toString());
            if (!result.isSuccess()) return result;
            FinanceOrder financeOrder = financeOrderRepository.findOne(financeId);
            noticeUser(taskMap.need == 1, supervisorInfo.getNoticeApplyUser() == 1, financeOrder.getApplyUserName(), financeOrder.getSourceId(), "尽调");
            noticeAdmin(String.valueOf(result.getData()), "监管员", financeOrder.getSourceId(), "监管");
            return methodService.setAssignUserMethod(task.getProcessInstanceId(), EnumFinanceEventType.salesmanSupplySupervisionMaterial.toString(), String.valueOf(result.getData()));
        } else {
            return Result.success();
        }
    }

    /**
     * 尽调员补充风控人员材料
     */
    public Result investigatorSupplyRiskMaterialFinanceOrderMethod(String userId, List<AttachmentObject> attachmentList, Task task, Long financeId) {
        if (!task.getTaskDefinitionKey().equals(EnumFinanceEventType.investigatorSupplyRiskMaterial.toString()))
            return Result.error(EnumAdminFinanceError.此任务不能进行尽调员补充风控人员要求的材料操作.toString());
        methodService.addAttachmentsMethod(attachmentList, task.getId(), task.getProcessInstanceId(), EnumFinanceAttachment.InvestigatorSupplyRiskAttachment);
        return supplyRiskMaterialFinanceOrderMethod(userId, task, financeId);
    }

    /**
     * 监管员补充风控人员材料
     */
    public Result supervisorSupplyRiskMaterialFinanceOrderMethod(String userId, List<AttachmentObject> attachmentList, Task task, Long financeId) {
        if (!task.getTaskDefinitionKey().equals(EnumFinanceEventType.supervisorSupplyRiskMaterial.toString()))
            return Result.error(EnumAdminFinanceError.此任务不能进行监管员补充风控人员要求的材料操作.toString());
        methodService.addAttachmentsMethod(attachmentList, task.getId(), task.getProcessInstanceId(), EnumFinanceAttachment.SupervisorSupplyRiskAttachment);
        return supplyRiskMaterialFinanceOrderMethod(userId, task, financeId);
    }

    private Result supplyRiskMaterialFinanceOrderMethod(String userId, Task task, Long financeId) {
        taskService.complete(task.getId());
        Result result1 = methodService.updateFinanceOrderApproveState(financeId, EnumFinanceStatus.Auditing, userId);
        if (!result1.isSuccess()) return result1;
        Result result = methodService.getLastCompleteTaskUserId(task.getProcessInstanceId(), EnumFinanceEventType.riskManagerAudit.toString());
        if (!result.isSuccess()) return result;
        return methodService.setAssignUserMethod(task.getProcessInstanceId(), EnumFinanceEventType.riskManagerAudit.toString(), String.valueOf(result.getData()));
    }

    /**
     * 风控人员审核
     */
    public Result riskManagerAuditFinanceOrderMethod(String userId, TaskMap taskMap, FinanceOrderRiskManagerInfo riskManagerInfo, Task task, Long financeId) {
        if (!task.getTaskDefinitionKey().equals(EnumFinanceEventType.riskManagerAudit.toString())) return Result.error(EnumAdminFinanceError.此任务不能进行风控人员审核操作.toString());
        riskManagerInfo.setFinanceId(financeId);
        riskManagerInfo.setCreateManId(userId);
        riskManagerInfo.setCreateTime(new Date());
        riskManagerInfo.setLastUpdateManId(userId);
        riskManagerInfo.setLastUpdateTime(new Date());
        orderService.saveFinanceOrderRiskManagerInfo(riskManagerInfo);
        methodService.addAttachmentsMethod(riskManagerInfo.getAttachmentList(), task.getId(), task.getProcessInstanceId(), EnumFinanceAttachment.RiskManagerAuditAttachment);
        if (taskMap.submit == 0) {
            return Result.success();
        } else {
            if (taskMap.need != 0 && taskMap.need != 1 && taskMap.need2 != 0 && taskMap.need2 != 1 && taskMap.pass != 0 && taskMap.pass != 1)
                return Result.error(EnumCommonError.Admin_System_Error);
            Map<String, Object> vars = new HashMap<>();
            vars.put(EnumFinanceConditions.needInvestigatorSupplyRiskMaterial.toString(), taskMap.need);
            vars.put(EnumFinanceConditions.needSupervisorSupplyRiskMaterial.toString(), taskMap.need);
            if (taskMap.need == 0 && taskMap.need2 == 0) {
                vars.put(EnumFinanceConditions.riskManagerAudit.toString(), taskMap.pass);
            }
            taskService.complete(task.getId(), vars);
            if (taskMap.need == 1 || taskMap.need2 == 1) {
                FinanceOrder financeOrder = financeOrderRepository.findOne(financeId);
                Result result = methodService.updateFinanceOrderApproveState(financeId, EnumFinanceStatus.SupplyMaterial, userId);
                if (!result.isSuccess()) return result;
                if (taskMap.need == 1) {
                    Result result1 = methodService.getLastCompleteTaskUserId(task.getProcessInstanceId(), EnumFinanceEventType.investigatorAudit.toString());
                    if (!result1.isSuccess()) return result1;
                    noticeUser(true, riskManagerInfo.getNoticeApplyUser() == 1, financeOrder.getApplyUserPhone(), financeOrder.getSourceId(), "尽调");
                    noticeAdmin(String.valueOf(result1.getData()), "风控人员", financeOrder.getSourceId(), "尽调");
                    Result result2 = methodService.setAssignUserMethod(task.getProcessInstanceId(), EnumFinanceEventType.investigatorSupplyRiskMaterial.toString(), String.valueOf(result1.getData()));
                    if (!result2.isSuccess()) return result2;
                }
                if (taskMap.need2 == 1) {
                    Result result1 = methodService.getLastCompleteTaskUserId(task.getProcessInstanceId(), EnumFinanceEventType.supervisorAudit.toString());
                    if (!result1.isSuccess()) return result1;
                    noticeUser(true, riskManagerInfo.getNoticeApplyUser() == 1, financeOrder.getApplyUserPhone(), financeOrder.getSourceId(), "尽调");
                    noticeAdmin(String.valueOf(result1.getData()), "风控人员", financeOrder.getSourceId(), "尽调");

                    Result result2 = methodService.setAssignUserMethod(task.getProcessInstanceId(), EnumFinanceEventType.supervisorSupplyRiskMaterial.toString(), String.valueOf(result1.getData()));
                    if (!result2.isSuccess()) return result2;
                }
                return Result.success();
            } else if (taskMap.pass == 1) {
                return methodService.updateFinanceOrderApproveState(financeId, EnumFinanceStatus.AuditPass, userId);
            } else {
                return methodService.updateFinanceOrderApproveState(financeId, EnumFinanceStatus.AuditNotPass, userId);
            }
        }
    }

    /**
     * 短信通知用户
     */
    public void noticeUser(boolean need, boolean noticeUser, String userPhone, String financeSourceId, String materialType) {
        if (need) {
            if (noticeUser && !StringUtils.isEmpty(userPhone)) {
                messageService.sendSMS(userPhone, FinanceSMSMessage.getUserNeedSupplyMaterialMessage(financeSourceId, materialType));
            }
        }
    }

    /**
     * 短信通知管理员
     */
    public void noticeAdmin(String userId, String adminName, String financeSourceId, String materialType) {
        UserObject userObject = userService.changeUserObject(identityService.createUserQuery().userId(userId).singleResult());
        if (!StringUtils.isEmpty(userObject.getPhone())) {
            messageService.sendSMS(userObject.getPhone(), FinanceSMSMessage.getAdminNeedSupplyMaterialMessage(adminName, financeSourceId, materialType));
        }
    }

}
