package com.yimei.finance.controllers.admin.restfulapi.finance;

import com.yimei.finance.config.session.AdminSession;
import com.yimei.finance.entity.admin.finance.EnumFinanceAssignEvent;
import com.yimei.finance.entity.admin.finance.EnumFinanceOrderType;
import com.yimei.finance.entity.admin.finance.FinanceApplyInfo;
import com.yimei.finance.entity.admin.user.EnumAdminUserError;
import com.yimei.finance.entity.admin.user.EnumSpecialGroup;
import com.yimei.finance.entity.common.enums.EnumCommonError;
import com.yimei.finance.entity.common.result.Result;
import com.yimei.finance.repository.admin.applyinfo.EnumAdminFinanceError;
import com.yimei.finance.repository.admin.applyinfo.FinanceApplyInfoRepository;
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
@RestController("adminFinanceCommonController")
public class FinanceCommonController {
    @Autowired
    private IdentityService identityService;
    @Autowired
    private AdminSession adminSession;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private FinanceApplyInfoRepository financeApplyInfoRepository;

    @RequestMapping(value = "/determine/type", method = RequestMethod.PUT)
    @ApiOperation(value = "确定金融申请单类型,发起流程", notes = "确定金融申请单类型,发起流程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "financeId", value = "金融申请单id", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "applyType", value = "金融申请单类型", required = true, dataType = "String", paramType = "query")
    })
    public Result determineFinanceOrderMethod(@RequestParam(value = "financeId", required = true)Long financeId,
                                              @RequestParam(value = "applyType", required = true)String applyType) {
        FinanceApplyInfo financeApplyInfo = financeApplyInfoRepository.findOne(financeId);
        if (financeApplyInfo == null) return Result.error(EnumAdminFinanceError.此金融单不存在.toString());
        if (runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(String.valueOf(financeId)) != null) return Result.error(EnumAdminFinanceError.此金融单已经创建流程.toString());
        financeApplyInfoRepository.updateApplyInfoType(financeId, applyType);
        if (financeApplyInfo.getApplyType().equals(EnumFinanceOrderType.MYR.toString())) {
            runtimeService.startProcessInstanceByKey("financingWorkFlow", String.valueOf(financeApplyInfo.getId()));
            Task task = taskService.createTaskQuery().processInstanceBusinessKey(String.valueOf(financeApplyInfo.getId())).singleResult();
            taskService.addGroupIdentityLink(task.getId(), EnumSpecialGroup.ManageTraderGroup.id, IdentityLinkType.CANDIDATE);
            return Result.success().setData(true);
        } else if (financeApplyInfo.getApplyType().equals(EnumFinanceOrderType.MYG.toString())) {

            return Result.success().setData(true);
        } else if (financeApplyInfo.getApplyType().equals(EnumFinanceOrderType.MYD.toString())) {

            return Result.success().setData(true);
        } else {
            return Result.error(EnumCommonError.Admin_System_Error);
        }
    }

    @RequestMapping(value = "/{processInstanceId}/task/claim", method = RequestMethod.PUT)
    @ApiOperation(value = "管理员领取任务", notes = "管理员领取任务操作")
    @ApiImplicitParam(name = "processInstanceId", value = "任务对应流程实例id", required = true, dataType = "String", paramType = "path")
    public Result onlineTraderManagerClaimTaskMethod(@PathVariable(value = "processInstanceId")String processInstanceId) {
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
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


    @RequestMapping(value = "/{processInstanceId}/assign/trader/{userId}", method = RequestMethod.PUT)
    @ApiOperation(value = "管理员分配人员", notes = "管理员分配人员操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processInstanceId", value = "任务对应流程实例id", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "userId", value = "被分配人userId", required = true, dataType = "String", paramType = "path")
    })
    public Result assignMYROnlineTraderMethod(@PathVariable(value = "processInstanceId") String processInstanceId,
                                              @PathVariable(value = "userId") String userId) {
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).active().singleResult();
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
