package com.yimei.finance.controllers.admin.restfulapi.finance;

import com.yimei.finance.config.session.AdminSession;
import com.yimei.finance.entity.admin.finance.EnumFinanceAssignEvent;
import com.yimei.finance.entity.admin.finance.EnumFinanceOrderType;
import com.yimei.finance.entity.admin.finance.FinanceApplyInfo;
import com.yimei.finance.entity.admin.user.EnumAdminUserError;
import com.yimei.finance.entity.admin.user.EnumSpecialGroup;
import com.yimei.finance.entity.common.enums.EnumCommonError;
import com.yimei.finance.entity.common.result.Page;
import com.yimei.finance.entity.common.result.Result;
import com.yimei.finance.repository.admin.applyinfo.EnumAdminFinanceError;
import com.yimei.finance.repository.admin.applyinfo.FinanceApplyInfoRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.IdentityLinkType;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = {"admin-api-flow"}, description = "金融公用接口")
@RequestMapping("/api/financing/admin")
@RestController
public class FinanceHomeController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private FinanceApplyInfoRepository financeApplyInfoRepository;
    @Autowired
    private AdminSession adminSession;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private RepositoryService repositoryService;

    @RequestMapping(value = "/user/tasks", method = RequestMethod.GET)
    @ApiOperation(value = "查看个人任务列表", notes = "查看个人任务列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页数", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "count", value = "每页显示数量", required = false, dataType = "Integer", paramType = "query")
    })
    public Result getPersonalTasksMethod(Page page) {
        if (identityService.createUserQuery().userId(adminSession.getUser().getId()).singleResult() == null) return Result.error(EnumAdminUserError.此用户不存在.toString());
        List<Task> taskList = taskService.createTaskQuery().taskAssignee(adminSession.getUser().getId()).active().orderByTaskDueDate().desc().listPage(page.getOffset(), page.getCount());
        page.setTotal(taskService.createTaskQuery().taskAssignee(adminSession.getUser().getId()).count());
        return Result.success().setData(taskList).setMeta(page);
    }

    @RequestMapping(value = "/user/unclaimed/tasks", method = RequestMethod.GET)
    @ApiOperation(value = "查看个人待签收任务列表", notes = "查看个人待签收任务列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页数", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "count", value = "每页显示数量", required = false, dataType = "Integer", paramType = "query")
    })
    public Result getPersonalWaitClaimTasksMethod(Page page) {
        List<Group> groupList = identityService.createGroupQuery().groupMember(adminSession.getUser().getId()).list();
        List<String> groupIds = new ArrayList<>();
        for (Group group : groupList) {
            groupIds.add(group.getId());
        }
        System.out.println(" -------------------------------------- admin user " + adminSession.getUser().getId());
        System.out.println(" -------------------------------------- admin user " + adminSession.getUser().getId());
        System.out.println(" -------------------------------------- groupList size " + groupList.size());
        System.out.println(" -------------------------------------- groupList size " + groupList.size());
        System.out.println(" -------------------------------------- groupNameList size " + groupIds.size());
        System.out.println(" -------------------------------------- groupNameList size " + groupIds.size());
        System.out.println(" -------------------------------------- groupNameList " + groupIds.toString());
        System.out.println(" -------------------------------------- groupNameList " + groupIds.toString());
        if (groupIds != null && groupIds.size() != 0) {
            page.setTotal(taskService.createTaskQuery().taskCandidateGroupIn(groupIds).active().count());
            List<Task> taskList = taskService.createTaskQuery().taskCandidateGroupIn(groupIds).active().orderByTaskDueDate().desc().listPage(page.getOffset(), page.getCount());
//            List<Map<String, Object>> tasks = new ArrayList<>();
//            for (Task task : taskList) {
//                Map<String, Object> map = new LinkedHashMap<>();
//                map.put("id", task.getId());
//                map.put("delegationState", task.getDelegationState());
//            }


            // Result.success().setData(h);

            return Result.success().setData(taskList).setMeta(page);
        }
        return Result.success().setData(null).setMeta(page);
    }

    @RequestMapping(value = "/determine/type", method = RequestMethod.PUT)
    @ApiOperation(value = "确定金融申请单类型,发起流程", notes = "确定金融申请单类型,发起流程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "金融申请单id", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "applyType", value = "金融申请单类型", required = true, dataType = "String", paramType = "query")
    })
    public Result determineFinanceOrderMethod(@RequestParam(value = "id", required = true)Long id,
                                              @RequestParam(value = "applyType", required = true)String applyType) {
        FinanceApplyInfo financeApplyInfo = financeApplyInfoRepository.findOne(id);
        if (financeApplyInfo == null) return Result.error(EnumAdminFinanceError.此金融单不存在.toString());
//        applyInfoRepository.updateApplyType(id, applyType);
        if (financeApplyInfo.getApplyType().equals(EnumFinanceOrderType.MYR.toString())) {
            runtimeService.startProcessInstanceByKey("financingWorkFlow", String.valueOf(id));
            Task task = taskService.createTaskQuery().processInstanceBusinessKey(String.valueOf(id)).singleResult();
            taskService.addGroupIdentityLink(task.getId(), EnumSpecialGroup.ManageTraderGroup.id, IdentityLinkType.CANDIDATE);
            return Result.success().setData(true);
        } else if (financeApplyInfo.getApplyType().equals(EnumFinanceOrderType.MYG.toString())) {

            return Result.success().setData(true);
        } else if (financeApplyInfo.getApplyType().equals(EnumFinanceOrderType.MYD.toString())) {

            return Result.success().setData(true);
        } else {
            return Result.error(EnumCommonError.Admin_System_Error.toString());
        }
    }

    @RequestMapping(value = "/{financeId}/task/claim", method = RequestMethod.PUT)
    @ApiOperation(value = "管理员领取任务", notes = "管理员领取任务操作")
    @ApiImplicitParam(name = "financeId", value = "金融申请单id", required = true, dataType = "Integer", paramType = "path")
    public Result onlineTraderManagerClaimTaskMethod(@PathVariable(value = "financeId")int financeId) {
        Task task = taskService.createTaskQuery().processInstanceBusinessKey(String.valueOf(financeId)).singleResult();
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


    @RequestMapping(value = "/{financeId}/assign/trader/{userId}", method = RequestMethod.PUT)
    @ApiOperation(value = "管理员分配人员", notes = "管理员分配人员操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "financeId", value = "金融申请单id", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "userId", value = "被分配人userId", required = true, dataType = "String", paramType = "path")
    })
    public Result assignMYROnlineTraderMethod(@PathVariable(value = "financeId") Long financeId,
                                              @PathVariable(value = "userId") String userId) {
        FinanceApplyInfo financeApplyInfo = financeApplyInfoRepository.findOne(financeId);
        if (financeApplyInfo == null) return Result.error(EnumAdminFinanceError.此金融单不存在.toString());
        Task task = taskService.createTaskQuery().processInstanceBusinessKey(String.valueOf(financeId)).active().singleResult();
        if (task == null) return Result.error(EnumAdminFinanceError.此流程不存在或已经结束.toString());
        ExecutionEntity execution = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(task.getExecutionId()).singleResult();
        if (execution == null || StringUtils.isEmpty(execution.getActivityId())) return Result.error(EnumCommonError.Admin_System_Error);
        List<User> userList = new ArrayList<>();
        if (execution.getActivityId().equals(EnumFinanceAssignEvent.assignOnlineTrader.toString())) {
            userList = identityService.createUserQuery().memberOfGroup(EnumSpecialGroup.OnlineTraderGroup.id).list();
        } else if (execution.getActivityId().equals(EnumFinanceAssignEvent.assignSalesman.toString())) {
            userList = identityService.createUserQuery().memberOfGroup(EnumSpecialGroup.SalesmanGroup.id).list();
        } else if (execution.getActivityId().equals(EnumFinanceAssignEvent.assignInvestigator.toString())) {
            userList = identityService.createUserQuery().memberOfGroup(EnumSpecialGroup.InvestigatorGroup.id).list();
        } else if (execution.getActivityId().equals(EnumFinanceAssignEvent.assignSupervisor.toString())) {
            userList = identityService.createUserQuery().memberOfGroup(EnumSpecialGroup.SupervisorGroup.id).list();
        } else if (execution.getActivityId().equals(EnumFinanceAssignEvent.assignRiskManager.toString())) {
            userList = identityService.createUserQuery().memberOfGroup(EnumSpecialGroup.RiskGroup.id).list();
        } else {
            return Result.error(EnumCommonError.Admin_System_Error);
        }
        for (User user : userList) {
            if (user.getId().equals(userId)) {
                taskService.setAssignee(task.getId(), String.valueOf(userId));
                return Result.success().setData(true);
            }
        }
        return Result.error(EnumAdminFinanceError.该用户没有处理此金融单的权限.toString());
    }

}
