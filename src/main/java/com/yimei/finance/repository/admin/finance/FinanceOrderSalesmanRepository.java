package com.yimei.finance.repository.admin.finance;

import com.yimei.finance.entity.admin.finance.FinanceOrderSalesmanInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinanceOrderSalesmanRepository extends JpaRepository<FinanceOrderSalesmanInfo, Long> {

    FinanceOrderSalesmanInfo findByFinanceId(Long financeId);

}
