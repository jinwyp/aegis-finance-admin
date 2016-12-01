package com.yimei.finance.admin.service.finance;

import com.yimei.finance.entity.admin.finance.FinanceOrder;
import com.yimei.finance.entity.admin.finance.FinanceOrderRiskManagerInfo;
import com.yimei.finance.exception.BusinessException;
import com.yimei.finance.admin.repository.finance.AdminFinanceOrderRepository;
import com.yimei.finance.admin.repository.finance.AdminFinanceOrderRiskRepository;
import com.yimei.finance.admin.representation.finance.enums.*;
import com.yimei.finance.admin.representation.finance.object.*;
import com.yimei.finance.common.representation.enums.EnumCommonError;
import com.yimei.finance.common.representation.file.AttachmentObject;
import com.yimei.finance.common.representation.result.Result;
import com.yimei.finance.common.representation.result.TaskMap;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("adminFinanceFlowStepService")
public class FinanceFlowStepServiceImpl {
    @Autowired
    private TaskService taskService;
    @Autowired
    private FinanceFlowMethodServiceImpl methodService;
    @Autowired
    private FinanceOrderServiceImpl orderService;
    @Autowired
    private AdminFinanceOrderRepository adminFinanceOrderRepository;
    @Autowired
    private AdminFinanceOrderRiskRepository adminFinanceOrderRiskRepository;

    /**
     * 线上交易员审核
     */
    @Transactional
    public Result onlineTraderAuditFinanceOrderMethod(String userId, TaskMap taskMap, Task task, FinanceOrderObject financeOrder, boolean submit) {
        if (!task.getTaskDefinitionKey().equals(EnumFinanceEventType.onlineTraderAudit.toString()))
            return Result.error(EnumAdminFinanceError.此任务不能进行交易员审核操作.toString());
        if (taskMap.pass == 1 && (financeOrder.getRiskCompanyId() == null || financeOrder.getRiskCompanyId().longValue() == 0))
            throw new BusinessException(EnumAdminFinanceError.请选择风控线.toString());
        orderService.updateFinanceOrderByOnlineTrader(userId, financeOrder);
        methodService.addAttachmentsMethod(financeOrder.getAttachmentList1(), task.getId(), task.getProcessInstanceId(), EnumFinanceAttachment.OnlineTraderAuditAttachment.toString());
        if (submit) {
            if (taskMap.pass != 0 && taskMap.pass != 1) throw new BusinessException(EnumCommonError.Admin_System_Error);
            methodService.setTaskVariableMethod(task.getId(), EnumFinanceEventType.onlineTraderAudit.toString(), taskMap.pass);
            taskService.complete(task.getId());
            if (taskMap.pass == 1) {
                return orderService.updateFinanceOrderApproveState(financeOrder.getId(), EnumFinanceStatus.Auditing, userId);
            } else {
                return orderService.updateFinanceOrderApproveState(financeOrder.getId(), EnumFinanceStatus.AuditNotPass, userId);
            }
        } else {
            return Result.success();
        }
    }

    /**
     * 业务员审核
     */
    @Transactional
    public Result salesmanAuditFinanceOrderMethod(String userId, TaskMap taskMap, FinanceOrderSalesmanInfoObject salesmanInfo, Task task, Long financeId, boolean submit) {
        if (!task.getTaskDefinitionKey().equals(EnumFinanceEventType.salesmanAudit.toString()))
            return Result.error(EnumAdminFinanceError.此任务不能进行业务员审核操作.toString());
        salesmanInfo.setFinanceId(financeId);
        salesmanInfo.setCreateManId(userId);
        salesmanInfo.setCreateTime(new Date());
        methodService.addAttachmentsMethod(salesmanInfo.getAttachmentList1(), task.getId(), task.getProcessInstanceId(), EnumFinanceAttachment.SalesmanAuditAttachment.toString());
        if (submit) {
            if (taskMap.pass != 0 && taskMap.pass != 1) throw new BusinessException(EnumCommonError.Admin_System_Error);
            methodService.setTaskVariableMethod(task.getId(), EnumFinanceEventType.salesmanAudit.toString(), taskMap.pass);
            taskService.complete(task.getId());
            if (taskMap.pass == 1) {
                salesmanInfo.setApproveState(EnumFinanceStatus.AuditPass.name);
                salesmanInfo.setApproveStateId(EnumFinanceStatus.AuditPass.id);
                orderService.saveFinanceOrderSalesmanInfo(userId, salesmanInfo);
                return Result.success();
            } else {
                salesmanInfo.setApproveState(EnumFinanceStatus.AuditNotPass.name);
                salesmanInfo.setApproveStateId(EnumFinanceStatus.AuditNotPass.id);
                orderService.saveFinanceOrderSalesmanInfo(userId, salesmanInfo);
                return orderService.updateFinanceOrderApproveState(financeId, EnumFinanceStatus.AuditNotPass, userId);
            }
        } else {
            orderService.saveFinanceOrderSalesmanInfo(userId, salesmanInfo);
            return Result.success();
        }
    }

    /**
     * 业务员补充尽调员材料
     */
    @Transactional
    public Result salesmanSupplyInvestigationMaterialFinanceOrderMethod(String userId, List<AttachmentObject> attachmentList, Task task, Long financeId) {
        if (!task.getTaskDefinitionKey().equals(EnumFinanceEventType.salesmanSupplyInvestigationMaterial.toString()))
            return Result.error(EnumAdminFinanceError.此任务不能进行业务员补充尽调员材料操作.toString());
        methodService.addAttachmentsMethod(attachmentList, task.getId(), task.getProcessInstanceId(), EnumFinanceAttachment.SalesmanSupplyAttachment_Investigator.toString());
        taskService.complete(task.getId());
        Result result1 = orderService.updateFinanceOrderApproveState(financeId, EnumFinanceStatus.Auditing, userId);
        if (!result1.isSuccess()) return result1;
        Result result = methodService.getLastCompleteTaskUserId(task.getProcessInstanceId(), EnumFinanceEventType.investigatorAudit.toString());
        if (!result.isSuccess()) return result;
        Result result2 = methodService.setAssignUserMethod(task.getProcessInstanceId(), EnumFinanceEventType.investigatorAudit.toString(), String.valueOf(result.getData()));
        if (!result2.isSuccess()) return result2;
        String newTaskId = String.valueOf(result2.getData());
        return methodService.addAttachmentListToNewTask(task.getProcessInstanceId(), newTaskId, EnumFinanceEventType.investigatorAudit);
    }

    /**
     * 尽调员审核
     */
    @Transactional
    public Result investigatorAuditFinanceOrderMethod(String userId, TaskMap taskMap, FinanceOrderInvestigatorInfoObject investigatorInfo, Task task, Long financeId, boolean submit) {
        if (!task.getTaskDefinitionKey().equals(EnumFinanceEventType.investigatorAudit.toString())) return Result.error(EnumAdminFinanceError.此任务不能进行尽调员审核操作.toString());
        FinanceOrder financeOrder = adminFinanceOrderRepository.findOne(financeId);
        investigatorInfo.setFinanceId(financeId);
        investigatorInfo.setApplyCompanyName(financeOrder.getApplyCompanyName());
        investigatorInfo.setCreateManId(userId);
        investigatorInfo.setCreateTime(new Date());
        methodService.addAttachmentsMethod(investigatorInfo.getAttachmentList1(), task.getId(), task.getProcessInstanceId(), EnumFinanceAttachment.InvestigatorAuditAttachment.toString());
        if (submit) {
            if ((taskMap.need != 0 && taskMap.need != 1) || (taskMap.pass != 0 && taskMap.pass != 1)) throw new BusinessException(EnumCommonError.Admin_System_Error);
            methodService.setTaskVariableMethod(task.getId(), EnumFinanceConditions.needSalesmanSupplyInvestigationMaterial.toString(), taskMap.need);
            taskService.complete(task.getId());
            if (taskMap.need == 1) {
                investigatorInfo.setApproveStateId(EnumFinanceStatus.SupplyMaterial.id);
                investigatorInfo.setApproveState(EnumFinanceStatus.SupplyMaterial.name);
                orderService.saveFinanceOrderInvestigatorInfo(userId, investigatorInfo);
                Result result1 = orderService.updateFinanceOrderApproveState(financeId, EnumFinanceStatus.SupplyMaterial, userId);
                if (!result1.isSuccess()) return result1;
                Result result = methodService.getLastCompleteTaskUserId(task.getProcessInstanceId(), EnumFinanceEventType.salesmanAudit.toString());
                if (!result.isSuccess()) return result;
                return methodService.setAssignUserMethod(task.getProcessInstanceId(), EnumFinanceEventType.salesmanSupplyInvestigationMaterial.toString(), String.valueOf(result.getData()));
            } else {
                if (taskMap.pass == 0) {
                    investigatorInfo.setApproveStateId(EnumFinanceStatus.AuditNotPass.id);
                    investigatorInfo.setApproveState(EnumFinanceStatus.AuditNotPass.name);
                } else if (taskMap.pass == 1) {
                    investigatorInfo.setApproveStateId(EnumFinanceStatus.AuditPass.id);
                    investigatorInfo.setApproveState(EnumFinanceStatus.AuditPass.name);
                }
                orderService.saveFinanceOrderInvestigatorInfo(userId, investigatorInfo);
                return Result.success();
            }
        } else {
            orderService.saveFinanceOrderInvestigatorInfo(userId, investigatorInfo);
            return Result.success();
        }
    }

    /**
     * 业务员补充监管员材料
     */
    @Transactional
    public Result salesmanSupplySupervisionMaterialFinanceOrderMethod(String userId, List<AttachmentObject> attachmentList, Task task, Long financeId) {
        if (!task.getTaskDefinitionKey().equals(EnumFinanceEventType.salesmanSupplySupervisionMaterial.toString()))
            return Result.error(EnumAdminFinanceError.此任务不能进行业务员补充监管员材料操作.toString());
        methodService.addAttachmentsMethod(attachmentList, task.getId(), task.getProcessInstanceId(), EnumFinanceAttachment.SalesmanSupplyAttachment_Supervisor.toString());
        taskService.complete(task.getId());
        Result result1 = orderService.updateFinanceOrderApproveState(financeId, EnumFinanceStatus.Auditing, userId);
        if (!result1.isSuccess()) return result1;
        Result result = methodService.getLastCompleteTaskUserId(task.getProcessInstanceId(), EnumFinanceEventType.supervisorAudit.toString());
        if (!result.isSuccess()) return result;
        Result result2 = methodService.setAssignUserMethod(task.getProcessInstanceId(), EnumFinanceEventType.supervisorAudit.toString(), String.valueOf(result.getData()));
        if (!result2.isSuccess()) throw new BusinessException(EnumCommonError.Admin_System_Error);
        String newTaskId = String.valueOf(result2.getData());
        return methodService.addAttachmentListToNewTask(task.getProcessInstanceId(), newTaskId, EnumFinanceEventType.supervisorAudit);
    }

    /**
     * 监管员审核
     */
    @Transactional
    public Result supervisorAuditFinanceOrderMethod(String userId, TaskMap taskMap, FinanceOrderSupervisorInfoObject supervisorInfo, Task task, Long financeId, boolean submit) {
        if (!task.getTaskDefinitionKey().equals(EnumFinanceEventType.supervisorAudit.toString())) return Result.error(EnumAdminFinanceError.此任务不能进行监管员审核操作.toString());
        supervisorInfo.setFinanceId(financeId);
        supervisorInfo.setCreateManId(userId);
        supervisorInfo.setCreateTime(new Date());
        methodService.addAttachmentsMethod(supervisorInfo.getAttachmentList1(), task.getId(), task.getProcessInstanceId(), EnumFinanceAttachment.SupervisorAuditAttachment.toString());
        if (submit) {
            if ((taskMap.need != 0 && taskMap.need != 1) || (taskMap.pass != 0 && taskMap.pass != 1)) throw new BusinessException(EnumCommonError.Admin_System_Error);
            methodService.setTaskVariableMethod(task.getId(), EnumFinanceConditions.needSalesmanSupplySupervisionMaterial.toString(), taskMap.need);
            taskService.complete(task.getId());
            if (taskMap.need == 1) {
                supervisorInfo.setApproveStateId(EnumFinanceStatus.SupplyMaterial.id);
                supervisorInfo.setApproveState(EnumFinanceStatus.SupplyMaterial.name);
                orderService.saveFinanceOrderSupervisorInfo(userId, supervisorInfo);
                Result result1 = orderService.updateFinanceOrderApproveState(financeId, EnumFinanceStatus.SupplyMaterial, userId);
                if (!result1.isSuccess()) return result1;
                Result result = methodService.getLastCompleteTaskUserId(task.getProcessInstanceId(), EnumFinanceEventType.salesmanAudit.toString());
                if (!result.isSuccess()) return result;
                return methodService.setAssignUserMethod(task.getProcessInstanceId(), EnumFinanceEventType.salesmanSupplySupervisionMaterial.toString(), String.valueOf(result.getData()));
            } else {
                if (taskMap.pass == 0) {
                    supervisorInfo.setApproveStateId(EnumFinanceStatus.AuditNotPass.id);
                    supervisorInfo.setApproveState(EnumFinanceStatus.AuditNotPass.name);
                } else if (taskMap.pass == 1) {
                    supervisorInfo.setApproveStateId(EnumFinanceStatus.AuditPass.id);
                    supervisorInfo.setApproveState(EnumFinanceStatus.AuditPass.name);
                }
                orderService.saveFinanceOrderSupervisorInfo(userId, supervisorInfo);
                return Result.success();
            }
        } else {
            orderService.saveFinanceOrderSupervisorInfo(userId, supervisorInfo);
            return Result.success();
        }
    }

    /**
     * 业务员补充风控人员材料
     */
    @Transactional
    public Result salesmanSupplyRiskMaterialFinanceOrderMethod(String userId, List<AttachmentObject> attachmentList, Task task, Long financeId) {
        if (!task.getTaskDefinitionKey().equals(EnumFinanceEventType.salesmanSupplyRiskManagerMaterial.toString()))
            return Result.error(EnumAdminFinanceError.此任务不能进行业务员补充风控人员要求的材料操作.toString());
        methodService.addAttachmentsMethod(attachmentList, task.getId(), task.getProcessInstanceId(), EnumFinanceAttachment.SalesmanSupplyAttachment_RiskManager.toString());
        taskService.complete(task.getId());
        Result result1 = orderService.updateFinanceOrderApproveState(financeId, EnumFinanceStatus.Auditing, userId);
        if (!result1.isSuccess()) throw new BusinessException(result1.getError().getMessage());
        Result result = methodService.getLastCompleteTaskUserId(task.getProcessInstanceId(), EnumFinanceEventType.riskManagerAudit.toString());
        if (!result.isSuccess()) throw new BusinessException(result.getError().getMessage());
        Result result2 = methodService.setAssignUserMethod(task.getProcessInstanceId(), EnumFinanceEventType.riskManagerAudit.toString(), String.valueOf(result.getData()));
        if (!result2.isSuccess()) throw new BusinessException(EnumCommonError.Admin_System_Error);
        String newTaskId = String.valueOf(result2.getData());
        return methodService.addAttachmentListToNewTask(task.getProcessInstanceId(), newTaskId, EnumFinanceEventType.riskManagerAudit);
    }

    /**
     * 风控人员审核
     */
    @Transactional
    public Result riskManagerAuditFinanceOrderMethod(String userId, TaskMap taskMap, FinanceOrderRiskManagerInfoObject riskManagerInfo, Task task, Long financeId, boolean submit) {
        if (!task.getTaskDefinitionKey().equals(EnumFinanceEventType.riskManagerAudit.toString())) return Result.error(EnumAdminFinanceError.此任务不能进行风控人员审核操作.toString());
        riskManagerInfo.setFinanceId(financeId);
        riskManagerInfo.setCreateManId(userId);
        riskManagerInfo.setCreateTime(new Date());
        methodService.addAttachmentsMethod(riskManagerInfo.getAttachmentList1(), task.getId(), task.getProcessInstanceId(), EnumFinanceAttachment.RiskManagerAuditAttachment.toString());
        methodService.addAttachmentsMethod(riskManagerInfo.getAttachmentList3(), task.getId(), task.getProcessInstanceId(), EnumFinanceAttachment.Upstream_Contract_Attachment.toString());
        methodService.addAttachmentsMethod(riskManagerInfo.getAttachmentList4(), task.getId(), task.getProcessInstanceId(), EnumFinanceAttachment.Downstream_Contract_Attachment.toString());
        if (submit) {
            FinanceOrder financeOrder = adminFinanceOrderRepository.findOne(financeId);
            if (taskMap.need == 0 && taskMap.pass == 1 && financeOrder.getApplyType().equals(EnumFinanceOrderType.MYD.toString())) {
                FinanceOrderRiskManagerInfo financeOrderRiskManagerInfo = adminFinanceOrderRiskRepository.findByFinanceId(riskManagerInfo.getFinanceId());
                if (financeOrderRiskManagerInfo == null)
                    return Result.error(EnumAdminFinanceError.你还没有提交合同信息.toString());
                if (financeOrderRiskManagerInfo.getUpstreamContractStatus() != 2)
                    return Result.error(EnumAdminFinanceError.你还没有提交上游合同信息.toString());
                if (financeOrderRiskManagerInfo.getDownstreamContractStatus() != 2)
                    return Result.error(EnumAdminFinanceError.你还没有提交下游合同信息.toString());
            }
            if ((taskMap.need != 0 && taskMap.need != 1) || (taskMap.pass != 0 && taskMap.pass != 1)) throw new BusinessException(EnumCommonError.Admin_System_Error);
            methodService.setTaskVariableMethod(task.getId(), EnumFinanceConditions.needSalesmanSupplyRiskManagerMaterial.toString(), taskMap.need);
            if (taskMap.need == 0) {
                methodService.setTaskVariableMethod(task.getId(), EnumFinanceEventType.riskManagerAudit.toString(), taskMap.pass);
            }
            taskService.complete(task.getId());
            if (taskMap.need == 1) {
                riskManagerInfo.setApproveStateId(EnumFinanceStatus.SupplyMaterial.id);
                riskManagerInfo.setApproveState(EnumFinanceStatus.SupplyMaterial.name);
                orderService.saveFinanceOrderRiskManagerInfo(userId, riskManagerInfo);
                Result result = orderService.updateFinanceOrderApproveState(financeId, EnumFinanceStatus.SupplyMaterial, userId);
                if (!result.isSuccess()) return result;
                Result result1 = methodService.getLastCompleteTaskUserId(task.getProcessInstanceId(), EnumFinanceEventType.salesmanAudit.toString());
                if (!result1.isSuccess()) return result1;
                return methodService.setAssignUserMethod(task.getProcessInstanceId(), EnumFinanceEventType.salesmanSupplyRiskManagerMaterial.toString(), String.valueOf(result1.getData()));
            } else if (taskMap.pass == 1) {
                riskManagerInfo.setApproveStateId(EnumFinanceStatus.AuditPass.id);
                riskManagerInfo.setApproveState(EnumFinanceStatus.AuditPass.name);
                orderService.saveFinanceOrderRiskManagerInfo(userId, riskManagerInfo);
                return orderService.updateFinanceOrderApproveState(financeId, EnumFinanceStatus.AuditPass, userId);
            } else {
                riskManagerInfo.setApproveStateId(EnumFinanceStatus.AuditNotPass.id);
                riskManagerInfo.setApproveState(EnumFinanceStatus.AuditNotPass.name);
                orderService.saveFinanceOrderRiskManagerInfo(userId, riskManagerInfo);
                return orderService.updateFinanceOrderApproveState(financeId, EnumFinanceStatus.AuditNotPass, userId);
            }
        } else {
            orderService.saveFinanceOrderRiskManagerInfo(userId, riskManagerInfo);
            return Result.success();
        }
    }

    /**
     * 风控人员填写合同内容
     */
    @Transactional
    public Result riskManagerAddFinanceOrderContractMethod(String userId, HistoricTaskInstance task, FinanceOrderContractObject financeOrderContractObject, boolean submit) {
        if (!task.getTaskDefinitionKey().equals(EnumFinanceEventType.riskManagerAudit.toString())) return Result.error(EnumAdminFinanceError.当前业务不能完善合同.toString());
        orderService.saveFinanceOrderContract(userId, financeOrderContractObject);
        if (submit) {
            orderService.changeFinanceOrderRiskManagerInfoContractStatus(userId, financeOrderContractObject.getFinanceId(), financeOrderContractObject.getType(), EnumFinanceContractStatus.Submitted.id);
        }
        return Result.success();
    }

}
