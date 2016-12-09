package com.yimei.finance.warehouse.repository.user;

import com.yimei.finance.warehouse.entity.user.WarehouseAdminUserLoginRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface WarehouseAdminUserLoginRecordRepository extends JpaRepository<WarehouseAdminUserLoginRecord, Long> {

    WarehouseAdminUserLoginRecord findTopByUserIdOrderByIdDesc(@Param("id") Long id);
}
