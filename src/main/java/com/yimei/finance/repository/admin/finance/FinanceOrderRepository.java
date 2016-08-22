package com.yimei.finance.repository.admin.finance;

import com.yimei.finance.entity.admin.finance.FinanceOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by zhangbolun on 16/8/16.
 */
public interface FinanceOrderRepository extends JpaRepository<FinanceOrder, Long> {

    List<FinanceOrder> findByUserId(int userId);

    FinanceOrder findByIdAndUserId(@Param("id") Long id,
                                   @Param("userId")int userId);


}
