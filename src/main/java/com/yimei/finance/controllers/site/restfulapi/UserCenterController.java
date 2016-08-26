package com.yimei.finance.controllers.site.restfulapi;

import com.yimei.finance.config.session.UserSession;
import com.yimei.finance.entity.admin.finance.EnumAdminFinanceError;
import com.yimei.finance.entity.admin.finance.EnumFinanceOrderType;
import com.yimei.finance.entity.admin.finance.EnumFinanceStatus;
import com.yimei.finance.entity.admin.finance.FinanceOrder;
import com.yimei.finance.entity.admin.user.EnumSpecialGroup;
import com.yimei.finance.entity.common.enums.EnumCommonError;
import com.yimei.finance.entity.common.result.MapObject;
import com.yimei.finance.entity.common.result.Page;
import com.yimei.finance.entity.common.result.Result;
import com.yimei.finance.ext.annotations.LoginRequired;
import com.yimei.finance.repository.admin.finance.FinanceOrderRepository;
import com.yimei.finance.service.admin.finance.FinanceOrderServiceImpl;
import com.yimei.finance.service.common.NumberServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.IdentityLinkType;
import org.activiti.engine.task.Task;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JinWYP on 8/15/16.
 */
@RequestMapping("/api/financing")
@Api(tags = {"site-api"})
@RestController("siteUserCenterController")
public class UserCenterController {
    @Autowired
    private FinanceOrderRepository financeOrderRepository;
    @Autowired
    private UserSession userSession;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private NumberServiceImpl numberService;
    @Autowired
    private FinanceOrderServiceImpl financeOrderService;

    /**
    * 供应链金融 - 发起融资申请
    */
    @ApiOperation(value = "供应链金融 - 发起融资申请", notes = "发起融资申请, 需要用户事先登录, 并完善企业信息", response = FinanceOrder.class)
    @LoginRequired
    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    public Result requestFinancingOrder(@ApiParam(name = "financeOrder", value = "只需填写applyType 字段即可", required = true) @Valid @RequestBody FinanceOrder financeOrder) {
        System.out.println("Order Type:" + financeOrder.getApplyType());
        financeOrder.setApplyType(financeOrder.getApplyType());
        financeOrder.setSourceId(numberService.getNextCode("JR"));
        financeOrder.setUserId(userSession.getUser().getId());
        financeOrder.setApplyCompanyName(userSession.getUser().getCompanyName());
//        financeOrder.setUserId(1);
//        financeOrder.setApplyDateTime(new Date());
        financeOrder.setApproveStateId(EnumFinanceStatus.WaitForAudit.id);
        financeOrder.setApproveState(EnumFinanceStatus.WaitForAudit.name);
        financeOrderRepository.save(financeOrder);
        financeOrder = financeOrderRepository.findBySourceId(financeOrder.getSourceId());
        if (financeOrder.getApplyType().equals(EnumFinanceOrderType.MYR.toString())) {
            runtimeService.startProcessInstanceByKey("financingMYRWorkFlow", String.valueOf(financeOrder.getId()));
            Task task = taskService.createTaskQuery().processInstanceBusinessKey(String.valueOf(financeOrder.getId())).active().singleResult();
            taskService.addGroupIdentityLink(task.getId(), EnumSpecialGroup.ManageOnlineTraderGroup.id, IdentityLinkType.CANDIDATE);
        } else if (financeOrder.getApplyType().equals(EnumFinanceOrderType.MYG.toString())) {

        } else if (financeOrder.getApplyType().equals(EnumFinanceOrderType.MYD.toString())) {

        } else {
            return Result.error(EnumCommonError.Admin_System_Error);
        }
        return Result.success().setData(financeOrderRepository.findBySourceId(financeOrder.getSourceId()));
    }

    /**
     * 供应链金融 - 用户中心 - 获取融资申请列表
     */
    @ApiOperation(value = "融资申请列表", notes = "用户查询融资申请列表", response = FinanceOrder.class, responseContainer = "List")
    @LoginRequired
    @RequestMapping(value = "/apply", method = RequestMethod.GET)
//    public Result getFinancingApplyInfoList(@RequestBody CombineObject object) {
    public Result getFinancingApplyInfoList(Page page) {
//        return financeOrderService.getFinanceOrderBySelect(1, object.financeOrderSearch, object.page);
        return Result.success().setData(financeOrderRepository.findByUserId(userSession.getUser().getId()));
    }

    /**
     * 供应链金融 - 用户中心 - 获取融资申请详细信息
     */
    @ApiOperation(value = "根据 id 查看金融申请单", notes = "根据 金融申请单id 查看金融申请单", response = FinanceOrder.class)
    @ApiImplicitParam(name = "id", value = "金融申请单id", required = true, dataType = "Long", paramType = "path")
    @LoginRequired
    @RequestMapping(value = "/apply/{id}", method = RequestMethod.GET)
    public Result getFinancingApplyInfo(@PathVariable("id") Long id) {
        FinanceOrder financeOrder = financeOrderRepository.findByIdAndUserId(id, userSession.getUser().getId());
        if (financeOrder == null) return Result.error(EnumAdminFinanceError.此金融单不存在.toString());
        return Result.success().setData(financeOrder);
    }


    @RequestMapping(value = "/status", method = RequestMethod.GET)
    @ApiOperation(value = "融资申请状态列表", notes = "融资申请状态列表", response = MapObject.class, responseContainer = "List")
    public Result findFinanceStatusList() {
        List<MapObject> mapList = new ArrayList<>();
        for (EnumFinanceStatus status : EnumFinanceStatus.values()) {
            mapList.add(new MapObject(String.valueOf(status.id), status.name));
        }
        return Result.success().setData(mapList);
    }

//    @LoginRequired
    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    @ApiOperation(value = "导出金融申请单", notes = "导出金融申请单", response = MapObject.class, responseContainer = "List")
    public Result exportFinancingOrderToExcel(HttpServletResponse response,
                                              HttpServletRequest request) throws IOException {
        HSSFWorkbook wb = new HSSFWorkbook();
        String filename = "金融申请单列表_" + LocalDate.now();
        HSSFSheet sheet = wb.createSheet(filename);
        HSSFRow row = sheet.createRow(0);
        CellStyle style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        sheet.setVerticallyCenter(true);
        sheet.setHorizontallyCenter(true);
        sheet.setColumnWidth(0, 1200);
        sheet.setColumnWidth(1, 6000);
        sheet.setColumnWidth(2, 5000);
        sheet.setColumnWidth(3, 5000);
        sheet.setColumnWidth(4, 3000);
        sheet.setColumnWidth(5, 5000);
        sheet.setColumnWidth(6, 6000);
        String[] excelHeader = {"序号", "业务编号", "业务类型", "申请时间", "拟融资总金额", "使用天数", "审核状态"};
        for (int i=0; i <excelHeader.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(excelHeader[i]);
            cell.setCellStyle(style);
        }
        SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        List<FinanceOrder> financeOrderList = financeOrderRepository.findByUserId(userSession.getUser().getId());
        List<FinanceOrder> financeOrderList = financeOrderRepository.findByUserId(1);
        for (int i = 0; i < financeOrderList.size(); i++) {
            FinanceOrder order = financeOrderList.get(i);
            row = sheet.createRow(i+1);
            row.createCell(0).setCellValue(String.valueOf(i + 1));
            row.createCell(1).setCellValue(String.valueOf(order.getSourceId()));
            row.createCell(2).setCellValue(String.valueOf(order.getApplyTypeName()));
            if (order.getApplyDateTime() != null && !StringUtils.isEmpty(String.valueOf(order.getApplyDateTime()))) {
                row.createCell(3).setCellValue(String.valueOf(myFmt.format(order.getApplyDateTime())));
            }
            row.createCell(4).setCellValue(String.valueOf(order.getFinancingAmount()));
            row.createCell(5).setCellValue(String.valueOf(order.getExpectDate()));
            row.createCell(6).setCellValue(String.valueOf(order.getApproveState()));
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/x-download");
        filename += LocalDate.now() + ".xls";
        if(request.getHeader("user-agent").toLowerCase().contains("firefox")) {
            filename =  new String(filename.getBytes("UTF-8"), "ISO-8859-1");
        } else {
            filename = URLEncoder.encode(filename, "UTF-8");
        }
        response.addHeader("Content-Disposition", "attachment;filename="+ filename);
        OutputStream out = response.getOutputStream();
        wb.write(out);
        out.close();
        return Result.success();
    }

}




