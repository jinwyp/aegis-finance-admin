package com.yimei.finance.controllers.admin.restfulapi.finance;

import com.yimei.finance.config.session.AdminSession;
import com.yimei.finance.repository.admin.applyinfo.FinanceApplyInfoRepository;
import com.yimei.finance.entity.common.file.FileList;
import com.yimei.finance.entity.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
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
    private FinanceApplyInfoRepository financeApplyInfoRepository;

    @RequestMapping(value = "/{financeId}/onlinetrader/material", method = RequestMethod.POST)
    @ApiOperation(value = "线上交易员填写材料", notes = "线上交易员填写材料")
    @ApiImplicitParam(name = "financeId", value = "金融申请单id", required = true, dataType = "Long", paramType = "path")
    public Result myrOnlineTraderAddMaterialMethod() {
        return Result.success();
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
