package com.yimei.finance.admin.repository.finance;

import com.yimei.finance.entity.admin.finance.FinanceOrderSalesmanInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminFinanceOrderSalesmanRepository extends JpaRepository<FinanceOrderSalesmanInfo, Long> {

    FinanceOrderSalesmanInfo findByFinanceId(Long financeId);

}
