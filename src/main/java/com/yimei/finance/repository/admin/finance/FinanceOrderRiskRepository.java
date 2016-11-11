package com.yimei.finance.repository.admin.finance;

import com.yimei.finance.entity.admin.finance.FinanceOrderRiskManagerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FinanceOrderRiskRepository extends JpaRepository<FinanceOrderRiskManagerInfo, Long> {

    FinanceOrderRiskManagerInfo findByFinanceId(Long financeId);

    @Modifying(clearAutomatically = true)
    @Query(" update FinanceOrderRiskManagerInfo f set f.upstreamContractStatus=:upstreamContractStatus where f.financeId=:financeId ")
    int updateUpstreamContractStatusByFinanceId(@Param("financeId") Long financeId,
                                                 @Param("upstreamContractStatus") int upstreamContractStatus);

    @Modifying(clearAutomatically = true)
    @Query(" update FinanceOrderRiskManagerInfo f set f.downstreamContractStatus=:downstreamContractStatus where f.financeId=:financeId ")
    int updateDownstreamContractStatusByFinanceId(@Param("financeId") Long financeId,
                                                   @Param("downstreamContractStatus") int downstreamContractStatus);
}
