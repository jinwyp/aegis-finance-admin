package com.yimei.finance.service.admin.finance;

import com.yimei.finance.entity.admin.finance.AttachmentObject;
import com.yimei.finance.entity.admin.finance.FinanceOrder;
import com.yimei.finance.entity.admin.finance.HistoryTaskObject;
import com.yimei.finance.entity.admin.finance.TaskObject;
import com.yimei.finance.entity.admin.user.UserObject;
import com.yimei.finance.repository.admin.finance.FinanceOrderRepository;
import com.yimei.finance.representation.admin.finance.EnumFinanceAttachment;
import com.yimei.finance.representation.admin.finance.EnumFinanceStatus;
import com.yimei.finance.representation.admin.finance.FinanceOrderObject;
import com.yimei.finance.representation.admin.finance.FinanceSMSMessage;
import com.yimei.finance.representation.common.enums.EnumCommonError;
import com.yimei.finance.representation.common.result.Result;
import com.yimei.finance.service.admin.user.AdminUserServiceImpl;
import com.yimei.finance.service.common.message.MessageServiceImpl;
import com.yimei.finance.utils.DozerUtils;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Attachment;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
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
    @Autowired
    private MessageServiceImpl messageService;

    /**
     * 添加附件方法
     */
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
     * 更改金融单状态
     */
    @Transactional
    public Result updateFinanceOrderApproveState(Long financeId, EnumFinanceStatus status, String userId) {
        FinanceOrder financeOrder = orderRepository.findOne(financeId);
        if (financeOrder == null) return Result.error(EnumCommonError.Admin_System_Error);
        FinanceOrder order = orderRepository.findOne(financeId);
        order.setApproveStateId(status.id);
        order.setApproveState(status.name);
        order.setLastUpdateTime(new Date());
        order.setLastUpdateManId(userId);
        if (status.id == EnumFinanceStatus.AuditPass.id) {
            order.setEndTime(new Date());
            if (!StringUtils.isEmpty(financeOrder.getApplyUserPhone())) {
                messageService.sendSMS(financeOrder.getApplyUserPhone(), FinanceSMSMessage.getUserAuditPassMessage(financeOrder.getSourceId()));
            }
        }
        if (status.id == EnumFinanceStatus.AuditNotPass.id) {
            order.setEndTime(new Date());
            if (!StringUtils.isEmpty(financeOrder.getApplyUserPhone())) {
                messageService.sendSMS(financeOrder.getApplyUserPhone(), FinanceSMSMessage.getUserAuditNotPassMessage(financeOrder.getSourceId()));
            }
        }
        orderRepository.save(order);
        return Result.success();
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
            List<Task> taskList = taskService.createTaskQuery().processInstanceId(historicProcessInstance.getId()).active().list();
            if (taskList == null || taskList.size() == 0) return Result.error(EnumCommonError.Admin_System_Error);
            taskObject.setCurrentName(taskList.get(0).getName());
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
            taskObject.setCurrentName(activityInstanceList.get(0).getActivityName());
            taskObject.setCurrentTaskDefinitionKey(activityInstanceList.get(0).getActivityId());
        }
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
