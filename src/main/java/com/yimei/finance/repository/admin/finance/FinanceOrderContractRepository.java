package com.yimei.finance.repository.admin.finance;

import com.yimei.finance.entity.admin.finance.FinanceOrderContract;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FinanceOrderContractRepository extends JpaRepository<FinanceOrderContract, Long> {

    FinanceOrderContract findByFinanceIdAndType(@Param("financeid") Long financeId,
                                                @Param("type") int type);

    @Query(" select f from FinanceOrderContract f where f.financeId = ?1 and (f.applyUserId=?2 or f.applyCompanyId=?3) ")
    FinanceOrderContract findByFinanceIdAndApplyUserIdAndApplyCompanyId(@Param("financeId") Long financeId,
                                                                        @Param("applyUserId") Long applyUserId,
                                                                        @Param("applyCompanyId") Long applyCompanyId);
}
