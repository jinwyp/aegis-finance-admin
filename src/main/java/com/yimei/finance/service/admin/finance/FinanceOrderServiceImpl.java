package com.yimei.finance.service.admin.finance;

import com.yimei.finance.entity.admin.finance.EnumFinanceStatus;
import com.yimei.finance.entity.admin.finance.FinanceOrder;
import com.yimei.finance.entity.common.result.Page;
import com.yimei.finance.entity.common.result.Result;
import com.yimei.finance.entity.site.user.FinanceOrderSearch;
import com.yimei.finance.repository.admin.finance.FinanceOrderRepository;
import com.yimei.finance.utils.DozerUtils;
import com.yimei.finance.utils.Where;
import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Service
public class FinanceOrderServiceImpl {
    @Autowired
    private FinanceOrderRepository financeOrderRepository;
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 交易员补充材料
     */
    @Transactional
    public void updateFinanceOrderByOnlineTrader(FinanceOrder financeOrder) {
        FinanceOrder order = financeOrderRepository.findOne(financeOrder.getId());
        order.setFinancingAmount(financeOrder.getFinancingAmount());
        order.setExpectDate(financeOrder.getExpectDate());
        order.setBusinessAmount(financeOrder.getBusinessAmount());
        order.setTransportMode(financeOrder.getTransportMode());
        order.setProcurementPrice(financeOrder.getProcurementPrice());
        order.setContractor(financeOrder.getContractor());
        order.setDownstreamContractor(financeOrder.getDownstreamContractor());
        order.setTerminalServer(financeOrder.getTerminalServer());
        order.setComments(financeOrder.getComments());
        order.setApproveStateId(EnumFinanceStatus.Auditing.id);
        order.setApproveState(EnumFinanceStatus.Auditing.name);
        financeOrderRepository.save(order);
    }

    /**
     * 查询金融单
     */
    public Result getFinanceOrderBySelect(int userId, FinanceOrderSearch order, Page page) {
        String hql = " select o.id, o.sourceId, o.applyType, o.applyDateTime, o.financingAmount, o.expectDate, " +
                " o.approveState from FinanceOrder o where o.userId=:userId ";
        if (order != null) {
            if (!StringUtils.isEmpty(order.getStartDate()) && !StringUtils.isEmpty(order.getEndDate())) {
                hql += " and o.applyStartDate between :startDate and :endDate ";
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

        Query query = entityManager.createQuery(hql);
        query.setParameter("userId", userId);
        if (order != null) {
            if (!StringUtils.isEmpty(order.getStartDate()) && !StringUtils.isEmpty(order.getEndDate())) {
                query.setParameter("startDate", order.getStartDate());
                query.setParameter("endDate", order.getEndDate());
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
        List<FinanceOrder> financeOrderList = totalList.subList(page.getOffset(), (int) (page.getOffset() + page.getPage() * page.getTotal()));
        return Result.success().setData(financeOrderList).setMeta(page);
    }

}
