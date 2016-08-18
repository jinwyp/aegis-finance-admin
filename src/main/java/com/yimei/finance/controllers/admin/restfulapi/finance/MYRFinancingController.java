package com.yimei.finance.controllers.admin.restfulapi.finance;

import com.yimei.finance.config.session.AdminSession;
import com.yimei.finance.repository.admin.user.EnumGroupId;
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
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/start", method = RequestMethod.POST)
    @ApiOperation(value = "发起流程", notes = "发起流程")
    @ApiImplicitParam(name = "id", value = "金融申请单id", required = true, dataType = "Integer", paramType = "query")
    public Result startMYRWorkFlowMethod(@RequestParam(value = "id", required = true) int id) {
        runtimeService.startProcessInstanceByKey("financingWorkFlow", String.valueOf(id));
        Task task = taskService.createTaskQuery().processInstanceBusinessKey(String.valueOf(id)).singleResult();
        taskService.addGroupIdentityLink(task.getId(), EnumGroupId.ManageTraderGroup.id, IdentityLinkType.CANDIDATE);
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

    @RequestMapping(value = "/riskmanager/need/investigation/material/{financeId}", method = RequestMethod.PUT)
    @ApiOperation(value = "风控人员确认需要补充尽调材料", notes = "风控人员确认需要补充尽调材料")
    @ApiImplicitParam(name = "financeId", value = "金融申请单id", required = true, dataType = "Integer", paramType = "path")
    public Result myrRiskManagerConfirmNeedSupplyInvestigationMaterialMethod(@PathVariable("financeId")int financeId) {
        return Result.success();
    }

    @RequestMapping(value = "/investigator/supply/riskmanager/material/{financeId}", method = RequestMethod.PUT)
    @ApiOperation(value = "监管员补充材料", notes = "监管员补充风控人员要求的材料")
    @ApiImplicitParam(name = "financeId", value = "金融申请单id", required = true, dataType = "Integer", paramType = "path")
    public Result myrInvestigatorSupplyRiskManagerMaterialMethod(@PathVariable("financeId")int financeId) {
        return Result.success();
    }

    @RequestMapping(value = "/riskmanager/audit/pass/{financeId}", method = RequestMethod.PUT)
    @ApiOperation(value = "风控人员审核通过", notes = "风控人员审核通过")
    @ApiImplicitParam(name = "financeId", value = "金融申请单id", required = true, dataType = "Integer", paramType = "path")
    public Result myrRiskManagerAuditPassMethod(@PathVariable("financeId")int financeId) {
        return Result.success();
    }

    @RequestMapping(value = "/riskmanager/audit/notpass/{financeId}", method = RequestMethod.PUT)
    @ApiOperation(value = "风控人员审核不通过", notes = "风控人员审核不通过, 流程结束.")
    @ApiImplicitParam(name = "financeId", value = "金融申请单id", required = true, dataType = "Integer", paramType = "path")
    public Result myrRiskManagerAuditNotPassMethod(@PathVariable("financeId")int financeId) {
        return Result.success();
    }

    @RequestMapping(value = "/riskmanager/contract/{financeId}", method = RequestMethod.POST)
    @ApiOperation(value = "风控人员上传合同", notes = "风控人员上传合同,流程完成")
    @ApiImplicitParam(name = "financeId", value = "金融申请单id", required = true, dataType = "Integer", paramType = "path")
    public Result myrRiskManagerUploadContractMethod(@PathVariable("financeId")int financeId) {
        return Result.success();
    }


}
