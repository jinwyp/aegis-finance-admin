package com.yimei.finance.admin.repository.finance;

import com.yimei.finance.entity.admin.finance.FinanceOrderSupervisorInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminFinanceOrderSupervisorRepository extends JpaRepository<FinanceOrderSupervisorInfo, Long> {

    FinanceOrderSupervisorInfo findByFinanceId(Long financeId);

}
