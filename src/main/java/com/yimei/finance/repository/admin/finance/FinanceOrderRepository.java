package com.yimei.finance.repository.admin.finance;

import com.yimei.finance.entity.admin.finance.FinanceOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface FinanceOrderRepository extends JpaRepository<FinanceOrder, Long> {

    List<FinanceOrder> findByUserIdOrApplyCompanyId(@Param("userId") Long userId,
                                                    @Param("applyCompanyId") Long applyCompanyId);

    @Query(" select f from FinanceOrder f where f.id = ?1 and (f.userId=?2 or f.applyCompanyId=?3) ")
    FinanceOrder findByIdAndUserIdOrCompanyId(@Param("id") Long id,
                                              @Param("userId") Long userId,
                                              @Param("applyCompanyId") Long applyCompanyId);

    FinanceOrder findBySourceId(@Param("sourceId") String sourceId);

    List<FinanceOrder> findByUserIdAndCreateTimeGreaterThan(@Param("id") Long id, @Param("createTime") Date createTime);

    @Query(" select f.riskCompanyId from FinanceOrder f where f.id = ?1")
    Long findRiskCompanyIdById(@Param("id") Long id);

    List<FinanceOrder> findByCreateTimeBeforeAndApproveStateIdNot(@Param("createTime") Date createTime, @Param("approveStateId") Integer approveStateId);


}
