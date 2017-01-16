package com.yimei.finance.admin.repository.finance;

import com.yimei.finance.entity.admin.finance.FinanceOrderRiskManagerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminFinanceOrderRiskRepository extends JpaRepository<FinanceOrderRiskManagerInfo, Long> {

    FinanceOrderRiskManagerInfo findByFinanceId(Long financeId);

}
