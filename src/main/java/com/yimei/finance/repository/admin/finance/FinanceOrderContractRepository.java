package com.yimei.finance.repository.admin.finance;

import com.yimei.finance.entity.admin.finance.FinanceOrderContract;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinanceOrderContractRepository extends JpaRepository<FinanceOrderContract, Long> {

    FinanceOrderContract findByFinanceIdAndType(@Param("financeid") Long financeId,
                                                @Param("type") int type);

    FinanceOrderContract findByFinanceIdAndApplyUserIdAndApplyCompanyId(@Param("financeId") Long financeId,
                                                                        @Param("applyUserId") Long applyUserId,
                                                                        @Param("applyCompanyId") Long applyCompanyId);
}
