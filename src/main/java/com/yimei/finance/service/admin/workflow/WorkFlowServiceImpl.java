package com.yimei.finance.service.admin.workflow;

import com.yimei.finance.entity.admin.finance.*;
import com.yimei.finance.entity.admin.user.UserObject;
import com.yimei.finance.entity.common.enums.EnumCommonError;
import com.yimei.finance.entity.common.result.Result;
import com.yimei.finance.repository.admin.finance.FinanceOrderRepository;
import com.yimei.finance.service.admin.user.AdminUserServiceImpl;
import com.yimei.finance.utils.DozerUtils;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLinkType;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkFlowServiceImpl {
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private FinanceOrderRepository financeOrderRepository;
    @Autowired
    private AdminUserServiceImpl userService;
    @Autowired
    private IdentityService identityService;

    /**
     * 添加附件方法
     */
    public void addAttachmentsMethod(AttachmentList attachmentList, String taskId, String processInstanceId) {
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
    public Result addGroupIdentityLinkMethod(String processInstanceId, String financeAssignType, String groupId) {
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
    public Result setAssignUserMethod(String processInstanceId, String financeEventType, String userId) {
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
    public Result auditNotPassMethod(Long financeId) {
        FinanceOrder financeOrder = financeOrderRepository.findOne(financeId);
        if (financeOrder == null) return Result.error(EnumCommonError.Admin_System_Error);
        financeOrder.setApproveStateId(EnumFinanceStatus.AuditNotPass.id);
        financeOrder.setApproveState(EnumFinanceStatus.AuditNotPass.name);
        financeOrderRepository.save(financeOrder);
        return Result.success().setData(true);
    }

    /**
     * 获取 上一次完成该任务的 userId
     */
    public Result getLastCompleteTaskUserId(String processInstanceId, String financeEventType) {
        List<HistoricTaskInstance> taskList = historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).taskDefinitionKey(financeEventType).orderByTaskCreateTime().desc().list();
        if (taskList == null || taskList.size() == 0) return Result.error(EnumCommonError.Admin_System_Error);
        String assignUserId = "";
        for (HistoricTaskInstance taskInstance : taskList) {
            if (!StringUtils.isEmpty(taskInstance.getAssignee())) {
                assignUserId = taskInstance.getAssignee();
            }
        }
        if (StringUtils.isEmpty(assignUserId)) return Result.error(EnumCommonError.Admin_System_Error);
        return Result.success().setData(assignUserId);
    }

    public Result changeTaskObject(Task task) {
        TaskObject taskObject = DozerUtils.copy(task, TaskObject.class);
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        if (processInstance == null) return Result.error(EnumCommonError.Admin_System_Error);
        if (StringUtils.isEmpty(processInstance.getBusinessKey())) return Result.error(EnumCommonError.Admin_System_Error);
        FinanceOrder financeOrder = financeOrderRepository.findOne(Long.valueOf(processInstance.getBusinessKey()));
        if (financeOrder == null) return Result.error(EnumCommonError.Admin_System_Error);
        taskObject.setApplyCompanyName(financeOrder.getApplyCompanyName());
        taskObject.setApplyType(financeOrder.getApplyType());
        taskObject.setFinancingAmount(financeOrder.getFinancingAmount());
        if (!StringUtils.isEmpty(task.getAssignee())) {
            UserObject user = userService.changeUserObject(identityService.createUserQuery().userId(task.getAssignee()).singleResult());
            taskObject.setAssigneeName(user.getUsername());
            taskObject.setAssigneeDepartment(user.getDepartment());
        }
        return Result.success().setData(taskObject);
    }

    public Result changeTaskObject(List<Task> taskList) {
        List<TaskObject> taskObjectList = new ArrayList<>();
        for (Task task : taskList) {
            Result result = changeTaskObject(task);
            if (!result.isSuccess()) return result;
            taskObjectList.add((TaskObject) result.getData());
        }
        return Result.success().setData(taskObjectList);
    }

    public Result changeHistoryTaskObject(HistoricTaskInstance task) {
        HistoryTaskObject taskObject = DozerUtils.copy(task, HistoryTaskObject.class);
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        if (processInstance == null) return Result.error(EnumCommonError.Admin_System_Error);
        if (StringUtils.isEmpty(processInstance.getBusinessKey())) return Result.error(EnumCommonError.Admin_System_Error);
        FinanceOrder financeOrder = financeOrderRepository.findOne(Long.valueOf(processInstance.getBusinessKey()));
        if (financeOrder == null) return Result.error(EnumCommonError.Admin_System_Error);
        taskObject.setApplyCompanyName(financeOrder.getApplyCompanyName());
        taskObject.setApplyType(financeOrder.getApplyType());
        taskObject.setFinancingAmount(financeOrder.getFinancingAmount());
        UserObject user = userService.changeUserObject(identityService.createUserQuery().userId(task.getAssignee()).singleResult());
        taskObject.setAssigneeName(user.getUsername());
        taskObject.setAssigneeDepartment(user.getDepartment());
        return Result.success().setData(taskObject);
    }

    public Result changeHistoryTaskObject(List<HistoricTaskInstance> taskList) {
        List<HistoryTaskObject> taskObjectList = new ArrayList<>();
        for (HistoricTaskInstance task : taskList) {
            Result result = changeHistoryTaskObject(task);
            if (!result.isSuccess()) return result;
            taskObjectList.add((HistoryTaskObject) result.getData());
        }
        return Result.success().setData(taskObjectList);
    }

}
