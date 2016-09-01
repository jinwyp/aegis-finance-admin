package com.yimei.finance.service.admin.finance;

import com.yimei.finance.entity.admin.finance.*;
import com.yimei.finance.repository.admin.finance.*;
import com.yimei.finance.representation.admin.finance.EnumAdminFinanceError;
import com.yimei.finance.representation.admin.finance.EnumFinanceAttachment;
import com.yimei.finance.representation.admin.finance.EnumFinanceStatus;
import com.yimei.finance.representation.common.enums.EnumCommonError;
import com.yimei.finance.representation.common.result.Page;
import com.yimei.finance.representation.common.result.Result;
import com.yimei.finance.representation.site.user.FinanceOrderSearch;
import com.yimei.finance.utils.DozerUtils;
import com.yimei.finance.utils.Where;
import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service("financeOrderService")
public class FinanceOrderServiceImpl {
    @Autowired
    private FinanceOrderRepository orderRepository;
    @Autowired
    private FinanceOrderSalesmanRepository salesmanRepository;
    @Autowired
    private FinanceOrderInvestigatorRepository investigatorRepository;
    @Autowired
    private FinanceOrderSupervisorRepository supervisorRepository;
    @Autowired
    private FinanceOrderRiskRepository riskRepository;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private FinanceFlowMethodServiceImpl methodService;

    /**
     * 交易员补充材料
     */
    @Transactional
    public void updateFinanceOrderByOnlineTrader(FinanceOrder financeOrder) {
        FinanceOrder order = orderRepository.findOne(financeOrder.getId());
        order.setFinancingAmount(financeOrder.getFinancingAmount());
        order.setExpectDate(financeOrder.getExpectDate());
        order.setBusinessAmount(financeOrder.getBusinessAmount());
        order.setTransportMode(financeOrder.getTransportMode());
        order.setProcurementPrice(financeOrder.getProcurementPrice());
        order.setContractor(financeOrder.getContractor());
        order.setDownstreamContractor(financeOrder.getDownstreamContractor());
        order.setTerminalServer(financeOrder.getTerminalServer());
        order.setComments(financeOrder.getComments());
        order.setCoalQuantityIndex(financeOrder.getCoalQuantityIndex());
        order.setApproveStateId(EnumFinanceStatus.Auditing.id);
        order.setApproveState(EnumFinanceStatus.Auditing.name);
        orderRepository.save(order);
    }

    /**
     * 查询金融单
     */
    public Result getFinanceOrderBySelect(int userId, FinanceOrderSearch order, Page page) {
        String hql = " select o from FinanceOrder o where o.userId=:userId ";
        if (order != null) {
            if (!StringUtils.isEmpty(order.getStartDate()) && !StringUtils.isEmpty(order.getEndDate())) {
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
        if (order != null) {
            if (!StringUtils.isEmpty(order.getStartDate()) && !StringUtils.isEmpty(order.getEndDate())) {
                query.setParameter("startDate", Date.valueOf(order.getStartDate()));
                query.setParameter("endDate", Date.valueOf(LocalDate.parse(order.getEndDate()).plusDays(1)));
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
        List<FinanceOrder> financeOrderList = totalList.subList(page.getOffset(), toIndex);
        return Result.success().setData(financeOrderList).setMeta(page);
    }

    public List<AttachmentObject> getAttachmentByProcessInstanceIdType(List<HistoryTaskObject> taskList, List<EnumFinanceAttachment> typeList) {
        List<AttachmentObject> attachmentList = new ArrayList<>();
        List<String> eventList = new ArrayList<>();
        for (EnumFinanceAttachment attachment : typeList) {
            eventList.add(attachment.type);
        }
        for (HistoryTaskObject task : taskList) {
            if (eventList.contains(task.getTaskDefinitionKey())) {
                attachmentList.addAll(DozerUtils.copy(taskService.getTaskAttachments(task.getId()), AttachmentObject.class));
            }
        }
        return attachmentList;
    }

    public List<HistoryTaskObject> getAllTaskListByProcessInstanceId(String processInstanceId) {
        return (List<HistoryTaskObject>) methodService.changeHistoryTaskObject(historyService.createHistoricTaskInstanceQuery().taskId(processInstanceId).orderByTaskCreateTime().list()).getData();
    }

    public Result findById(Long id, List<EnumFinanceAttachment> typeList) {
        FinanceOrder financeOrder = orderRepository.findOne(id);
        if (financeOrder == null) return Result.error(EnumAdminFinanceError.此金融单不存在.toString());
        HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(String.valueOf(id)).singleResult();
        if (processInstance == null) return Result.error(EnumCommonError.Admin_System_Error);
        financeOrder.setTaskList(getAllTaskListByProcessInstanceId(processInstance.getId()));
        financeOrder.setAttachmentList(getAttachmentByProcessInstanceIdType(financeOrder.getTaskList(), typeList));
        return Result.success().setData(financeOrder);
    }

    /**
     * 保存,更新 业务员 填写的信息
     */
    public void saveFinanceOrderSalesmanInfo(FinanceOrderSalesmanInfo salesmanInfo) {
        FinanceOrderSalesmanInfo salesmanOrder = salesmanRepository.findByFinanceId(salesmanInfo.getFinanceId());
        if (salesmanOrder != null) {
            salesmanInfo.setId(salesmanOrder.getId());
        }
        salesmanRepository.save(salesmanInfo);
    }

    /**
     * 保存,更新 尽调员 填写的信息
     */
    public void saveFinanceOrderInvestigatorInfo(FinanceOrderInvestigatorInfo investigatorInfo) {
        FinanceOrderInvestigatorInfo investigatorOrder = investigatorRepository.findByFinanceId(investigatorInfo.getFinanceId());
        if (investigatorOrder != null) {
            investigatorInfo.setId(investigatorOrder.getId());
        }
        investigatorRepository.save(investigatorInfo);
    }

    /**
     * 保存,更新 监管员 填写的信息
     */
    public void saveFinanceOrderSupervisorInfo(FinanceOrderSupervisorInfo supervisorInfo) {
        FinanceOrderSupervisorInfo supervisorOrder = supervisorRepository.findByFinanceId(supervisorInfo.getFinanceId());
        if (supervisorInfo != null) {
            supervisorInfo.setId(supervisorOrder.getId());
        }
        supervisorRepository.save(supervisorInfo);
    }

    /**
     * 保存,更新 风控 填写的信息
     */
    public void saveFinanceOrderRiskManagerInfo(FinanceOrderRiskManagerInfo riskManagerInfo) {
        FinanceOrderRiskManagerInfo riskManagerOrder = riskRepository.findByFinanceId(riskManagerInfo.getFinanceId());
        if (riskManagerOrder != null) {
            riskManagerInfo.setId(riskManagerOrder.getId());
        }
        riskRepository.save(riskManagerInfo);
    }


}
