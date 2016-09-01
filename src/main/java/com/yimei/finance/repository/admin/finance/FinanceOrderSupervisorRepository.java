package com.yimei.finance.repository.admin.finance;

import com.yimei.finance.entity.admin.finance.FinanceOrderSupervisorInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinanceOrderSupervisorRepository extends JpaRepository<FinanceOrderSupervisorInfo, Long> {

    FinanceOrderSupervisorInfo findByFinanceId(Long financeId);

}
