package com.yimei.finance.controllers.site.page;

import com.yimei.finance.config.session.UserSession;
import com.yimei.finance.ext.annotations.LoginRequired;
import com.yimei.finance.repository.admin.finance.FinanceOrderRepository;
import com.yimei.finance.representation.admin.finance.object.FinanceOrderObject;
import com.yimei.finance.representation.common.result.MapObject;
import com.yimei.finance.utils.DozerUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by liuxinjie on 16/7/29.
 */
@Api(tags = {"site-page"})
@Controller
public class IndexController {

    private Environment env;

    @Autowired
    public IndexController(Environment env) {
        this.env = env;
    }

    @Autowired
    private FinanceOrderRepository financeOrderRepository;

    @Autowired
    private UserSession userSession;

    /**
     * 网站供应链金融 页面
     */
    @ApiOperation(value = "网站供应链金融 页面", notes = "网站页面需要登录")
    @RequestMapping(value = {"/finance","/"}, method = RequestMethod.GET)
    public String financeIndex(Model model) {
        return "site/index";
    }


    /**
    * 网站供应链金融 - 个人中心 - 我的申请
    */
    @ApiOperation(value = "网站供应链金融 - 个人中心 - 我的申请", notes = "供应链金融 我的融资 申请列表")
    @LoginRequired
    @RequestMapping(value = "/finance/user/financing", method = RequestMethod.GET)
    public String personCenterFinancingList(Model model) {
        return "site/user/financingList";
    }


    /**
     * 网站供应链金融 - 个人中心 - 我的申请 - 业务详情
     */
    @ApiOperation(value = "网站供应链金融 - 个人中心 - 我的申请 - 业务详情", notes = "供应链金融 我的融资 申请详情页面")
    @LoginRequired
    @RequestMapping(value = "/finance/user/financing/{id}", method = RequestMethod.GET)
    public String personCenterFinancingRequest(@PathVariable("id") int id, Model model) {
        return "site/user/financingInfo";
    }


    /**
     * 网站供应链金融 - 个人中心 - 我的申请
     */
    @LoginRequired
    @RequestMapping(value = "/finance/user/financing/excel", method = RequestMethod.GET)
    @ApiOperation(value = "网站供应链金融 - 个人中心 - 我的申请 - 导出金融申请单", notes = "供应链金融 我的融资 申请列表 导出金融申请单", response = MapObject.class, responseContainer = "List")
    public void exportFinancingOrderToExcel(HttpServletResponse response,
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
        String[] excelHeader = {"序号", "业务编号", "业务类型", "申请时间", "拟融资总金额（万元）", "使用天数", "审核状态"};
        for (int i=0; i <excelHeader.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(excelHeader[i]);
            cell.setCellStyle(style);
        }
        SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<FinanceOrderObject> financeOrderList = DozerUtils.copy(financeOrderRepository.findByUserId(userSession.getUser().getId()), FinanceOrderObject.class);
        for (int i = 0; i < financeOrderList.size(); i++) {
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);
            sheet.autoSizeColumn(5);
            sheet.autoSizeColumn(6);
            FinanceOrderObject order = financeOrderList.get(i);
            row = sheet.createRow(i+1);
            row.createCell(0).setCellValue(String.valueOf(i + 1));
            row.createCell(1).setCellValue(String.valueOf(order.getSourceId()));
            row.createCell(2).setCellValue(String.valueOf(order.getApplyTypeName()));
            if (order.getCreateTime() != null && !StringUtils.isEmpty(String.valueOf(order.getCreateTime()))) {
                row.createCell(3).setCellValue(String.valueOf(myFmt.format(order.getCreateTime())));
            }
            row.createCell(4).setCellValue(String.valueOf(order.getFinancingAmount() == null ? "/" : order.getFinancingAmount()));
            row.createCell(5).setCellValue(String.valueOf(order.getExpectDate() == 0 ? "/" : order.getExpectDate()));
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
    }

}
