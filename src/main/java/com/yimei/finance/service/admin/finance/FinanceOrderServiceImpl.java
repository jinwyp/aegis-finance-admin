package com.yimei.finance.service.admin.finance;

import com.yimei.finance.entity.admin.finance.EnumFinanceStatus;
import com.yimei.finance.entity.admin.finance.FinanceOrder;
import com.yimei.finance.repository.admin.finance.FinanceOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FinanceOrderServiceImpl {
    @Autowired
    private FinanceOrderRepository financeOrderRepository;

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
     * 更改金融单状态
     */
    @Transactional
    public void updateFinanceOrderApproveState(Long financeId, EnumFinanceStatus status) {
        FinanceOrder order = financeOrderRepository.findOne(financeId);
        order.setApproveStateId(status.id);
        order.setApproveState(status.name);
        financeOrderRepository.save(order);
    }

}
