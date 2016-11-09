package com.yimei.finance.service.site.finance;

import com.yimei.finance.entity.admin.finance.FinanceOrder;
import com.yimei.finance.entity.admin.finance.FinanceOrderContract;
import com.yimei.finance.entity.site.user.User;
import com.yimei.finance.exception.BusinessException;
import com.yimei.finance.repository.admin.finance.FinanceOrderContractRepository;
import com.yimei.finance.repository.admin.finance.FinanceOrderRepository;
import com.yimei.finance.representation.admin.finance.enums.EnumAdminFinanceError;
import com.yimei.finance.representation.admin.finance.enums.EnumFinanceAttachment;
import com.yimei.finance.representation.admin.finance.enums.EnumFinanceOrderType;
import com.yimei.finance.representation.admin.finance.enums.EnumFinanceStatus;
import com.yimei.finance.representation.admin.finance.object.FinanceOrderContractObject;
import com.yimei.finance.representation.admin.finance.object.FinanceOrderObject;
import com.yimei.finance.representation.common.enums.EnumCommonError;
import com.yimei.finance.representation.common.file.AttachmentObject;
import com.yimei.finance.representation.common.result.ErrorMessage;
import com.yimei.finance.representation.common.result.MapObject;
import com.yimei.finance.representation.common.result.Page;
import com.yimei.finance.representation.common.result.Result;
import com.yimei.finance.representation.site.finance.FinanceContractSearch;
import com.yimei.finance.representation.site.finance.FinanceOrderResult;
import com.yimei.finance.representation.site.finance.FinanceOrderSearch;
import com.yimei.finance.service.common.tools.NumberServiceImpl;
import com.yimei.finance.utils.DozerUtils;
import com.yimei.finance.utils.Where;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Attachment;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SiteFinanceOrderServiceImpl {
    @Autowired
    private FinanceOrderRepository financeOrderRepository;
    @Autowired
    private NumberServiceImpl numberService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private RuntimeService runtimeService;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private FinanceOrderContractRepository orderContractRepository;

    /**
     * 客户申请金融单
     */
    public Result customerApplyFinanceOrder(FinanceOrderObject financeOrderObject, User sessionUser) {
        FinanceOrder financeOrder = new FinanceOrder();
        financeOrder.setApplyType(financeOrderObject.getApplyType());
        if (!sessionUser.getVerifystatus().equals("审核通过")) return Result.error(ErrorMessage.User_Company_Not_AuditSuccess);
        List<FinanceOrder> financeOrderList = financeOrderRepository.findByUserIdAndCreateTimeGreaterThan(Long.valueOf(sessionUser.getId()), java.sql.Date.valueOf(LocalDate.now()));
        if (financeOrderList != null && financeOrderList.size() >= 2) return Result.error(ErrorMessage.User_Finance_Times);
        financeOrder.setApplyType(financeOrder.getApplyType());
        financeOrder.setSourceId(numberService.getNextCode("JR"));
        financeOrder.setUserId(Long.valueOf(sessionUser.getId()));
        financeOrder.setApplyUserName(sessionUser.getNickname());
        financeOrder.setApplyUserPhone(sessionUser.getSecurephone());
        financeOrder.setApplyCompanyId(Long.valueOf(sessionUser.getCompanyId()));
        financeOrder.setApplyCompanyName(sessionUser.getCompanyName());
        financeOrder.setCreateManId(String.valueOf(sessionUser.getId()));
        financeOrder.setLastUpdateManId(String.valueOf(sessionUser.getId()));
        financeOrder.setCreateTime(new Date());
        financeOrder.setLastUpdateTime(new Date());
        financeOrder.setEndTime(null);
        financeOrder.setApproveStateId(EnumFinanceStatus.WaitForAudit.id);
        financeOrder.setApproveState(EnumFinanceStatus.WaitForAudit.name);
        financeOrderRepository.save(financeOrder);
        financeOrder = financeOrderRepository.findBySourceId(financeOrder.getSourceId());
        identityService.setAuthenticatedUserId(String.valueOf(sessionUser.getId()));
        if (financeOrder.getApplyType().equals(EnumFinanceOrderType.MYR.toString())) {
            runtimeService.startProcessInstanceByKey("financingMYRWorkFlow", String.valueOf(financeOrder.getId()));
        } else if (financeOrder.getApplyType().equals(EnumFinanceOrderType.MYG.toString())) {
            runtimeService.startProcessInstanceByKey("financingMYGWorkFlow", String.valueOf(financeOrder.getId()));
        } else if (financeOrder.getApplyType().equals(EnumFinanceOrderType.MYD.toString())) {
            runtimeService.startProcessInstanceByKey("financingMYDWorkFlow", String.valueOf(financeOrder.getId()));
        } else {
            throw new BusinessException(EnumCommonError.Admin_System_Error);
        }
        return Result.success().setData(financeOrderRepository.findBySourceId(financeOrder.getSourceId()));
    }

    /**
     * 前台查询金融单
     */
    public Result getFinanceOrderBySelect(Long userId, Long companyId, FinanceOrderSearch order, Page page) {
        String hql = " select o from FinanceOrder o where (o.userId=:userId or o.applyCompanyId=:companyId) ";
        if (order != null) {
            if (order.getStartDate() != null && order.getEndDate() != null) {
                hql += " and o.createTime between :startDate and :endDate ";
            }
            if (order.getApproveStateId() != 0) {
                hql += " and o.approveStateId=:approveStateId ";
            }
            if (!StringUtils.isEmpty(order.getSourceId())) {
                hql += " and o.sourceId like :sourceId ";
            }
            if (!StringUtils.isEmpty(order.getApplyType())) {
                hql += " and o.applyType=:applyType ";
            }
        }
        hql += " order by o.id desc ";
        TypedQuery<FinanceOrder> query = entityManager.createQuery(hql, FinanceOrder.class);
        query.setParameter("userId", userId);
        query.setParameter("companyId", companyId);
        if (order != null) {
            if (order.getStartDate() != null && order.getEndDate() != null) {
                query.setParameter("startDate", java.sql.Date.valueOf(order.getStartDate()));
                query.setParameter("endDate", java.sql.Date.valueOf(order.getEndDate().plusDays(1)));
            }
            if (order.getApproveStateId() != 0) {
                query.setParameter("approveStateId", order.getApproveStateId());
            }
            if (!StringUtils.isEmpty(order.getSourceId())) {
                query.setParameter("sourceId", Where.$like$(order.getSourceId()));
            }
            if (!StringUtils.isEmpty(order.getApplyType())) {
                query.setParameter("applyType", order.getApplyType());
            }
        }
        List<FinanceOrder> totalList = query.getResultList();
        page.setTotal(Long.valueOf(totalList.size()));
        int toIndex = page.getPage() * page.getCount() < totalList.size() ? page.getPage() * page.getCount() : totalList.size();
        List<FinanceOrderResult> financeOrderList = DozerUtils.copy(totalList.subList(page.getOffset(), toIndex), FinanceOrderResult.class);
        return Result.success().setData(financeOrderList).setMeta(page);
    }

    /**
     * 客户根据 id 查询 金融申请单
     */
    public Result findByIdAndUserIdOrCompanyId(Long id, Long sessionUserId, Long sessionCompanyId) {
        FinanceOrder financeOrder = financeOrderRepository.findByIdAndUserIdOrCompanyId(id, sessionUserId, sessionCompanyId);
        if (financeOrder == null) return Result.error(EnumAdminFinanceError.此金融单不存在.toString());
        FinanceOrderObject financeOrderObject = DozerUtils.copy(financeOrder, FinanceOrderObject.class);
        financeOrderObject.setAttachmentList1(getAttachmentByFinanceIdTypeOnce(id, EnumFinanceAttachment.OnlineTraderAuditAttachment));
        return Result.success().setData(financeOrderObject);
    }

    public List<AttachmentObject> getAttachmentByFinanceIdTypeOnce(Long financeId, EnumFinanceAttachment attachmentType) {
        List<AttachmentObject> attachmentList = new ArrayList<>();
        List<HistoricTaskInstance> taskList = historyService.createHistoricTaskInstanceQuery().processInstanceBusinessKey(String.valueOf(financeId)).taskDefinitionKey(attachmentType.type).orderByTaskCreateTime().desc().list();
        if (taskList == null || taskList.size() == 0) throw new BusinessException(EnumCommonError.Admin_System_Error);
        HistoricTaskInstance task = taskList.get(0);
        List<Attachment> attachments = taskService.getTaskAttachments(task.getId());
        if (attachments != null && attachments.size() != 0) {
            attachmentList.addAll(DozerUtils.copy(attachments, AttachmentObject.class));
        }
        return attachmentList;
    }

    /**
     * 金融申请单状态list
     */
    public Result financeOrderStatusList() {
        List<MapObject> mapList = new ArrayList<>();
        for (EnumFinanceStatus status : EnumFinanceStatus.values()) {
            mapList.add(new MapObject(String.valueOf(status.id), status.name));
        }
        return Result.success().setData(mapList);
    }

    /**
     * 客户导出金融单
     */
    public void customerExportFinanceOrder(Long sessionUserId, Long sessionCompanyId, HttpServletResponse response, HttpServletRequest request) throws IOException {
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
        List<FinanceOrderObject> financeOrderList = DozerUtils.copy(financeOrderRepository.findByUserIdOrApplyCompanyId(sessionUserId, sessionCompanyId), FinanceOrderObject.class);
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

    public Result getFinanceOrderContractObject(Long financeId, Integer type, Long sessionUserId, Long sessionCompanyId) {
        FinanceOrder financeOrder = financeOrderRepository.findByIdAndUserIdOrCompanyId(financeId, sessionUserId, sessionCompanyId);
        if (financeOrder == null) return Result.error(EnumAdminFinanceError.此金融单不存在.toString());
        FinanceOrderContract financeOrderContract = orderContractRepository.findByFinanceIdAndType(financeId, type);
        if (financeOrderContract == null) return Result.error(EnumAdminFinanceError.此合同不存在.toString());
        FinanceOrderContractObject financeOrderContractObject = changeFinanceOrderContractObject(financeOrderContract);
        return Result.success().setData(financeOrderContractObject);
    }

    public Result findFinanceContractByFinanceIdUserIdCompanyId(Long financeId, Long sessionUserId, Long sessionCompanyId) {
        FinanceOrderContract financeOrderContract = orderContractRepository.findByFinanceIdAndApplyUserIdAndApplyCompanyId(financeId, sessionUserId, sessionCompanyId);
        if (financeOrderContract == null) return Result.error(EnumAdminFinanceError.此合同不存在.toString());
        if (financeOrderRepository.findByIdAndUserIdOrCompanyId(financeOrderContract.getFinanceId(), sessionUserId, sessionCompanyId) == null)
            return Result.error(EnumAdminFinanceError.你没有权限查看此合同.toString());
        FinanceOrderContractObject financeOrderContractObject = changeFinanceOrderContractObject(financeOrderContract);
        return Result.success().setData(financeOrderContractObject);
    }

    public Result getFinanceContractBySelect(Long sessionUserId, Long sessionCompanyId, FinanceContractSearch contractSearch, Page page) {
        return null;
    }

    private FinanceOrderContractObject changeFinanceOrderContractObject(FinanceOrderContract financeOrderContract) {
        if (financeOrderContract == null) return null;
        FinanceOrderContractObject financeOrderContractObject = DozerUtils.copy(financeOrderContract, FinanceOrderContractObject.class);
        FinanceOrder financeOrder = financeOrderRepository.findOne(financeOrderContract.getId());
        if (financeOrder == null) throw new BusinessException(EnumCommonError.Admin_System_Error);
        financeOrderContractObject.setFinanceType(financeOrder.getApplyType());
        financeOrderContractObject.setFinanceSourceId(financeOrder.getSourceId());
        return financeOrderContractObject;
    }

    public void downFinanceOrderContractByFinanceIdAndContractType(Long financeId, int type) {
        FinanceOrderContract financeOrderContract = orderContractRepository.findByFinanceIdAndType(financeId, type);

    }
}
