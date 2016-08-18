package com.yimei.finance.controllers.admin.restfulapi.finance;

import com.yimei.finance.config.session.AdminSession;
import com.yimei.finance.repository.admin.applyinfo.ApplyInfoRepository;
import com.yimei.finance.repository.admin.user.EnumSpecialGroup;
import com.yimei.finance.repository.common.file.FileList;
import com.yimei.finance.repository.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.IdentityLinkType;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Api(tags = {"admin-api-flow"}, description = "处理煤易融相关逻辑")
@RequestMapping("/api/financing/admin/myr")
@RestController
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
    private ApplyInfoRepository applyInfoRepository;

    @RequestMapping(value = "/start/{financeId}", method = RequestMethod.POST)
    @ApiOperation(value = "发起流程", notes = "发起流程", response = Boolean.class)
    @ApiImplicitParam(name = "financeId", value = "金融申请单id", required = true, dataType = "Integer", paramType = "path")
    public Result startMYRWorkFlowMethod(@PathVariable(value = "financeId") int financeId) {
        runtimeService.startProcessInstanceByKey("financingWorkFlow", String.valueOf(financeId));
        Task task = taskService.createTaskQuery().processInstanceBusinessKey(String.valueOf(financeId)).singleResult();
        taskService.addGroupIdentityLink(task.getId(), EnumSpecialGroup.ManageTraderGroup.id, IdentityLinkType.CANDIDATE);
        return Result.success().setData(true);
    }

    @RequestMapping(value = "/{financeId}/trader/manager/task/claim", method = RequestMethod.PUT)
    @ApiOperation(value = "线上交易员管理员领取任务", notes = "线上交易员管理员组内成员领取任务")
    @ApiImplicitParam(name = "financeId", value = "金融申请单id", required = true, dataType = "Integer", paramType = "path")
    public Result onlineTraderManagerClaimTaskMethod(@PathVariable(value = "financeId")int financeId) {
        Task task = taskService.createTaskQuery().processInstanceBusinessKey(String.valueOf(financeId)).singleResult();
        taskService.claim(task.getId(), adminSession.getUser().getId());
        return Result.success();
    }

    @RequestMapping(value = "/{financeId}/assign/trader/{traderId}", method = RequestMethod.PUT)
    @ApiOperation(value = "分配线上交易员操作", notes = "分配线上交易员操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "financeId", value = "金融申请单id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "traderId", value = "交易员id", required = true, dataType = "Integer", paramType = "path")
    })
    public Result assignMYROnlineTraderMethod(@PathVariable(value = "financeId") int financeId,
                                              @PathVariable(value = "traderId") int traderId) {
        Task task = taskService.createTaskQuery().processInstanceBusinessKey(String.valueOf(financeId)).singleResult();
        taskService.setAssignee(task.getId(), String.valueOf(traderId));
        return Result.success();
    }

    @RequestMapping(value = "/trader/material", method = RequestMethod.POST)
    @ApiOperation(value = "线上交易员填写材料", notes = "线上交易员填写材料")
    @ApiImplicitParam(name = "financeId", value = "金融申请单id", required = true, dataType = "Long", paramType = "path")
    public Result myrOnlineTraderAddMaterialMethod() {
        return Result.success();
    }

    @RequestMapping(value = "/{financeId}/salesman/manager/task/claim", method = RequestMethod.PUT)
    @ApiOperation(value = "业务员管理员领取任务", notes = "业务员管理员组内成员领取任务")
    @ApiImplicitParam(name = "financeId", value = "金融申请单id", required = true, dataType = "Integer", paramType = "path")
    public Result salesmanManagerClaimTaskMethod(@PathVariable(value = "financeId")int financeId) {
        Task task = taskService.createTaskQuery().processInstanceBusinessKey(String.valueOf(financeId)).singleResult();
        taskService.claim(task.getId(), adminSession.getUser().getId());
        return Result.success();
    }

    @RequestMapping(value = "/{financeId}/assign/salesman/{salesmanId}", method = RequestMethod.PUT)
    @ApiOperation(value = "分配业务员操作", notes = "分配业务员操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "financeId", value = "金融申请单id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "salesmanId", value = "业务员id", required = true, dataType = "Integer", paramType = "path")
    })
    public Result assignMYRSalesmanMethod(@PathVariable(value = "financeId") int financeId,
                                          @PathVariable(value = "salesmanId") int salesmanId) {
        Task task = taskService.createTaskQuery().processInstanceBusinessKey(String.valueOf(financeId)).singleResult();
        taskService.setAssignee(task.getId(), String.valueOf(salesmanId));
        return Result.success();
    }

    @RequestMapping(value = "/salesman/audit/pass/{financeId}", method = RequestMethod.POST)
    @ApiOperation(value = "业务员填写材料并审核通过", notes = "业务员填写材料并审核通过")
    @ApiImplicitParam(name = "financeId", value = "金融申请单id", required = true, dataType = "Integer", paramType = "path")
    public Result myrSalesmanAddMaterialAndAuditPassMethod(@PathVariable("financeId")int financeId) {
        return Result.success();
    }

    @RequestMapping(value = "/salesman/audit/notpass/{financeId}", method = RequestMethod.POST)
    @ApiOperation(value = "业务员填写材料并审核不通过", notes = "业务员填写材料并审核不通过")
    @ApiImplicitParam(name = "financeId", value = "金融申请单id", required = true, dataType = "Integer", paramType = "path")
    public Result myrSalesmanAddMaterialAndAuditNotPassMethod(@PathVariable("financeId")int financeId) {
        return Result.success();
    }

    @RequestMapping(value = "/{financeId}/investigator/manager/task/claim", method = RequestMethod.PUT)
    @ApiOperation(value = "尽调员管理员领取任务", notes = "尽调员管理员组内成员领取任务")
    @ApiImplicitParam(name = "financeId", value = "金融申请单id", required = true, dataType = "Integer", paramType = "path")
    public Result investigatorManagerClaimTaskMethod(@PathVariable(value = "financeId")int financeId) {
        Task task = taskService.createTaskQuery().processInstanceBusinessKey(String.valueOf(financeId)).singleResult();
        taskService.claim(task.getId(), adminSession.getUser().getId());
        return Result.success();
    }

    @RequestMapping(value = "/{financeId}/assign/investigator/{investigatorId}", method = RequestMethod.PUT)
    @ApiOperation(value = "分配尽调员操作", notes = "分配尽调员操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "financeId", value = "金融申请单id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "investigatorId", value = "尽调员id", required = true, dataType = "Integer", paramType = "path")
    })
    public Result assignMYRInvestigatorMethod(@PathVariable(value = "financeId") int financeId,
                                              @PathVariable(value = "investigatorId") int investigatorId) {
        Task task = taskService.createTaskQuery().processInstanceBusinessKey(String.valueOf(financeId)).singleResult();
        taskService.setAssignee(task.getId(), String.valueOf(investigatorId));
        return Result.success();
    }

    @RequestMapping(value = "/investigator/need/material/{financeId}", method = RequestMethod.PUT)
    @ApiOperation(value = "尽调员确认需要补充材料", notes = "尽调员确认需要业务员补充材料")
    @ApiImplicitParam(name = "financeId", value = "金融申请单id", required = true, dataType = "Integer", paramType = "path")
    public Result myrInvestigatorConfirmNeedSupplyMaterialMethod(@PathVariable("financeId")int financeId) {
        return Result.success();
    }

    @RequestMapping(value = "/salesman/supply/investigation/material/{financeId}", method = RequestMethod.PUT)
    @ApiOperation(value = "业务员补充材料", notes = "业务员补充材料")
    @ApiImplicitParam(name = "financeId", value = "金融申请单id", required = true, dataType = "Integer", paramType = "path")
    public Result myrSalesmanSupplyInvestigationMaterialMethod(@PathVariable("financeId")int financeId) {
        return Result.success();
    }

    @RequestMapping(value = "/investigator/audit/pass/{financeId}", method = RequestMethod.PUT)
    @ApiOperation(value = "尽调员审核通过", notes = "尽调员审核通过")
    @ApiImplicitParam(name = "financeId", value = "金融申请单id", required = true, dataType = "Integer", paramType = "path")
    public Result myrInvestigatorAuditPassMethod(@PathVariable("financeId")int financeId) {
        return Result.success();
    }

    @RequestMapping(value = "/investigator/audit/notpass/{financeId}", method = RequestMethod.PUT)
    @ApiOperation(value = "尽调员审核不通过", notes = "尽调员审核不通过")
    @ApiImplicitParam(name = "financeId", value = "金融申请单id", required = true, dataType = "Integer", paramType = "path")
    public Result myrInvestigatorAuditNotPassMethod(@PathVariable("financeId")int financeId) {
        return Result.success();
    }

    @RequestMapping(value = "/{financeId}/riskmanager/manager/task/claim", method = RequestMethod.PUT)
    @ApiOperation(value = "风控管理员领取任务", notes = "风控管理员组内成员领取任务")
    @ApiImplicitParam(name = "financeId", value = "金融申请单id", required = true, dataType = "Integer", paramType = "path")
    public Result riskManagerManagerClaimTaskMethod(@PathVariable(value = "financeId")int financeId) {
        Task task = taskService.createTaskQuery().processInstanceBusinessKey(String.valueOf(financeId)).singleResult();
        taskService.claim(task.getId(), adminSession.getUser().getId());
        return Result.success();
    }

    @RequestMapping(value = "/{financeId}/assign/riskmanager/{riskManagerId}", method = RequestMethod.PUT)
    @ApiOperation(value = "分配风控人员操作", notes = "分配风控人员操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "financeId", value = "金融申请单id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "riskManagerId", value = "风控人员Id", required = true, dataType = "Integer", paramType = "path")
    })
    public Result assignMYRRiskManagerMethod(@PathVariable(value = "financeId") int financeId,
                                             @PathVariable(value = "riskManagerId") int riskManagerId) {
        Task task = taskService.createTaskQuery().processInstanceBusinessKey(String.valueOf(financeId)).singleResult();
        taskService.setAssignee(task.getId(), String.valueOf(riskManagerId));
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

    @RequestMapping(value = "/{financeId}/riskmanager/audit/pass", method = RequestMethod.PUT)
    @ApiOperation(value = "风控人员审核通过", notes = "风控人员审核通过")
    @ApiImplicitParam(name = "financeId", value = "金融申请单id", required = true, dataType = "Integer", paramType = "path")
    public Result myrRiskManagerAuditPassMethod(@PathVariable("financeId")int financeId) {
        return Result.success();
    }

    @RequestMapping(value = "/{financeId}/riskmanager/audit/notpass", method = RequestMethod.PUT)
    @ApiOperation(value = "风控人员审核不通过", notes = "风控人员审核不通过, 流程结束.")
    @ApiImplicitParam(name = "financeId", value = "金融申请单id", required = true, dataType = "Integer", paramType = "path")
    public Result myrRiskManagerAuditNotPassMethod(@PathVariable("financeId")int financeId) {
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