package com.yimei.finance.admin.controller.restfulapi.finance;

import com.yimei.finance.admin.repository.finance.AdminFinanceOrderRepository;
import com.yimei.finance.admin.representation.finance.enums.EnumAdminFinanceError;
import com.yimei.finance.admin.representation.finance.enums.EnumFinanceAttachment;
import com.yimei.finance.admin.representation.finance.object.*;
import com.yimei.finance.admin.service.finance.FinanceFlowMethodServiceImpl;
import com.yimei.finance.admin.service.finance.FinanceOrderServiceImpl;
import com.yimei.finance.common.representation.enums.EnumCommonError;
import com.yimei.finance.common.representation.file.AttachmentObject;
import com.yimei.finance.common.representation.result.Result;
import com.yimei.finance.config.session.AdminSession;
import com.yimei.finance.entity.admin.finance.FinanceOrder;
import com.yimei.finance.exception.BusinessException;
import com.yimei.finance.utils.DozerUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
    private HistoryService historyService;
    @Autowired
    private FinanceOrderServiceImpl orderService;
    @Autowired
    private AdminFinanceOrderRepository orderRepository;
    @Autowired
    private FinanceFlowMethodServiceImpl methodService;
    @Autowired
    private AdminSession adminSession;
    @Autowired
    private TaskService taskService;

    @RequestMapping(value = "/finance/{financeId}", method = RequestMethod.GET)
    @ApiOperation(value = "通过 金融单id 查看金融详细信息 线上业务员信息", notes = "通过 金融单id 查看金融详细信息 线上业务员信息", response = FinanceOrderObject.class)
    @ApiImplicitParam(name = "financeId", value = "金融单id", required = true, dataType = "int", paramType = "path")
    public Result getFinanceOrderDetailByIdMethod(@PathVariable("financeId") Long financeId) {
        return orderService.findById(financeId, EnumFinanceAttachment.OnlineTraderAuditAttachment, adminSession.getUser().getCompanyId());
    }

    @RequestMapping(value = "/finance/{financeId}/salesman", method = RequestMethod.GET)
    @ApiOperation(value = "通过 金融单id 查看业务员填写表单详细信息", notes = "通过 金融单id 查看业务员填写表单详细信息", response = FinanceOrderSalesmanInfoObject.class)
    @ApiImplicitParam(name = "financeId", value = "金融单id", required = true, dataType = "int", paramType = "path")
    public Result getFinanceOrderSalesmanInfoByFinanceIdMethod(@PathVariable("financeId") Long financeId) {
        return orderService.findSalesmanInfoByFinanceId(financeId, EnumFinanceAttachment.SalesmanAuditAttachment, adminSession.getUser().getCompanyId());
    }

    @RequestMapping(value = "/finance/{financeId}/investigator", method = RequestMethod.GET)
    @ApiOperation(value = "通过 金融单id 查看尽调员填写表单详细信息", notes = "通过 金融单id 查看尽调员填写表单详细信息", response = FinanceOrderInvestigatorInfoObject.class)
    @ApiImplicitParam(name = "financeId", value = "金融单id", required = true, dataType = "int", paramType = "path")
    public Result getFinanceOrderInvestigatorInfoByFinanceIdMethod(@PathVariable("financeId") Long financeId) {
        return orderService.findInvestigatorInfoByFinanceId(financeId, EnumFinanceAttachment.InvestigatorAuditAttachment, Arrays.asList(new EnumFinanceAttachment[] {EnumFinanceAttachment.SalesmanSupplyAttachment_Investigator}), adminSession.getUser().getCompanyId());
    }

    @RequestMapping(value = "/finance/{financeId}/supervisor", method = RequestMethod.GET)
    @ApiOperation(value = "通过 金融单id 查看监管员填写表单详细信息", notes = "通过 金融单id 查看监管员填写表单详细信息", response = FinanceOrderSupervisorInfoObject.class)
    @ApiImplicitParam(name = "financeId", value = "金融单id", required = true, dataType = "int", paramType = "path")
    public Result getFinanceOrderSupervisorInfoByFinanceIdMethod(@PathVariable("financeId") Long financeId) {
        return orderService.findSupervisorInfoByFinanceId(financeId, EnumFinanceAttachment.SupervisorAuditAttachment, Arrays.asList(new EnumFinanceAttachment[] {EnumFinanceAttachment.SalesmanSupplyAttachment_Supervisor}), adminSession.getUser().getCompanyId());
    }

    @RequestMapping(value = "/finance/{financeId}/riskmanager", method = RequestMethod.GET)
    @ApiOperation(value = "通过 金融单id 查看风控人员填写表单详细信息", notes = "通过 金融单id 查看风控人员填写表单详细信息", response = FinanceOrderRiskManagerInfoObject.class)
    @ApiImplicitParam(name = "financeId", value = "金融单id", required = true, dataType = "int", paramType = "path")
    public Result getFinanceOrderRiskManagerInfoByFinanceIdMethod(@PathVariable("financeId") Long financeId) {
        return orderService.findRiskManagerInfoByFinanceId(financeId, EnumFinanceAttachment.RiskManagerAuditAttachment, Arrays.asList(new EnumFinanceAttachment[] {EnumFinanceAttachment.SalesmanSupplyAttachment_RiskManager}), adminSession.getUser().getCompanyId());
    }

    @RequestMapping(value = "/finance/{financeId}/riskmanager/contract/{type}")
    @ApiOperation(value = "通过 金融单id, type 查看 合同", notes = "通过 金融单id 查看 合同详细内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "financeId", value = "金融单id", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "type", value = "类型", required = true, dataType = "int", paramType = "path")
    })
    public Result getFinanceOrderRiskManagerContractByFinanceIdAndTypeTypeMethod(@PathVariable("financeId") Long financeId,
                                                                                 @PathVariable("type") int type) {
        return orderService.findFinanceOrderRiskManagerContractByFinanceIdAndType(financeId, type, adminSession.getUser().getCompanyId());
    }

    @RequestMapping(value = "/finance/{financeId}/tasks", method = RequestMethod.GET)
    @ApiOperation(value = "通过 金融单id 获取所有任务步骤列表", notes = "通过 金融单id 获取所有任务步骤列表")
    @ApiImplicitParam(name = "financeId", value = "金融单id", required = true, dataType = "int", paramType = "path")
    public Result getAllTasksByFinanceIdMethod(@PathVariable(value = "financeId") Long financeId) {
        FinanceOrder financeOrder = orderRepository.findOne(financeId);
        if (financeOrder == null) return Result.error(EnumAdminFinanceError.此金融单不存在.toString());
        if (adminSession.getUser().getCompanyId().longValue() != 0 && financeOrder.getRiskCompanyId().longValue() != adminSession.getUser().getCompanyId()) return Result.error(EnumAdminFinanceError.你没有权限查看此金融单.toString());
        return Result.success().setData(methodService.changeHistoryTaskObject(historyService.createHistoricTaskInstanceQuery().processInstanceBusinessKey(String.valueOf(financeId)).orderByTaskCreateTime().asc().list()));
    }

    @RequestMapping(value = "/finance/{financeId}/files", method = RequestMethod.GET)
    @ApiOperation(value = "通过 金融单id 获取该流程所有附件列表", notes = "通过 金融单id 获取 该流程所有附件列表")
    @ApiImplicitParam(name = "financeId", value = "金融单id", required = true, dataType = "int", paramType = "path")
    public Result getAllFilesByFinancIdMethod(@PathVariable(value = "financeId") Long financeId) {
        HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(String.valueOf(financeId)).singleResult();
        if (processInstance == null) throw new BusinessException(EnumCommonError.Admin_System_Error);
        List<AttachmentObject> fileList = DozerUtils.copy(taskService.getProcessInstanceAttachments(processInstance.getId()), AttachmentObject.class);
        return Result.success().setData(fileList);
    }

    @RequestMapping(value = "/finance/process/{processInstanceId}/image", method = RequestMethod.GET)
    @ApiOperation(value = "通过流程实例id获取流程图", notes = "通过流程实例id获取流程图")
    @ApiImplicitParam(name = "processInstanceId", value = "流程实例id", required = true, dataType = "string", paramType = "path")
    public void getProcessDiagramMethod(@PathVariable("processInstanceId") String processInstanceId, HttpServletResponse response) throws IOException {
        response.setContentType("image/gif");
        OutputStream out = response.getOutputStream();
        HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if (processInstance == null) throw new BusinessException(EnumCommonError.Admin_System_Error);
        Long riskCompanyId = orderRepository.findRiskCompanyIdById(Long.valueOf(processInstance.getBusinessKey()));
        if (adminSession.getUser().getCompanyId().longValue() != 0 && riskCompanyId.longValue() != adminSession.getUser().getCompanyId().longValue()) throw new BusinessException(EnumAdminFinanceError.你没有权限查看此流程.toString());
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


