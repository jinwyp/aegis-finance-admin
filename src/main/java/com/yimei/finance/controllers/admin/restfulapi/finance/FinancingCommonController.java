package com.yimei.finance.controllers.admin.restfulapi.finance;

import com.yimei.finance.config.session.AdminSession;
import com.yimei.finance.entity.admin.finance.*;
import com.yimei.finance.exception.BusinessException;
import com.yimei.finance.repository.admin.finance.FinanceOrderRepository;
import com.yimei.finance.representation.admin.finance.enums.EnumAdminFinanceError;
import com.yimei.finance.representation.admin.finance.enums.EnumFinanceAssignType;
import com.yimei.finance.representation.admin.finance.enums.EnumFinanceAttachment;
import com.yimei.finance.representation.admin.user.EnumAdminUserError;
import com.yimei.finance.representation.common.enums.EnumCommonError;
import com.yimei.finance.representation.common.result.Result;
import com.yimei.finance.service.admin.finance.FinanceOrderServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
import java.util.Arrays;
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
    @Autowired
    private FinanceOrderServiceImpl orderService;
    @Autowired
    private FinanceOrderRepository orderRepository;

    @RequestMapping(value = "/finance/{financeId}", method = RequestMethod.GET)
    @ApiOperation(value = "通过 金融单id 查看金融详细信息 线上业务员信息", notes = "通过 金融单id 查看金融详细信息 线上业务员信息", response = FinanceOrder.class)
    @ApiImplicitParam(name = "financeId", value = "金融单id", required = true, dataType = "Long", paramType = "path")
    public Result getFinanceOrderDetailByIdMethod(@PathVariable("financeId")Long financeId) {
        return orderService.findById(financeId, Arrays.asList(new EnumFinanceAttachment[] {EnumFinanceAttachment.OnlineTraderAuditAttachment}));
    }

    @RequestMapping(value = "/finance/{financeId}/salesman", method = RequestMethod.GET)
    @ApiOperation(value = "通过 金融单id 查看业务员填写表单详细信息", notes = "通过 金融单id 查看业务员填写表单详细信息", response = FinanceOrderSalesmanInfo.class)
    @ApiImplicitParam(name = "financeId", value = "金融单id", required = true, dataType = "Long", paramType = "path")
    public Result getFinanceOrderSalesmanInfoByFinanceIdMethod(@PathVariable("financeId")Long financeId) {
        return orderService.findSalesmanInfoByFinanceId(financeId, Arrays.asList(new EnumFinanceAttachment[] {EnumFinanceAttachment.SalesmanAuditAttachment}));
    }

    @RequestMapping(value = "/finance/{financeId}/investigator", method = RequestMethod.GET)
    @ApiOperation(value = "通过 金融单id 查看尽调员填写表单详细信息", notes = "通过 金融单id 查看尽调员填写表单详细信息", response = FinanceOrderInvestigatorInfo.class)
    @ApiImplicitParam(name = "financeId", value = "金融单id", required = true, dataType = "Long", paramType = "path")
    public Result getFinanceOrderInvestigatorInfoByFinanceIdMethod(@PathVariable("financeId")Long financeId) {
        return orderService.findInvestigatorInfoByFinanceId(financeId, Arrays.asList(new EnumFinanceAttachment[] {EnumFinanceAttachment.InvestigatorAuditAttachment, EnumFinanceAttachment.SalesmanSupplyAttachment_Investigator}));
    }

    @RequestMapping(value = "/finance/{financeId}/supervisor", method = RequestMethod.GET)
    @ApiOperation(value = "通过 金融单id 查看监管员填写表单详细信息", notes = "通过 金融单id 查看监管员填写表单详细信息", response = FinanceOrderSupervisorInfo.class)
    @ApiImplicitParam(name = "financeId", value = "金融单id", required = true, dataType = "Long", paramType = "path")
    public Result getFinanceOrderSupervisorInfoByFinanceIdMethod(@PathVariable("financeId")Long financeId) {
        return orderService.findSupervisorInfoByFinanceId(financeId, Arrays.asList(new EnumFinanceAttachment[] {EnumFinanceAttachment.SupervisorAuditAttachment, EnumFinanceAttachment.SalesmanSupplyAttachment_Supervisor}));
    }

    @RequestMapping(value = "/finance/{financeId}/riskmanager", method = RequestMethod.GET)
    @ApiOperation(value = "通过 金融单id 查看风控人员填写表单详细信息", notes = "通过 金融单id 查看风控人员填写表单详细信息", response = FinanceOrderRiskManagerInfo.class)
    @ApiImplicitParam(name = "financeId", value = "金融单id", required = true, dataType = "Long", paramType = "path")
    public Result getFinanceOrderRiskManagerInfoByFinanceIdMethod(@PathVariable("financeId")Long financeId) {
        return orderService.findRiskManagerInfoByFinanceId(financeId, Arrays.asList(new EnumFinanceAttachment[] {EnumFinanceAttachment.RiskManagerAuditAttachment, EnumFinanceAttachment.InvestigatorSupplyRiskAttachment, EnumFinanceAttachment.SupervisorSupplyRiskAttachment}));
    }

    @RequestMapping(value = "/finance/{financeId}/tasks", method = RequestMethod.GET)
    @ApiOperation(value = "通过 金融单id 获取所有任务步骤列表", notes = "通过 金融单id 获取所有任务步骤列表", response = Boolean.class)
    @ApiImplicitParam(name = "financeId", value = "金融单id", required = true, dataType = "String", paramType = "path")
    public Result getAllTasksByFinanceIdMethod(@PathVariable(value = "financeId") Long financeId) {
        FinanceOrder financeOrder = orderRepository.findOne(financeId);
        if (financeOrder == null) return Result.error(EnumAdminFinanceError.此金融单不存在.toString());
        return Result.success().setData(orderService.getAllTaskListByFinanceId(financeId));
    }

    @RequestMapping(value = "/tasks/{taskId}/claim", method = RequestMethod.POST)
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
    @Transactional
    public Result assignMYROnlineTraderMethod(@PathVariable(value = "taskId") String taskId,
                                              @PathVariable(value = "userId") String userId) {
        Task task = taskService.createTaskQuery().taskId(taskId).active().singleResult();
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
                taskService.setAssignee(t.getId(), userId);
                return Result.success().setData(true);
            }
        }
        return Result.error(EnumAdminFinanceError.你没有处理此金融单的权限.toString());
    }

    @RequestMapping(value = "/finance/process/{processInstanceId}/image", method = RequestMethod.GET)
    @ApiOperation(value = "通过流程实例id获取流程图", notes = "通过流程实例id获取流程图")
    @ApiImplicitParam(name = "processInstanceId", value = "流程实例id", required = true, dataType = "String", paramType = "path")
    public void getProcessDiagramMethod(@PathVariable("processInstanceId") String processInstanceId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("image/gif");
        OutputStream out = response.getOutputStream();
        HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if (processInstance == null) throw new BusinessException(EnumCommonError.Admin_System_Error);
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
        InputStream inputStream = null;
        if (processInstance.getEndTime() == null) {
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


