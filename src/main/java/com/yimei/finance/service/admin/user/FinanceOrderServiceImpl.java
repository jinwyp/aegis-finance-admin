package com.yimei.finance.service.admin.user;

import com.yimei.finance.entity.admin.finance.FinanceOrder;
import com.yimei.finance.repository.admin.finance.FinanceOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by liuxinjie on 16/8/22.
 */
@Service
public class FinanceOrderServiceImpl {
    @Autowired
    private FinanceOrderRepository financeOrderRepository;

    @Transactional
    public void updateFinanceOrder(FinanceOrder financeOrder) {
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
        financeOrderRepository.save(order);
    }

}
