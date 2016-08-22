package com.yimei.finance.controllers.admin.restfulapi.user;

import com.yimei.finance.config.session.AdminSession;
import com.yimei.finance.entity.admin.finance.*;
import com.yimei.finance.entity.admin.user.EnumAdminUserError;
import com.yimei.finance.entity.admin.user.EnumSpecialGroup;
import com.yimei.finance.entity.common.enums.EnumCommonError;
import com.yimei.finance.entity.common.result.Page;
import com.yimei.finance.entity.common.result.Result;
import com.yimei.finance.repository.admin.finance.FinanceOrderRepository;
import com.yimei.finance.utils.DozerUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.IdentityLinkType;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = {"admin-api-user"}, description = "金融公用接口")
@RequestMapping("/api/financing/admin/user")
@RestController("adminUserCenterController")
public class UserCenterController {
    @Autowired
    private IdentityService identityService;
    @Autowired
    private AdminSession adminSession;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private FinanceOrderRepository financeOrderRepository;

    @RequestMapping(value = "/determine/type", method = RequestMethod.PUT)
    @ApiOperation(value = "确定金融申请单类型,发起流程", notes = "确定金融申请单类型,发起流程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "financeId", value = "金融申请单id", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "applyType", value = "金融申请单类型", required = true, dataType = "String", paramType = "query")
    })
    public Result determineFinanceOrderMethod(@RequestParam(value = "financeId", required = true)Long financeId,
                                              @RequestParam(value = "applyType", required = true)String applyType) {
        FinanceOrder financeOrder = financeOrderRepository.findOne(financeId);
        if (financeOrder == null) return Result.error(EnumAdminFinanceError.此金融单不存在.toString());
        if (runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(String.valueOf(financeId)).singleResult() != null) return Result.error(EnumAdminFinanceError.此金融单已经创建流程.toString());
        financeOrder.setApplyType(applyType);
        financeOrderRepository.save(financeOrder);
        if (financeOrder.getApplyType().equals(EnumFinanceOrderType.MYR.toString())) {
            runtimeService.startProcessInstanceByKey("financingWorkFlow", String.valueOf(financeOrder.getId()));
            Task task = taskService.createTaskQuery().processInstanceBusinessKey(String.valueOf(financeOrder.getId())).active().singleResult();
            taskService.addGroupIdentityLink(task.getId(), EnumSpecialGroup.ManageTraderGroup.id, IdentityLinkType.CANDIDATE);
            return Result.success().setData(true);
        } else if (financeOrder.getApplyType().equals(EnumFinanceOrderType.MYG.toString())) {

            return Result.success().setData(true);
        } else if (financeOrder.getApplyType().equals(EnumFinanceOrderType.MYD.toString())) {

            return Result.success().setData(true);
        } else {
            return Result.error(EnumCommonError.Admin_System_Error);
        }
    }

    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    @ApiOperation(value = "查看个人任务列表", notes = "查看个人任务列表", response = TaskObject.class, responseContainer = "List")
    @ApiImplicitParam(name = "page", value = "当前页数", required = false, dataType = "Integer", paramType = "query")
    public Result getPersonalTasksMethod(Page page) {
        List<TaskObject> taskList = DozerUtils.copy(taskService.createTaskQuery().taskAssignee(adminSession.getUser().getId()).active().orderByTaskDueDate().desc().listPage(page.getOffset(), page.getCount()), TaskObject.class);
        page.setTotal(taskService.createTaskQuery().taskAssignee(adminSession.getUser().getId()).count());
        return Result.success().setData(taskList).setMeta(page);
    }

    @RequestMapping(value = "/unclaimed/tasks", method = RequestMethod.GET)
    @ApiOperation(value = "查看个人待领取任务列表", notes = "查看个人待领取任务列表")
    @ApiImplicitParam(name = "page", value = "当前页数", required = false, dataType = "Integer", paramType = "query")
    public Result getPersonalWaitClaimTasksMethod(Page page) {
        List<Group> groupList = identityService.createGroupQuery().groupMember(adminSession.getUser().getId()).list();
        List<String> groupIds = new ArrayList<>();
        for (Group group : groupList) {
            groupIds.add(group.getId());
        }
        if (groupIds != null && groupIds.size() != 0) {
            page.setTotal(taskService.createTaskQuery().taskCandidateGroupIn(groupIds).active().count());
            List<TaskObject> taskList = DozerUtils.copy(taskService.createTaskQuery().taskCandidateGroupIn(groupIds).active().orderByTaskDueDate().desc().listPage(page.getOffset(), page.getCount()), TaskObject.class);
            return Result.success().setData(taskList).setMeta(page);
        }
        return Result.success().setData(null).setMeta(page);
    }

    @RequestMapping(value = "/task/{taskId}/claim", method = RequestMethod.PUT)
    @ApiOperation(value = "管理员领取任务", notes = "管理员领取任务操作", response = Boolean.class)
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path")
    public Result onlineTraderManagerClaimTaskMethod(@PathVariable(value = "taskId")String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).active().singleResult();
        List<IdentityLink> identityLinkList = taskService.getIdentityLinksForTask(task.getId());
        List<Group> groupList = identityService.createGroupQuery().groupMember(adminSession.getUser().getId()).list();
        for (IdentityLink identityLink : identityLinkList) {
            for (Group group : groupList) {
                if (identityLink.getGroupId().equals(group.getId())) {
                    taskService.claim(task.getId(), adminSession.getUser().getId());
                    return Result.success().setData(true);
                }
            }
        }
        return Result.error(EnumAdminUserError.你没有权限领取此任务.toString());
    }

    @RequestMapping(value = "/assign/trader/{taskId}/{userId}", method = RequestMethod.PUT)
    @ApiOperation(value = "管理员分配人员", notes = "管理员分配人员操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "userId", value = "被分配人userId", required = true, dataType = "String", paramType = "path")
    })
    public Result assignMYROnlineTraderMethod(@PathVariable(value = "taskId") String taskId,
                                              @PathVariable(value = "userId") String userId) {
        Task task = taskService.createTaskQuery().taskId(taskId).active().singleResult();
        if (task == null) return Result.error(EnumAdminFinanceError.此流程不存在或已经结束.toString());
        User user = identityService.createUserQuery().userId(userId).singleResult();
        if (user == null) return Result.error(EnumAdminUserError.此用户不存在.toString());
        ExecutionEntity execution = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(task.getExecutionId()).singleResult();
        if (execution == null || StringUtils.isEmpty(execution.getActivityId())) return Result.error(EnumCommonError.Admin_System_Error);
        List<User> userList = new ArrayList<>();
        if (execution.getActivityId().equals(EnumFinanceAssignType.assignOnlineTrader.toString())) {
            userList = identityService.createUserQuery().memberOfGroup(EnumSpecialGroup.OnlineTraderGroup.id).list();
        } else if (execution.getActivityId().equals(EnumFinanceAssignType.assignSalesman.toString())) {
            userList = identityService.createUserQuery().memberOfGroup(EnumSpecialGroup.SalesmanGroup.id).list();
        } else if (execution.getActivityId().equals(EnumFinanceAssignType.assignInvestigator.toString())) {
            userList = identityService.createUserQuery().memberOfGroup(EnumSpecialGroup.InvestigatorGroup.id).list();
        } else if (execution.getActivityId().equals(EnumFinanceAssignType.assignSupervisor.toString())) {
            userList = identityService.createUserQuery().memberOfGroup(EnumSpecialGroup.SupervisorGroup.id).list();
        } else if (execution.getActivityId().equals(EnumFinanceAssignType.assignRiskManager.toString())) {
            userList = identityService.createUserQuery().memberOfGroup(EnumSpecialGroup.RiskGroup.id).list();
        } else {
            return Result.error(EnumCommonError.Admin_System_Error);
        }
        for (User u : userList) {
            if (u.getId().equals(userId)) {
                taskService.complete(task.getId());
                List<Task> taskList = taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId()).active().list();
                for (Task t : taskList) {
                    Execution exe = runtimeService.createExecutionQuery().executionId(t.getExecutionId()).singleResult();
                    if (exe.getActivityId().equals(EnumFinanceEventType.onlineTraderAddMaterial.toString())) {
                        taskService.setAssignee(t.getId(), userId);
                        return Result.success().setData(true);
                    }
                }
            }
        }
        return Result.error(EnumAdminFinanceError.该用户没有处理此金融单的权限.toString());
    }

}
