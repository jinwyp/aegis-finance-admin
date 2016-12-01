package com.yimei.finance.repository.admin.finance;

import com.yimei.finance.entity.admin.finance.FinanceOrderContract;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdminFinanceOrderContractRepository extends JpaRepository<FinanceOrderContract, Long> {

    FinanceOrderContract findByFinanceIdAndType(@Param("financeId") Long financeId,
                                                @Param("type") int type);

    @Query(" select f from FinanceOrderContract f where f.financeId=?1 and f.type=?2 and (f.applyUserId=?3 or f.applyCompanyId=?4) ")
    FinanceOrderContract findByFinanceIdAndTypeAndApplyUserIdAndApplyCompanyId(@Param("financeId") Long financeId,
                                                                               @Param("type") int type,
                                                                               @Param("applyUserId") Long applyUserId,
                                                                               @Param("applyCompanyId") Long applyCompanyId);
}
