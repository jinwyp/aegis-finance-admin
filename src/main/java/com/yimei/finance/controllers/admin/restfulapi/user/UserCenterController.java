package com.yimei.finance.controllers.admin.restfulapi.user;

import com.yimei.finance.config.session.AdminSession;
import com.yimei.finance.exception.BusinessException;
import com.yimei.finance.representation.admin.finance.enums.EnumAdminFinanceError;
import com.yimei.finance.representation.admin.finance.enums.EnumFinanceAssignType;
import com.yimei.finance.representation.admin.finance.object.HistoryTaskObject;
import com.yimei.finance.representation.admin.finance.object.TaskObject;
import com.yimei.finance.representation.admin.user.EnumAdminUserError;
import com.yimei.finance.representation.common.enums.EnumCommonError;
import com.yimei.finance.representation.common.result.Page;
import com.yimei.finance.representation.common.result.Result;
import com.yimei.finance.service.admin.finance.FinanceFlowMethodServiceImpl;
import com.yimei.finance.service.admin.user.AdminUserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(tags = {"admin-api-flow"}, description = "金融公用接口")
@RequestMapping("/api/financing/admin/tasks")
@RestController("adminUserCenterController")
public class UserCenterController {
    @Autowired
    private AdminSession adminSession;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private FinanceFlowMethodServiceImpl workFlowService;
    @Autowired
    private AdminUserServiceImpl userService;
    @Autowired
    private IdentityService identityService;

    @RequestMapping(value = "/{taskId}", method = RequestMethod.GET)
    @ApiOperation(value = "通过 id 查询任务对象", notes = "通过 id 查询任务对象", response = TaskObject.class)
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path")
    public Result getTaskByIdMethod(@PathVariable(value = "taskId") String taskId) {
        HistoricTaskInstance taskInstance = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
        if (taskInstance == null) return Result.error(EnumAdminFinanceError.不存在此任务.toString());
        return workFlowService.changeHistoryTaskObject(taskInstance);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "个人待办任务列表", notes = "个人待办任务列表", response = TaskObject.class, responseContainer = "List")
    @ApiImplicitParam(name = "page", value = "当前页数", required = false, dataType = "int", paramType = "query")
    public Result getPersonalTasksMethod(Page page) {
        List<Task> taskList = taskService.createTaskQuery().taskAssignee(adminSession.getUser().getId()).active().orderByDueDateNullsFirst().asc().orderByProcessInstanceId().desc().orderByTaskCreateTime().desc().list();
        page.setTotal((long) taskList.size());
        int toIndex = page.getPage() * page.getCount() < taskList.size() ? page.getPage() * page.getCount() : taskList.size();
        Result result = workFlowService.changeTaskObject(taskList.subList(page.getOffset(), toIndex));
        if (!result.isSuccess()) return result;
        List<TaskObject> taskObjectList = (List<TaskObject>) result.getData();
        return Result.success().setData(taskObjectList).setMeta(page);
    }

    @RequestMapping(value = "/unclaimed", method = RequestMethod.GET)
    @ApiOperation(value = "给分配管理员查看待领取任务列表", notes = "给线上交易员管理组, 业务员管理组, 尽调员管理组, 监管员管理组, 风控管理组, 查看待领取任务列表", response = TaskObject.class, responseContainer = "List")
    @ApiImplicitParam(name = "page", value = "当前页数", required = false, dataType = "int", paramType = "query")
    public Result getPersonalWaitClaimTasksMethod(Page page) {
        List<String> groupIds = userService.getUserGroupIdList(adminSession.getUser().getId());
        if (groupIds != null && groupIds.size() != 0) {
            List<Task> taskList = taskService.createTaskQuery().taskCandidateGroupIn(groupIds).active().orderByDueDateNullsFirst().asc().orderByProcessInstanceId().desc().orderByTaskCreateTime().desc().list();
            page.setTotal(Long.valueOf(taskList.size()));
            int toIndex = page.getPage() * page.getCount() < taskList.size() ? page.getPage() * page.getCount() : taskList.size();
            Result result = workFlowService.changeTaskObject(taskList.subList(page.getOffset(), toIndex));
            if (!result.isSuccess()) return result;
            List<TaskObject> taskObjectList = (List<TaskObject>) result.getData();
            return Result.success().setData(taskObjectList).setMeta(page);
        }
        return Result.success().setData(null).setMeta(page);
    }

    @RequestMapping(value = "/history", method = RequestMethod.GET)
    @ApiOperation(value = "个人已处理任务列表", notes = "个人已处理任务列表", response = HistoryTaskObject.class, responseContainer = "List")
    @ApiImplicitParam(name = "page", value = "当前页数", required = false, dataType = "int", paramType = "query")
    public Result getPersonalHistoryTasksMethod(Page page) {
        List<HistoricTaskInstance> historicTaskInstanceList = historyService.createHistoricTaskInstanceQuery().taskAssignee(adminSession.getUser().getId()).finished().orderByDueDateNullsFirst().asc().orderByProcessInstanceId().desc().orderByTaskCreateTime().desc().list();
        page.setTotal(Long.valueOf(historicTaskInstanceList.size()));
        Long toIndex = page.getPage() * page.getCount() < page.getTotal() ? page.getPage() * page.getCount() : page.getTotal();
        Result result = workFlowService.changeHistoryTaskObject(historicTaskInstanceList.subList(page.getOffset(), Math.toIntExact(toIndex)));
        if (!result.isSuccess()) return result;
        List<HistoryTaskObject> taskList = (List<HistoryTaskObject>) result.getData();
        return Result.success().setData(taskList).setMeta(page);
    }

    @RequestMapping(value = "/{taskId}/claim", method = RequestMethod.POST)
    @ApiOperation(value = "管理员领取任务", notes = "管理员领取任务操作", response = Boolean.class)
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path")
    public Result onlineTraderManagerClaimTaskMethod(@PathVariable(value = "taskId") String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).active().singleResult();
        if (task == null) return Result.error(EnumAdminFinanceError.此任务不存在或者已经完成.toString());
        if (!StringUtils.isEmpty(task.getAssignee())) {
            if (task.getAssignee().equals(adminSession.getUser().getId())) {
                return Result.error(EnumAdminFinanceError.你已经处理此任务.toString());
            } else {
                return Result.error(EnumAdminFinanceError.此任务已经被其他人处理.toString());
            }
        }
        List<IdentityLink> identityLinkList = taskService.getIdentityLinksForTask(task.getId());
        List<Group> groupList = identityService.createGroupQuery().groupMember(adminSession.getUser().getId()).list();
        for (IdentityLink identityLink : identityLinkList) {
            for (Group group : groupList) {
                if (identityLink.getGroupId().equals(group.getId())) {
                    taskService.setOwner(task.getId(), adminSession.getUser().getId());
                    taskService.claim(task.getId(), adminSession.getUser().getId());
                    return Result.success().setData(true);
                }
            }
        }
        return Result.error(EnumAdminFinanceError.你没有权限领取此任务.toString());
    }

    @RequestMapping(value = "/{taskId}/person/{userId}", method = RequestMethod.PUT)
    @ApiOperation(value = "管理员分配任务", notes = "管理员分配任务操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "userId", value = "被分配人userId", required = true, dataType = "String", paramType = "path")
    })
    @Transactional
    public Result assignMYROnlineTraderMethod(@PathVariable(value = "taskId") String taskId,
                                              @PathVariable(value = "userId") String userId) {
        Task task = taskService.createTaskQuery().taskAssignee(adminSession.getUser().getId()).taskId(taskId).active().singleResult();
        if (task == null) return Result.error(EnumAdminFinanceError.你没有权限处理此任务或者你已经处理过.toString());
        User user = identityService.createUserQuery().userId(userId).singleResult();
        if (user == null) return Result.error(EnumAdminUserError.此用户不存在.toString());
        List<User> userList = new ArrayList<>();
        String financeEventType = "";
        for (EnumFinanceAssignType type : EnumFinanceAssignType.values()) {
            if (task.getTaskDefinitionKey().equals(type.toString())) {
                userList = identityService.createUserQuery().memberOfGroup(type.id).list();
                financeEventType = type.nextStep;
                break;
            }
        }
        if (StringUtils.isEmpty(financeEventType)) return Result.error(EnumCommonError.Admin_System_Error);
        for (User u : userList) {
            if (u.getId().equals(userId)) {
                taskService.complete(task.getId());
                Task t = taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId()).taskDefinitionKey(financeEventType).active().singleResult();
                if (t == null) throw new BusinessException(EnumCommonError.Admin_System_Error);
                taskService.setOwner(t.getId(), userId);
                taskService.setAssignee(t.getId(), userId);
                return Result.success().setData(true);
            }
        }
        return Result.error(EnumAdminFinanceError.你没有处理此金融单的权限.toString());
    }

}
