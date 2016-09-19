package com.yimei.finance.service.admin.finance;

import com.yimei.finance.exception.BusinessException;
import com.yimei.finance.repository.admin.finance.FinanceOrderRepository;
import com.yimei.finance.representation.admin.finance.enums.EnumFinanceAttachment;
import com.yimei.finance.representation.admin.finance.enums.EnumFinanceEndType;
import com.yimei.finance.representation.admin.finance.enums.EnumFinanceEventType;
import com.yimei.finance.representation.admin.finance.object.*;
import com.yimei.finance.representation.admin.user.UserObject;
import com.yimei.finance.representation.common.enums.EnumCommonError;
import com.yimei.finance.representation.common.result.Result;
import com.yimei.finance.service.admin.user.AdminUserServiceImpl;
import com.yimei.finance.utils.DozerUtils;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Attachment;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service("financeFlowMethodService")
public class FinanceFlowMethodServiceImpl {
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private FinanceOrderRepository orderRepository;
    @Autowired
    private AdminUserServiceImpl userService;
    @Autowired
    private IdentityService identityService;

    /**
     * 添加附件方法
     */
    @Transactional
    public void addAttachmentsMethod(List<AttachmentObject> attachmentList, String taskId, String processInstanceId, EnumFinanceAttachment type) {
        List<Attachment> oldAttachmentList = taskService.getTaskAttachments(taskId);
        if (oldAttachmentList != null && oldAttachmentList.size() != 0) {
            for (Attachment attachment : oldAttachmentList) {
                taskService.deleteAttachment(attachment.getId());
            }
        }
        if (attachmentList != null && attachmentList.size() != 0) {
            for (AttachmentObject attachmentObject : attachmentList) {
                if (!StringUtils.isEmpty(attachmentObject.getName()) && !StringUtils.isEmpty(attachmentObject.getUrl())) {
                    taskService.createAttachment(type.toString(), taskId, processInstanceId, attachmentObject.getName(), attachmentObject.getDescription(), attachmentObject.getUrl());
                }
            }
        }
    }

    /**
     * 添加变量
     */
    public void setTaskVariableMethod(String taskId, String variableName, Object variableValue) {
        taskService.setVariable(taskId, variableName, variableValue);
        taskService.setVariableLocal(taskId, variableName, variableValue);
    }

    /**
     * 指派给人方法
     */
    @Transactional
    public Result setAssignUserMethod(String processInstanceId, String financeEventType, String userId) {
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstanceId).active().list();
        for (Task task : taskList) {
            if (task.getTaskDefinitionKey().equals(financeEventType)) {
                taskService.setAssignee(task.getId(), userId);
                return Result.success().setData(task.getId());
            }
        }
        throw new BusinessException(EnumCommonError.Admin_System_Error);
    }

    /**
     * 将原附件添加到新任务上
     */
    @Transactional
    public Result addAttachmentListToNewTask(String processInstanceId, String newTaskId, EnumFinanceEventType eventType, EnumFinanceAttachment attachmentType) {
        List<HistoricTaskInstance> historyTaskList = historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).taskDefinitionKey(eventType.toString()).finished().orderByTaskCreateTime().desc().list();
        if (historyTaskList == null || historyTaskList.size() == 0) throw new BusinessException(EnumCommonError.Admin_System_Error);
        List<Attachment> oldAttachmentList = taskService.getTaskAttachments(historyTaskList.get(0).getId());
        if (oldAttachmentList != null && oldAttachmentList.size() != 0) {
            for (Attachment attachment : oldAttachmentList) {
                taskService.createAttachment(attachmentType.toString(), newTaskId, processInstanceId, attachment.getName(), attachment.getDescription(), attachment.getUrl());
            }
        }
        return Result.success();
    }

    /**
     * 获取 上一次完成该任务的 userId
     */
    public Result getLastCompleteTaskUserId(String processInstanceId, String financeEventType) {
        List<HistoricTaskInstance> taskList = historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).taskDefinitionKey(financeEventType).orderByTaskCreateTime().desc().list();
        if (taskList == null || taskList.size() == 0) throw new BusinessException(EnumCommonError.Admin_System_Error);
        String assignUserId = "";
        for (HistoricTaskInstance taskInstance : taskList) {
            if (!StringUtils.isEmpty(taskInstance.getAssignee())) {
                assignUserId = taskInstance.getAssignee();
            }
        }
        if (StringUtils.isEmpty(assignUserId)) throw new BusinessException(EnumCommonError.Admin_System_Error);
        return Result.success().setData(assignUserId);
    }

    /**
     * 封装 task, 从 Task 到 TaskObject
     */
    public Result changeTaskObject(Task task) {
        TaskObject taskObject = DozerUtils.copy(task, TaskObject.class);
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        if (processInstance == null) return Result.error(EnumCommonError.Admin_System_Error);
        if (StringUtils.isEmpty(processInstance.getBusinessKey())) return Result.error(EnumCommonError.Admin_System_Error);
        FinanceOrderObject financeOrderObject = DozerUtils.copy(orderRepository.findOne(Long.valueOf(processInstance.getBusinessKey())), FinanceOrderObject.class);
        if (financeOrderObject == null) return Result.error(EnumCommonError.Admin_System_Error);
        taskObject.setFinanceId(financeOrderObject.getId());
        taskObject.setApplyCompanyName(financeOrderObject.getApplyCompanyName());
        taskObject.setApplyType(financeOrderObject.getApplyType());
        taskObject.setApplyTypeName(financeOrderObject.getApplyTypeName());
        taskObject.setFinancingAmount(financeOrderObject.getFinancingAmount());
        taskObject.setSourceId(financeOrderObject.getSourceId());
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

    /**
     * 封装 HistoryTask, 从 HistoryTask 到 HistoryTaskObject
     */
    public Result changeHistoryTaskObject(HistoricTaskInstance task) {
        HistoryTaskObject taskObject = DozerUtils.copy(task, HistoryTaskObject.class);
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        if (historicProcessInstance == null) return Result.error(EnumCommonError.Admin_System_Error);
        if (StringUtils.isEmpty(historicProcessInstance.getBusinessKey())) return Result.error(EnumCommonError.Admin_System_Error);
        FinanceOrderObject financeOrderObject = DozerUtils.copy(orderRepository.findOne(Long.valueOf(historicProcessInstance.getBusinessKey())), FinanceOrderObject.class);
        if (financeOrderObject == null) return Result.error(EnumCommonError.Admin_System_Error);
        taskObject.setFinanceId(financeOrderObject.getId());
        taskObject.setApplyCompanyName(financeOrderObject.getApplyCompanyName());
        taskObject.setApplyType(financeOrderObject.getApplyType());
        taskObject.setApplyTypeName(financeOrderObject.getApplyTypeName());
        taskObject.setFinancingAmount(financeOrderObject.getFinancingAmount());
        taskObject.setSourceId(financeOrderObject.getSourceId());
        if (!StringUtils.isEmpty(task.getAssignee())) {
            UserObject user = userService.changeUserObject(identityService.createUserQuery().userId(task.getAssignee()).singleResult());
            taskObject.setAssigneeName(user.getUsername());
            taskObject.setAssigneeDepartment(user.getDepartment());
        }
        if (historicProcessInstance.getEndTime() == null) {
            List<Task> taskList = taskService.createTaskQuery().active().processInstanceId(historicProcessInstance.getId()).orderByTaskCreateTime().desc().list();
            if (taskList == null || taskList.size() == 0) return Result.error(EnumCommonError.Admin_System_Error);
            String currentName = "";
            for (Task t : taskList) {
                currentName += t.getName() + ",";
            }
            currentName = currentName.substring(0, currentName.length() - 1);
            taskObject.setCurrentName(currentName);
            taskObject.setCurrentTaskDefinitionKey(taskList.get(0).getTaskDefinitionKey());
            if (!StringUtils.isEmpty(taskList.get(0).getAssignee())) {
                UserObject user = userService.changeUserObject(identityService.createUserQuery().userId(taskList.get(0).getAssignee()).singleResult());
                taskObject.setCurrentAssignee(user.getId());
                taskObject.setCurrentAssigneeName(user.getUsername());
                taskObject.setCurrentAssigneeDepartment(user.getDepartment());
            }
        } else {
            List<HistoricActivityInstance> activityInstanceList = historyService.createHistoricActivityInstanceQuery().processInstanceId(historicProcessInstance.getId()).orderByHistoricActivityInstanceStartTime().desc().list();
            if (activityInstanceList == null || activityInstanceList.size() == 0) return Result.error(EnumCommonError.Admin_System_Error);
            for (HistoricActivityInstance instance : activityInstanceList) {
                if (instance.getActivityId().equals(EnumFinanceEndType.completeWorkFlowSuccess.toString())
                        || instance.getActivityId().equals(EnumFinanceEndType.EndByOnlineTrader.toString())
                        || instance.getActivityId().equals(EnumFinanceEndType.EndBySalesman.toString())
                        || instance.getActivityId().equals(EnumFinanceEndType.EndByRiskManager.toString())) {
                    taskObject.setCurrentName(instance.getActivityName());
                    taskObject.setCurrentTaskDefinitionKey(instance.getActivityId());
                    break;
                }
            }
        }
        taskObject.setTaskLocalVariables(DozerUtils.copy(historyService.createHistoricVariableInstanceQuery().taskId(task.getId()).list(), HistoryVariableObject.class));
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
