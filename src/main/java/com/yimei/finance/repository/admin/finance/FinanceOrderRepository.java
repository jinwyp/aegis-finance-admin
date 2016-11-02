package com.yimei.finance.repository.admin.finance;

import com.yimei.finance.entity.admin.finance.FinanceOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface FinanceOrderRepository extends JpaRepository<FinanceOrder, Long> {

    List<FinanceOrder> findByUserId(int userId);

    FinanceOrder findByIdAndUserId(@Param("id") Long id,
                                   @Param("userId")int userId);

    FinanceOrder findBySourceId(@Param("sourceId") String sourceId);

    List<FinanceOrder> findByUserIdAndCreateTimeGreaterThan(@Param("id") int id, @Param("createTime") Date createTime);

    Long findBusinessCompanyIdById(@Param("id") Long id);
}
