package com.yimei.finance.service.admin.finance;

import com.yimei.finance.exception.BusinessException;
import com.yimei.finance.repository.admin.finance.FinanceOrderRepository;
import com.yimei.finance.representation.admin.activiti.HistoryTaskObject;
import com.yimei.finance.representation.admin.activiti.HistoryVariableObject;
import com.yimei.finance.representation.admin.activiti.TaskObject;
import com.yimei.finance.representation.admin.finance.enums.EnumAdminFinanceError;
import com.yimei.finance.representation.admin.finance.enums.EnumFinanceAssignType;
import com.yimei.finance.representation.admin.finance.enums.EnumFinanceEndType;
import com.yimei.finance.representation.admin.finance.enums.EnumFinanceEventType;
import com.yimei.finance.representation.admin.finance.object.FinanceOrderObject;
import com.yimei.finance.representation.admin.user.enums.EnumAdminUserError;
import com.yimei.finance.representation.admin.user.object.UserObject;
import com.yimei.finance.representation.common.enums.EnumCommonError;
import com.yimei.finance.representation.common.file.AttachmentObject;
import com.yimei.finance.representation.common.result.Page;
import com.yimei.finance.representation.common.result.Result;
import com.yimei.finance.service.admin.user.AdminUserServiceImpl;
import com.yimei.finance.utils.DozerUtils;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.identity.User;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Attachment;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
     * 根据 taskId 查询任务
     */
    public Result findTaskByTaskId(String taskId, Long sessionCompanyId) {
        HistoricTaskInstance taskInstance = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
        if (taskInstance == null) return Result.error(EnumAdminFinanceError.不存在此任务.toString());
        HistoryTaskObject historyTaskObject = changeHistoryTaskObject(taskInstance);
        if (sessionCompanyId.longValue() == 0 || (sessionCompanyId.longValue() == historyTaskObject.getRiskCompanyId().longValue())) {
            return Result.success().setData(historyTaskObject);
        }
        return Result.error(EnumAdminFinanceError.你没有权限查看此任务.toString());
    }

    /**
     * 查看个人待办任务列表
     */
    public Result findSelfTaskList(String sessionUserId, Page page) {
        List<TaskObject> taskObjectList = new ArrayList<>();
        page.setTotal(taskService.createTaskQuery().taskAssignee(sessionUserId).active().count());
        if (page.getTotal() == 0) return Result.success().setData(taskObjectList);
        Long toIndex = page.getPage() * page.getCount() < page.getTotal() ? page.getPage() * page.getCount() : page.getTotal();
        List<Task> taskList = taskService.createTaskQuery().taskAssignee(sessionUserId).active().orderByDueDateNullsFirst().asc().orderByProcessInstanceId().desc().orderByTaskCreateTime().desc().listPage(page.getOffset(), Math.toIntExact(toIndex));
        taskObjectList = changeTaskObject(taskList);
        return Result.success().setData(taskObjectList).setMeta(page);
    }

    /**
     * 查看个人待领取任务列表
     */
    public Result findSelfWaitClaimTaskList(String sessionUserId, Long sessionCompanyId, Page page) {

        List<String> groupIds = userService.getUserGroupIdList(sessionUserId);
        if (groupIds == null || groupIds.size() == 0) return Result.success().setData(new ArrayList<>());
        List<Task> taskList = taskService.createTaskQuery().taskCandidateGroupIn(groupIds).active().orderByDueDateNullsFirst().asc().orderByProcessInstanceId().desc().orderByTaskCreateTime().desc().list();
        if (taskList == null || taskList.size() == 0) return Result.success().setData(new ArrayList<>());
        List<TaskObject> taskObjectList = changeTaskObject(taskList).parallelStream().filter(task -> (sessionCompanyId.longValue() == 0 || task.getRiskCompanyId().longValue() == sessionCompanyId.longValue())).collect(Collectors.toList());
        page.setTotal(Long.valueOf(taskObjectList.size()));
        int toIndex = page.getPage() * page.getCount() < taskObjectList.size() ? page.getPage() * page.getCount() : taskObjectList.size();
        return Result.success().setData(taskObjectList.subList(page.getOffset(), toIndex)).setMeta(page);
    }

    /**
     * 查看个人处理历史列表
     */
    public Result findSelfHistoryTaskList(String sessionUserId, Page page) {
        page.setTotal(historyService.createHistoricTaskInstanceQuery().taskAssignee(sessionUserId).finished().count());
        Long toIndex = page.getPage() * page.getCount() < page.getTotal() ? page.getPage() * page.getCount() : page.getTotal();
        List<HistoricTaskInstance> historicTaskInstanceList = historyService.createHistoricTaskInstanceQuery().taskAssignee(sessionUserId).finished().orderByDueDateNullsFirst().asc().orderByProcessInstanceId().desc().orderByTaskCreateTime().desc().listPage(page.getOffset(), Math.toIntExact(toIndex));
        return Result.success().setData(changeHistoryTaskObject(historicTaskInstanceList)).setMeta(page);
    }

    /**
     * 管理员领取任务
     */
    @Transactional
    public Result adminClaimTask(String sessionUserId, Long sessionCompanyId, String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).active().singleResult();
        if (task == null) return Result.error(EnumAdminFinanceError.此任务不存在或者已经完成.toString());
        if (!StringUtils.isEmpty(task.getAssignee())) {
            if (task.getAssignee().equals(sessionUserId)) {
                return Result.error(EnumAdminFinanceError.你已经处理此任务.toString());
            } else {
                return Result.error(EnumAdminFinanceError.此任务已被其他人处理.toString());
            }
        }
        TaskObject taskObject = changeTaskObject(task);
        if (sessionCompanyId.longValue() == 0 || taskObject.getRiskCompanyId().longValue() == sessionCompanyId.longValue()) {
            List<IdentityLink> identityLinkList = taskService.getIdentityLinksForTask(task.getId());
            List<String> groupIdList = userService.getUserGroupIdList(sessionUserId);
            for (IdentityLink identityLink : identityLinkList) {
                for (String gid : groupIdList) {
                    if (identityLink.getGroupId().equals(gid)) {
                        taskService.setOwner(task.getId(), sessionUserId);
                        taskService.claim(task.getId(), sessionUserId);
                        return Result.success().setData(true);
                    }
                }
            }
        }
        return Result.error(EnumAdminFinanceError.你没有权限领取此任务.toString());
    }

    /**
     * 管理员分配任务
     */
    public Result adminAssignTask(String sessionUserId, Long sessionCompanyId, String taskId, String userId) {
        TaskObject taskObject = changeTaskObject(taskService.createTaskQuery().taskAssignee(sessionUserId).taskId(taskId).active().singleResult());
        if (taskObject == null) return Result.error(EnumAdminFinanceError.你没有权限处理此任务或者你已经处理过.toString());
        UserObject userObject = userService.changeUserObjectSimple(identityService.createUserQuery().userId(userId).singleResult());
        if (userObject == null) return Result.error(EnumAdminUserError.此用户不存在.toString());
        if (sessionCompanyId == 0 || (sessionCompanyId.longValue() == userObject.getCompanyId().longValue())) {
            List<User> userList = new ArrayList<>();
            String financeEventType = "";
            for (EnumFinanceAssignType type : EnumFinanceAssignType.values()) {
                if (taskObject.getTaskDefinitionKey().equals(type.toString())) {
                    userList = identityService.createUserQuery().memberOfGroup(type.id).list();
                    financeEventType = type.nextStep;
                    break;
                }
            }
            if (StringUtils.isEmpty(financeEventType)) return Result.error(EnumCommonError.Admin_System_Error);
            for (User u : userList) {
                if (u.getId().equals(userId)) {
                    taskService.complete(taskObject.getId());
                    Task t = taskService.createTaskQuery().processInstanceId(taskObject.getProcessInstanceId()).taskDefinitionKey(financeEventType).active().singleResult();
                    if (t == null) throw new BusinessException(EnumCommonError.Admin_System_Error);
                    taskService.setOwner(t.getId(), userId);
                    taskService.setAssignee(t.getId(), userId);
                    return Result.success().setData(true);
                }
            }
        }
        return Result.error(EnumAdminFinanceError.你没有处理此金融单的权限.toString());
    }

    @Transactional
    public void addAttachmentsMethod(List<AttachmentObject> attachmentList, String taskId, String processInstanceId, String type) {
        List<Attachment> oldAttachmentList = taskService.getTaskAttachments(taskId);
        if (oldAttachmentList != null && oldAttachmentList.size() != 0) {
            oldAttachmentList.parallelStream().filter(a -> a.getType().equals(type)).forEach(a -> {
                taskService.deleteAttachment(a.getId());
            });
        }
        if (attachmentList != null && attachmentList.size() != 0) {
            attachmentList.parallelStream().forEach(a -> {
                if (!StringUtils.isEmpty(a.getName()) && !StringUtils.isEmpty(a.getUrl())) {
                    taskService.createAttachment(type, taskId, processInstanceId, a.getName(), a.getDescription(), a.getUrl());
                }
            });
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
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstanceId).active().taskDefinitionKey(financeEventType).list();
        if (taskList == null || taskList.size() == 0 || taskList.size() > 1) throw new BusinessException(EnumCommonError.Admin_System_Error);
        taskService.setOwner(taskList.get(0).getId(), userId);
        taskService.setAssignee(taskList.get(0).getId(), userId);
        return Result.success().setData(taskList.get(0).getId());

    }

    /**
     * 将原附件添加到新任务上
     */
    @Transactional
    public Result addAttachmentListToNewTask(String processInstanceId, String newTaskId, EnumFinanceEventType eventType) {
        List<HistoricTaskInstance> historyTaskList = historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).taskDefinitionKey(eventType.toString()).finished().orderByTaskCreateTime().desc().list();
        if (historyTaskList == null || historyTaskList.size() == 0) throw new BusinessException(EnumCommonError.Admin_System_Error);
        List<Attachment> attachmentList = taskService.getTaskAttachments(historyTaskList.get(0).getId());
        if (attachmentList != null && attachmentList.size() != 0) {
            attachmentList.stream().forEach(a -> {
                taskService.createAttachment(a.getType(), newTaskId, a.getProcessInstanceId(), a.getName(), a.getDescription(), a.getUrl());
            });
        }
        return Result.success();
    }

    /**
     * 获取 上一次完成该任务的 userId
     */
    @Transactional
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
    public TaskObject changeTaskObject(Task task) {
        TaskObject taskObject = DozerUtils.copy(task, TaskObject.class);
        if (task != null) {
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
            if (processInstance == null || StringUtils.isEmpty(processInstance.getBusinessKey())) throw new BusinessException(EnumCommonError.Admin_System_Error);
            FinanceOrderObject financeOrderObject = DozerUtils.copy(orderRepository.findOne(Long.valueOf(processInstance.getBusinessKey())), FinanceOrderObject.class);
            if (financeOrderObject == null) throw new BusinessException(EnumCommonError.Admin_System_Error);
            taskObject.setFinanceId(financeOrderObject.getId());
            taskObject.setApplyCompanyName(financeOrderObject.getApplyCompanyName());
            taskObject.setApplyType(financeOrderObject.getApplyType());
            taskObject.setApplyTypeName(financeOrderObject.getApplyTypeName());
            taskObject.setFinancingAmount(financeOrderObject.getFinancingAmount());
            taskObject.setSourceId(financeOrderObject.getSourceId());
            taskObject.setRiskCompanyId(financeOrderObject.getRiskCompanyId());
            if (!StringUtils.isEmpty(task.getAssignee())) {
                UserObject user = userService.changeUserObjectTask(identityService.createUserQuery().userId(task.getAssignee()).singleResult());
                taskObject.setAssigneeName(user.getUsername());
                taskObject.setAssigneeDepartment(user.getDepartment());
            }
        }
        return taskObject;
    }

    public List<TaskObject> changeTaskObject(List<Task> taskList) {
        if (taskList == null || taskList.size() == 0) return null;
        List<TaskObject> taskObjectList = taskList.parallelStream()
                .map(task -> changeTaskObject(task))
                .collect(Collectors.toList());
        return taskObjectList;
    }

    /**
     * 封装 HistoryTask, 从 HistoryTask 到 HistoryTaskObject
     */
    public HistoryTaskObject changeHistoryTaskObject(HistoricTaskInstance task) {
        HistoryTaskObject taskObject = DozerUtils.copy(task, HistoryTaskObject.class);
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        if (historicProcessInstance == null) throw new BusinessException(EnumCommonError.Admin_System_Error);
        if (StringUtils.isEmpty(historicProcessInstance.getBusinessKey())) throw new BusinessException(EnumCommonError.Admin_System_Error);
        FinanceOrderObject financeOrderObject = DozerUtils.copy(orderRepository.findOne(Long.valueOf(historicProcessInstance.getBusinessKey())), FinanceOrderObject.class);
        if (financeOrderObject == null) throw new BusinessException(EnumCommonError.Admin_System_Error);
        taskObject.setFinanceId(financeOrderObject.getId());
        taskObject.setApplyCompanyName(financeOrderObject.getApplyCompanyName());
        taskObject.setApplyType(financeOrderObject.getApplyType());
        taskObject.setApplyTypeName(financeOrderObject.getApplyTypeName());
        taskObject.setFinancingAmount(financeOrderObject.getFinancingAmount());
        taskObject.setSourceId(financeOrderObject.getSourceId());
        taskObject.setRiskCompanyId(financeOrderObject.getRiskCompanyId());
        if (!StringUtils.isEmpty(task.getAssignee())) {
            UserObject user = userService.changeUserObject(identityService.createUserQuery().userId(task.getAssignee()).singleResult());
            taskObject.setAssigneeName(user.getUsername());
            taskObject.setAssigneeDepartment(user.getDepartment());
        }
        if (historicProcessInstance.getEndTime() == null) {
            List<Task> taskList = taskService.createTaskQuery().active().processInstanceId(historicProcessInstance.getId()).orderByTaskCreateTime().desc().list();
            if (taskList == null || taskList.size() == 0) throw new BusinessException(EnumCommonError.Admin_System_Error);
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
            if (activityInstanceList == null || activityInstanceList.size() == 0) throw new BusinessException(EnumCommonError.Admin_System_Error);
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
        return taskObject;
    }

    public List<HistoryTaskObject> changeHistoryTaskObject(List<HistoricTaskInstance> taskList) {
        if (taskList == null || taskList.size() == 0) return new ArrayList<>();
        return taskList.parallelStream().map(task -> changeHistoryTaskObject(task)).collect(Collectors.toList());
    }



}
