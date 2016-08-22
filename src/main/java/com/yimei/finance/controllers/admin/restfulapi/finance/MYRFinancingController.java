package com.yimei.finance.controllers.admin.restfulapi.finance;

import com.yimei.finance.config.session.AdminSession;
import com.yimei.finance.entity.admin.finance.*;
import com.yimei.finance.entity.admin.user.EnumSpecialGroup;
import com.yimei.finance.entity.common.enums.EnumCommonError;
import com.yimei.finance.entity.common.file.FileList;
import com.yimei.finance.entity.common.result.Result;
import com.yimei.finance.repository.admin.finance.FinanceOrderRepository;
import com.yimei.finance.service.admin.user.FinanceOrderServiceImpl;
import io.swagger.annotations.*;
import org.activiti.engine.*;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLinkType;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = {"admin-api-flow"})
@RequestMapping("/api/financing/admin/myr")
@RestController("adminMYRFinancingController")
public class MYRFinancingController {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private AdminSession adminSession;
    @Autowired
    private FinanceOrderRepository financeOrderRepository;
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private FinanceOrderServiceImpl financeOrderService;

    @RequestMapping(value = "/onlinetrader/material/{taskId}", method = RequestMethod.POST)
    @ApiOperation(value = "线上交易员填写材料", notes = "线上交易员填写材料", response = Boolean.class)
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path")
    public Result myrOnlineTraderAddMaterialMethod(@PathVariable("taskId")String taskId,
                                                   @ApiParam(name = "financeOrder", value = "金融申请单对象", required = true) FinanceOrder financeOrder,
                                                   @ApiParam(name = "attachmentObjectList", value = "金融申请单上传单据列表", required = false)@RequestBody AttachmentList attachmentList) {
        Task task = taskService.createTaskQuery().taskId(taskId).taskAssignee(adminSession.getUser().getId()).active().singleResult();
        if (task == null) return Result.error(EnumAdminFinanceError.你没有权限处理此任务或者你已经处理过.toString());
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).active().singleResult();
        if (processInstance == null) return Result.error(EnumAdminFinanceError.此流程不存在或已经结束.toString());
        if (StringUtils.isEmpty(processInstance.getBusinessKey()) || financeOrderRepository.findOne(Long.valueOf(processInstance.getBusinessKey())) == null) return Result.error(EnumCommonError.Admin_System_Error);
        financeOrder.setId(Long.valueOf(processInstance.getBusinessKey()));
        financeOrderService.updateFinanceOrder(financeOrder);
        for (AttachmentObject attachmentObject : attachmentList.getAttachmentObjects()) {
            if (!StringUtils.isEmpty(attachmentObject.getName()) && !StringUtils.isEmpty(attachmentObject.getUrl())) {
                taskService.createAttachment(attachmentObject.getType(), task.getId(), task.getProcessInstanceId(), attachmentObject.getName(), attachmentObject.getDescription(), attachmentObject.getUrl());
            }
        }
        taskService.complete(task.getId());
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId()).active().list();
        for (Task t : taskList) {
            Execution exe = runtimeService.createExecutionQuery().executionId(t.getExecutionId()).singleResult();
            if (exe.getActivityId().equals(EnumFinanceAssignType.assignSalesman.toString())) taskService.addGroupIdentityLink(t.getId(), EnumSpecialGroup.ManageSalesmanGroup.id, IdentityLinkType.CANDIDATE);
        }
        return Result.success().setData(true);
    }

    @RequestMapping(value = "/{financeId}/salesman/audit", method = RequestMethod.POST)
    @ApiOperation(value = "业务员填写材料并审核", notes = "业务员填写材料并审核")
    @ApiImplicitParam(name = "financeId", value = "金融申请单id", required = true, dataType = "Integer", paramType = "path")
    public Result myrSalesmanAddMaterialAndAuditMethod(@PathVariable("financeId")int financeId) {
        return Result.success();
    }

    @RequestMapping(value = "/{financeId}/investigator/need/material", method = RequestMethod.PUT)
    @ApiOperation(value = "尽调员确认需要补充材料", notes = "尽调员确认需要业务员补充材料")
    @ApiImplicitParam(name = "financeId", value = "金融申请单id", required = true, dataType = "Integer", paramType = "path")
    public Result myrInvestigatorConfirmNeedSupplyMaterialMethod(@PathVariable("financeId")int financeId) {
        return Result.success();
    }

    @RequestMapping(value = "/{financeId}/salesman/supply/investigation/material", method = RequestMethod.PUT)
    @ApiOperation(value = "业务员补充材料", notes = "业务员补充材料")
    @ApiImplicitParam(name = "financeId", value = "金融申请单id", required = true, dataType = "Integer", paramType = "path")
    public Result myrSalesmanSupplyInvestigationMaterialMethod(@PathVariable("financeId")int financeId) {
        return Result.success();
    }

    @RequestMapping(value = "/{financeId}/investigator/audit", method = RequestMethod.PUT)
    @ApiOperation(value = "尽调员审核", notes = "尽调员审核")
    @ApiImplicitParam(name = "financeId", value = "金融申请单id", required = true, dataType = "Integer", paramType = "path")
    public Result myrInvestigatorAuditMethod(@PathVariable("financeId")int financeId) {
        return Result.success();
    }

    @RequestMapping(value = "/{financeId}/riskmanager/need/investigation/material", method = RequestMethod.PUT)
    @ApiOperation(value = "风控人员确认需要补充尽调材料", notes = "风控人员确认需要补充尽调材料")
    @ApiImplicitParam(name = "financeId", value = "金融申请单id", required = true, dataType = "Integer", paramType = "path")
    public Result myrRiskManagerConfirmNeedSupplyInvestigationMaterialMethod(@PathVariable("financeId")int financeId) {
        return Result.success();
    }

    @RequestMapping(value = "/{financeId}/investigator/supply/riskmanager/material", method = RequestMethod.PUT)
    @ApiOperation(value = "监管员补充材料", notes = "监管员补充风控人员要求的材料")
    @ApiImplicitParam(name = "financeId", value = "金融申请单id", required = true, dataType = "Integer", paramType = "path")
    public Result myrInvestigatorSupplyRiskManagerMaterialMethod(@PathVariable("financeId")int financeId) {
        return Result.success();
    }

    @RequestMapping(value = "/{financeId}/riskmanager/audit", method = RequestMethod.PUT)
    @ApiOperation(value = "风控人员审核", notes = "风控人员审核")
    @ApiImplicitParam(name = "financeId", value = "金融申请单id", required = true, dataType = "Integer", paramType = "path")
    public Result myrRiskManagerAuditMethod(@PathVariable("financeId")int financeId) {
        return Result.success();
    }

    @RequestMapping(value = "/{financeId}/riskmanager/contract", method = RequestMethod.POST)
    @ApiOperation(value = "风控人员上传合同", notes = "风控人员上传合同,流程完成", response = Boolean.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "financeId", value = "金融申请单id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "files", value = "合同文件list", required = true, dataType = "FileList", paramType = "body")
    })
    public Result myrRiskManagerUploadContractMethod(@PathVariable("financeId")int financeId, FileList files) {
        Task task = taskService.createTaskQuery().processInstanceBusinessKey(String.valueOf(financeId)).singleResult();
        Map<String, Object> vars = new HashMap<>();
        vars.put("contracts", files);
        taskService.complete(task.getId(), vars);
        return Result.success().setData(true);
    }


}
