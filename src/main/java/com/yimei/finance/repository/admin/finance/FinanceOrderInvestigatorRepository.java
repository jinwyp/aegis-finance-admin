package com.yimei.finance.repository.admin.finance;

import com.yimei.finance.entity.admin.finance.FinanceOrderInvestigatorInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinanceOrderInvestigatorRepository extends JpaRepository<FinanceOrderInvestigatorInfo, Long> {

    FinanceOrderInvestigatorInfo findByFinanceId(Long financeId);
}
