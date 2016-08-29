package com.yimei.finance.controllers.admin.restfulapi.finance;

import com.yimei.finance.config.session.AdminSession;
import com.yimei.finance.entity.admin.finance.*;
import com.yimei.finance.entity.admin.user.EnumSpecialGroup;
import com.yimei.finance.entity.common.enums.EnumCommonError;
import com.yimei.finance.entity.common.result.Result;
import com.yimei.finance.service.admin.finance.FinanceFlowStepServiceImpl;
import com.yimei.finance.service.admin.finance.FinanceFlowMethodServiceImpl;
import io.swagger.annotations.*;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "admin-api-flow-myr", description = "煤易融相关接口")
@RequestMapping("/api/financing/admin/myr")
@RestController("adminMYRFinancingController")
public class MYRFinancingController {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private AdminSession adminSession;
    @Autowired
    private FinanceFlowMethodServiceImpl workFlowService;
    @Autowired
    private FinanceFlowStepServiceImpl flowStepService;


    @RequestMapping(value = "/onlinetrader/audit/{taskId}", method = RequestMethod.PUT)
    @ApiOperation(value = "线上交易员审核并填写材料", notes = "线上交易员审核并填写材料", response = Boolean.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "pass", value = "是否审核通过, 0:审核不通过,1:审核通过", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "comment", value = "备注,批注等", required = false, dataType = "String", paramType = "query")
    })
    public Result myrOnlineTraderAddMaterialMethod(@PathVariable("taskId") String taskId,
                                                   @RequestParam(value = "pass", required = true) int pass,
                                                   @RequestParam(value = "comment", required = false) String comment,
                                                   @ApiParam(name = "financeOrder", value = "金融申请单对象", required = true) FinanceOrder financeOrder,
                                                   @ApiParam(name = "attachmentList", value = "金融申请单上传单据列表", required = false) AttachmentList attachmentList) {
        return flowStepService.onlineTraderAuditFinanceOrderMethod(adminSession.getUser().getId(), taskId, pass, comment, financeOrder, attachmentList);
    }


    @RequestMapping(value = "/salesman/audit/{taskId}", method = RequestMethod.PUT)
    @ApiOperation(value = "业务员审核并填写材料", notes = "业务员审核并填写材料")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "pass", value = "审核结果: 0:审核不通过, 1:审核通过", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "comment", value = "备注,批注,审核信息等", required = false, dataType = "String", paramType = "query")
    })
    public Result myrSalesmanAddMaterialAndAuditMethod(@PathVariable("taskId") String taskId,
                                                       @RequestParam(value = "pass", required = true) int pass,
                                                       @RequestParam(value = "comment", required = false) String comment,
                                                       @ApiParam(name = "attachmentList", value = "业务员上传资料文件", required = false) @RequestBody AttachmentList attachmentList) {
        return flowStepService.salesmanAutidFinanceOrderMethod(adminSession.getUser().getId(), taskId, pass, comment, attachmentList);
    }


    @RequestMapping(value = "/salesman/supply/investigation/material/{taskId}", method = RequestMethod.POST)
    @ApiOperation(value = "业务员补充尽调材料", notes = "业务员补充尽调材料")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "comment", value = "备注,批注等", required = false, dataType = "String", paramType = "query")
    })
    public Result myrSalesmanSupplyInvestigationMaterialMethod(@PathVariable("taskId") String taskId,
                                                               @RequestParam(value = "comment", required = false) String comment,
                                                               @ApiParam(name = "attachmentList", value = "业务员上传资料文件", required = false) @RequestBody AttachmentList attachmentList) {
        return flowStepService.salesmanSupplyInvestigationMaterialFinanceOrderMethod(adminSession.getUser().getId(), taskId, comment, attachmentList);
    }

    @RequestMapping(value = "/investigator/audit/{taskId}", method = RequestMethod.PUT)
    @ApiOperation(value = "尽调员审核", notes = "尽调员审核")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "金融申请单id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "need", value = "是否需要补充材料: 0:不需要, 1:需要", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pass", value = "审核结果: 0:审核不通过, 1:审核通过", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "comment", value = "备注,批注等", required = false, dataType = "String", paramType = "query")
    })
    public Result myrInvestigatorAuditMethod(@PathVariable("taskId") String taskId,
                                             @RequestParam(value = "need", required = true) int need,
                                             @RequestParam(value = "pass", required = true) int pass,
                                             @RequestParam(value = "comment", required = false) String comment,
                                             @ApiParam(name = "attachmentList", value = "尽调员上传资料文件", required = false) @RequestBody AttachmentList attachmentList) {
        return flowStepService.investigatorAuditFinanceOrderMethod(adminSession.getUser().getId(), taskId, need, pass, comment, attachmentList);
    }

    @RequestMapping(value = "/investigator/supply/riskmanager/material/{taskId}", method = RequestMethod.POST)
    @ApiOperation(value = "尽调员补充材料", notes = "尽调员补充风控人员要求的材料")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "comment", value = "备注,批注等", required = false, dataType = "String", paramType = "query")
    })
    public Result myrInvestigatorSupplyRiskManagerMaterialMethod(@PathVariable("taskId") String taskId,
                                                                 @RequestParam(value = "comment", required = false) String comment,
                                                                 @ApiParam(name = "attachmentList", value = "业务员上传资料文件", required = false) @RequestBody AttachmentList attachmentList) {
        return flowStepService.investigatorSupplyRiskMaterialFinanceOrderMethod(adminSession.getUser().getId(), taskId, comment, attachmentList);
    }

    @RequestMapping(value = "/riskmanager/audit/{taskId}", method = RequestMethod.PUT)
    @ApiOperation(value = "风控人员审核", notes = "风控人员审核")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "comment", value = "备注,批注等", required = false, dataType = "String", paramType = "query")
    })
    public Result myrRiskManagerAuditMethod(@PathVariable("taskId") String taskId,
                                            @RequestParam(value = "need", required = true) int need,
                                            @RequestParam(value = "pass", required = true) int pass,
                                            @RequestParam(value = "comment", required = false) String comment,
                                            @ApiParam(name = "attachmentList", value = "尽调员上传资料文件", required = false) @RequestBody AttachmentList attachmentList) {
        return flowStepService.riskManagerAuditMYRFinanceOrderMethod(adminSession.getUser().getId(), taskId, need, pass, comment, attachmentList);
    }

    @RequestMapping(value = "/riskmanager/contract/{taskId}", method = RequestMethod.POST)
    @ApiOperation(value = "风控人员上传合同", notes = "风控人员上传合同,流程完成", response = Boolean.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "comment", value = "备注,批注等", required = false, dataType = "String", paramType = "query")
    })
    public Result myrRiskManagerUploadContractMethod(@PathVariable("taskId") String taskId,
                                                     @RequestParam(value = "comment", required = false) String comment,
                                                     @ApiParam(name = "attachmentList", value = "风控人员上传资料文件", required = false) @RequestBody AttachmentList attachmentList) {
        return flowStepService.riskManagerAddContractFinanceOrderMethod(adminSession.getUser().getId(), taskId, comment, attachmentList);
    }


}
