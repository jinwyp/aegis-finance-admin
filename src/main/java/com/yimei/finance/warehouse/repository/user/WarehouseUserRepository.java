package com.yimei.finance.warehouse.repository.user;

import com.yimei.finance.warehouse.entity.user.WarehouseUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseUserRepository extends JpaRepository<WarehouseUser, Long> {
}
