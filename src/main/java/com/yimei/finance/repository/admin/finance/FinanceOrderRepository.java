package com.yimei.finance.repository.admin.finance;

import com.yimei.finance.entity.admin.finance.FinanceOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface FinanceOrderRepository extends JpaRepository<FinanceOrder, Long> {

    List<FinanceOrder> findByUserId(int userId);

    FinanceOrder findByIdAndUserId(@Param("id") Long id,
                                   @Param("userId")int userId);

    FinanceOrder findBySourceId(String sourceId);

    List<FinanceOrder> findByUserIdAndCreateTimeGreaterThan(int id, LocalDateTime createTime);
}
