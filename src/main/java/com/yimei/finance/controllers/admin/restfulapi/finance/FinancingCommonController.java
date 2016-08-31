package com.yimei.finance.controllers.admin.restfulapi.finance;

import com.yimei.finance.config.session.AdminSession;
import com.yimei.finance.exception.BusinessException;
import com.yimei.finance.representation.admin.finance.EnumAdminFinanceError;
import com.yimei.finance.representation.admin.finance.EnumFinanceAssignType;
import com.yimei.finance.representation.admin.finance.EnumFinanceEventType;
import com.yimei.finance.representation.admin.user.EnumAdminUserError;
import com.yimei.finance.representation.admin.user.EnumSpecialGroup;
import com.yimei.finance.representation.common.enums.EnumCommonError;
import com.yimei.finance.representation.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Api(tags = {"admin-api-flow"}, description = "金融公用接口")
@RequestMapping("/api/financing/admin")
@RestController("adminFinancingCommonController")
public class FinancingCommonController {
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private AdminSession adminSession;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private HistoryService historyService;

    @RequestMapping(value = "/tasks/{taskId}/claim", method = RequestMethod.POST)
    @ApiOperation(value = "管理员领取任务", notes = "管理员领取任务操作", response = Boolean.class)
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path")
    public Result onlineTraderManagerClaimTaskMethod(@PathVariable(value = "taskId") String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).active().singleResult();
        if (task == null) return Result.error(EnumAdminFinanceError.此任务不存在或者已经完成.toString());
        if (!StringUtils.isEmpty(task.getAssignee())) {
            if (task.getAssignee().equals(adminSession.getUser().getId())) {
                return Result.error(EnumAdminFinanceError.你已经领取此任务.toString());
            } else {
                return Result.error(EnumAdminFinanceError.此任务已经被其他人领取.toString());
            }
        }
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
        return Result.error(EnumAdminFinanceError.你没有权限领取此任务.toString());
    }


    @RequestMapping(value = "/tasks/{taskId}/person/{userId}", method = RequestMethod.PUT)
    @ApiOperation(value = "管理员分配人员", notes = "管理员分配人员操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "userId", value = "被分配人userId", required = true, dataType = "String", paramType = "path")
    })
    public Result assignMYROnlineTraderMethod(@PathVariable(value = "taskId") String taskId,
                                              @PathVariable(value = "userId") String userId) {
        Task task = taskService.createTaskQuery().taskId(taskId).active().singleResult();
        if (task == null) return Result.error(EnumAdminFinanceError.你没有权限处理此任务或者你已经处理过.toString());
        User user = identityService.createUserQuery().userId(userId).singleResult();
        if (user == null) return Result.error(EnumAdminUserError.此用户不存在.toString());
        List<User> userList = new ArrayList<>();
        String financeEventType = "";
        if (task.getTaskDefinitionKey().equals(EnumFinanceAssignType.assignOnlineTrader.toString())) {
            userList = identityService.createUserQuery().memberOfGroup(EnumSpecialGroup.OnlineTraderGroup.id).list();
            financeEventType = EnumFinanceEventType.onlineTraderAudit.toString();
        } else if (task.getTaskDefinitionKey().equals(EnumFinanceAssignType.assignSalesman.toString())) {
            userList = identityService.createUserQuery().memberOfGroup(EnumSpecialGroup.SalesmanGroup.id).list();
            financeEventType = EnumFinanceEventType.salesmanAudit.toString();
        } else if (task.getTaskDefinitionKey().equals(EnumFinanceAssignType.assignInvestigator.toString())) {
            userList = identityService.createUserQuery().memberOfGroup(EnumSpecialGroup.InvestigatorGroup.id).list();
            financeEventType = EnumFinanceEventType.investigatorAudit.toString();
        } else if (task.getTaskDefinitionKey().equals(EnumFinanceAssignType.assignSupervisor.toString())) {
            userList = identityService.createUserQuery().memberOfGroup(EnumSpecialGroup.SupervisorGroup.id).list();
            financeEventType = EnumFinanceEventType.supervisorAudit.toString();
        } else if (task.getTaskDefinitionKey().equals(EnumFinanceAssignType.assignRiskManager.toString())) {
            userList = identityService.createUserQuery().memberOfGroup(EnumSpecialGroup.RiskGroup.id).list();
            financeEventType = EnumFinanceEventType.riskManagerAudit.toString();
        } else {
            return Result.error(EnumCommonError.Admin_System_Error);
        }
        for (User u : userList) {
            if (u.getId().equals(userId)) {
                taskService.complete(task.getId());
                List<Task> taskList = taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId()).active().orderByTaskId().desc().list();
                for (Task t : taskList) {
                    if (t.getTaskDefinitionKey().equals(financeEventType)) {
                        taskService.setAssignee(t.getId(), userId);
                        return Result.success().setData(true);
                    }
                }
                return Result.error(EnumCommonError.Admin_System_Error);
            }
        }
        return Result.error(EnumAdminFinanceError.你没有处理此金融单的权限.toString());
    }

    @RequestMapping(value = "/process/{processInstanceId}/image", method = RequestMethod.GET)
    @ApiOperation(value = "通过流程实例id获取流程图", notes = "通过流程实例id获取流程图")
    @ApiImplicitParam(name = "processInstanceId", value = "流程实例id", required = true, dataType = "String", paramType = "path")
    public void getProcessDiagramMethod(@PathVariable("processInstanceId") String processInstanceId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("image/gif");
        OutputStream out = response.getOutputStream();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if (processInstance == null && historicProcessInstance == null) throw new BusinessException(EnumCommonError.Admin_System_Error);
        String processDefinitionId = processInstance != null ? processInstance.getProcessDefinitionId() : historicProcessInstance.getProcessDefinitionId();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        InputStream inputStream = null;
        if (processInstance != null) {
            inputStream = processEngine.getProcessEngineConfiguration().getProcessDiagramGenerator()
                    .generateDiagram(bpmnModel, "png", runtimeService.getActiveActivityIds(processInstanceId));
        } else {
            inputStream = processEngine.getProcessEngineConfiguration().getProcessDiagramGenerator()
                    .generatePngDiagram(bpmnModel);
        }
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        int len = 0;
        while ((len = inputStream.read()) != -1) {
            bytestream.write(len);
        }
        byte[] b = bytestream.toByteArray();
        bytestream.close();
        out.write(b);
        out.flush();
    }

}


