package com.yimei.finance.service.admin.finance;

import com.yimei.finance.entity.admin.finance.*;
import com.yimei.finance.repository.admin.finance.*;
import com.yimei.finance.representation.admin.finance.*;
import com.yimei.finance.representation.common.result.Page;
import com.yimei.finance.representation.common.result.Result;
import com.yimei.finance.representation.site.user.FinanceOrderSearch;
import com.yimei.finance.utils.DozerUtils;
import com.yimei.finance.utils.Where;
import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Attachment;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
                query.setParameter("startDate", order.getStartDate());
                query.setParameter("endDate", java.sql.Date.valueOf(LocalDate.parse(order.getEndDate()).plusDays(1)));
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

    public List<AttachmentObject> getAttachmentByFinanceIdType(Long financeId, List<EnumFinanceAttachment> typeList) {
        List<AttachmentObject> attachmentList = new ArrayList<>();
        for (EnumFinanceAttachment attachment: typeList) {
            List<Task> tasks = taskService.createTaskQuery().processInstanceBusinessKey(String.valueOf(financeId)).taskDefinitionKey(attachment.type).list();
            for (Task t : tasks) {
                List<Attachment> attachments = taskService.getTaskAttachments(t.getId());
                if (attachments != null && attachments.size() != 0) {
                    attachmentList.addAll(DozerUtils.copy(taskService.getTaskAttachments(t.getId()), AttachmentObject.class));
                }
            }
        }
        return attachmentList;
    }

    public List<HistoryTaskObject> getAllTaskListByFinanceId(Long financeId) {
        return (List<HistoryTaskObject>) methodService.changeHistoryTaskObject(historyService.createHistoricTaskInstanceQuery().processInstanceBusinessKey(String.valueOf(financeId)).orderByTaskCreateTime().asc().list()).getData();
    }

    /**
     * 获取金融申请单详细
     */
    public Result findById(Long id, List<EnumFinanceAttachment> typeList) {
        FinanceOrderObject financeOrderObject = DozerUtils.copy(orderRepository.findOne(id), FinanceOrderObject.class);
        if (financeOrderObject == null) return Result.error(EnumAdminFinanceError.此金融单不存在.toString());
        financeOrderObject.setAttachmentList(getAttachmentByFinanceIdType(id, typeList));
        return Result.success().setData(financeOrderObject);
    }

    /**
     * 获取业务员填写信息详细
     */
    public Result findSalesmanInfoByFinanceId(Long financeId, List<EnumFinanceAttachment> typeList) {
        FinanceOrderSalesmanInfoObject salesmanInfoObject = DozerUtils.copy(salesmanRepository.findByFinanceId(financeId), FinanceOrderSalesmanInfoObject.class);
        if (salesmanInfoObject != null) {
            salesmanInfoObject.setAttachmentList(getAttachmentByFinanceIdType(financeId, typeList));
        }
        return Result.success().setData(salesmanInfoObject);
    }

    /**
     * 获取尽调员填写信息详细
     */
    public Result findInvestigatorInfoByFinanceId(Long financeId, List<EnumFinanceAttachment> typeList) {
        FinanceOrderInvestigatorInfoObject investigatorInfoObject = DozerUtils.copy(investigatorRepository.findByFinanceId(financeId), FinanceOrderInvestigatorInfoObject.class);
        if (investigatorInfoObject != null) {
            investigatorInfoObject.setAttachmentList(getAttachmentByFinanceIdType(financeId, typeList));
        }
        return Result.success().setData(investigatorInfoObject);
    }

    /**
     * 获取监管员填写信息详细
     */
    public Result findSupervisorInfoByFinanceId(Long financeId, List<EnumFinanceAttachment> typeList) {
        FinanceOrderSupervisorInfoObject supervisorInfoObject = DozerUtils.copy(supervisorRepository.findByFinanceId(financeId), FinanceOrderSupervisorInfoObject.class);
        if (supervisorInfoObject != null) {
            supervisorInfoObject.setAttachmentList(getAttachmentByFinanceIdType(financeId, typeList));
        }
        return Result.success().setData(supervisorInfoObject);
    }

    /**
     * 获取风控人员填写信息详细
     */
    public Result findRiskManagerInfoByFinanceId(Long financeId, List<EnumFinanceAttachment> typeList) {
        FinanceOrderRiskManagerInfoObject riskManagerInfoObject = DozerUtils.copy(riskRepository.findByFinanceId(financeId), FinanceOrderRiskManagerInfoObject.class);
        if (riskManagerInfoObject != null) {
            riskManagerInfoObject.setAttachmentList(getAttachmentByFinanceIdType(financeId, typeList));
        }
        return Result.success().setData(riskManagerInfoObject);
    }


    /**
     * 交易员补充材料
     */
    @Transactional
    public void updateFinanceOrderByOnlineTrader(String userId, FinanceOrderObject financeOrder) {
        financeOrder.setApproveStateId(EnumFinanceStatus.Auditing.id);
        financeOrder.setApproveState(EnumFinanceStatus.Auditing.name);
        financeOrder.setLastUpdateManId(userId);
        financeOrder.setLastUpdateTime(new Date());
        orderRepository.save(DozerUtils.copy(financeOrder, FinanceOrder.class));
    }

    /**
     * 保存,更新 业务员 填写的信息
     */
    public void saveFinanceOrderSalesmanInfo(FinanceOrderSalesmanInfoObject salesmanInfo) {
        FinanceOrderSalesmanInfo salesmanOrder = salesmanRepository.findByFinanceId(salesmanInfo.getFinanceId());
        if (salesmanOrder != null) {
            salesmanInfo.setId(salesmanOrder.getId());
        }
        salesmanRepository.save(DozerUtils.copy(salesmanInfo, FinanceOrderSalesmanInfo.class));
    }

    /**
     * 保存,更新 尽调员 填写的信息
     */
    public void saveFinanceOrderInvestigatorInfo(FinanceOrderInvestigatorInfoObject investigatorInfo) {
        FinanceOrderInvestigatorInfo investigatorOrder = investigatorRepository.findByFinanceId(investigatorInfo.getFinanceId());
        if (investigatorOrder != null) {
            investigatorInfo.setId(investigatorOrder.getId());
        }
        investigatorRepository.save(DozerUtils.copy(investigatorInfo, FinanceOrderInvestigatorInfo.class));
    }

    /**
     * 保存,更新 监管员 填写的信息
     */
    public void saveFinanceOrderSupervisorInfo(FinanceOrderSupervisorInfoObject supervisorInfo) {
        FinanceOrderSupervisorInfo supervisorOrder = supervisorRepository.findByFinanceId(supervisorInfo.getFinanceId());
        if (supervisorOrder != null) {
            supervisorInfo.setId(supervisorOrder.getId());
        }
        supervisorRepository.save(DozerUtils.copy(supervisorInfo, FinanceOrderSupervisorInfo.class));
    }

    /**
     * 保存,更新 风控 填写的信息
     */
    public void saveFinanceOrderRiskManagerInfo(FinanceOrderRiskManagerInfoObject riskManagerInfo) {
        FinanceOrderRiskManagerInfo riskManagerOrder = riskRepository.findByFinanceId(riskManagerInfo.getFinanceId());
        if (riskManagerOrder != null) {
            riskManagerInfo.setId(riskManagerOrder.getId());
        }
        riskRepository.save(DozerUtils.copy(riskManagerInfo, FinanceOrderRiskManagerInfo.class));
    }



}
